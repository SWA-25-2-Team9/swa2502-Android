package com.example.swa2502.data.repository

import com.example.swa2502.data.datasource.OrderDataSource
import com.example.swa2502.domain.repository.OrderRepository
import com.example.swa2502.domain.model.CurrentOrderInfo
import com.example.swa2502.domain.model.MenuItem
import com.example.swa2502.domain.model.OrderItem
import com.example.swa2502.data.dto.order.MenuDto
import com.example.swa2502.domain.model.MenuDetail
import com.example.swa2502.domain.model.OptionGroup
import com.example.swa2502.domain.model.OptionItem
import com.example.swa2502.domain.model.CartStore
import com.example.swa2502.domain.model.CartMenu
import com.example.swa2502.domain.model.CartOption
import com.example.swa2502.data.dto.order.OrderRequestDto
import com.example.swa2502.data.dto.order.OrderResponseDto
import com.example.swa2502.data.dto.order.ItemDto
import com.example.swa2502.data.dto.order.CartAddRequestDto
import com.example.swa2502.data.dto.order.CartAddResponseDto
import com.example.swa2502.data.dto.order.DeleteCartItemResponseDto
import com.example.swa2502.data.dto.order.ClearShoppingCartDto
import javax.inject.Inject
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class OrderRepositoryImpl @Inject constructor(
    private val remote: OrderDataSource
): OrderRepository {
    // 장바구니 항목의 menuId와 selectedOptions를 저장 (cartItemId -> Pair<menuId, selectedOptions>)
    // 서버가 반환하는 price가 옵션 가격을 포함하지 않으므로, 클라이언트에서 옵션 가격을 재계산하기 위해 필요
    private val cartItemInfoMap = mutableMapOf<Int, Pair<Int, List<Long>>>()
    private val cartItemInfoMutex = Mutex()
    override suspend fun getMenuList(shopId: Int): Result<List<MenuItem>> {
        return try {
            val dtoList = remote.fetchMenuList(shopId)
            val menuList = dtoList.map { dto ->
                MenuItem(
                    menuId = dto.menuId,
                    name = dto.name,
                    price = dto.price,
                    imageUrl = dto.imageUrl,
                    isSoldOut = dto.isSoldOut
                )
            }
            Result.success(menuList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 현재 주문
    override suspend fun getCurrentOrder(): Result<List<CurrentOrderInfo>> {
        return try {
            val dtoList = remote.getCurrentOrder()
            // 빈 리스트인 경우 (204 처리된 경우) 그대로 반환
            if (dtoList.isEmpty()) {
                Result.success(emptyList())
            } else {
                val orderList = dtoList.map { dto ->
                    CurrentOrderInfo(
                        orderId = dto.orderId,
                        orderNumber = "${dto.orderNumber}번",
                        shopName = dto.shopName,
                        myTurn = dto.myTurn,
                        etaMinutes = dto.etaMinutes,
                        estimatedWaitTime = "약 ${dto.etaMinutes}분 예상",
                        totalPrice = dto.totalPrice,
                        orderedAt = dto.orderedAt,
                        items = dto.items.map { itemDto ->
                            OrderItem(
                                menuName = itemDto.menuName,
                                quantity = itemDto.quantity,
                                price = itemDto.price,
                                options = itemDto.options
                            )
                        }
                    )
                }
                Result.success(orderList)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMenuDetail(menuId: Int): Result<MenuDetail> {
        return try {
            val dto = remote.fetchMenuDetail(menuId)
            // API 응답의 price를 메뉴 기본 가격으로 사용
            // API 응답의 extraPrice를 옵션 추가 가격으로 사용
            val menuDetail = MenuDetail(
                menuId = dto.menuId,
                name = dto.name,
                basePrice = dto.price, // API의 price 필드 활용
                optionGroups = dto.optionGroups.map { groupDto ->
                    OptionGroup(
                        id = groupDto.groupId,
                        name = groupDto.name,
                        isRequired = groupDto.required,
                        options = groupDto.options.map { itemDto ->
                            OptionItem(
                                id = itemDto.id,
                                name = itemDto.name,
                                price = itemDto.extraPrice // API의 extraPrice 필드 활용
                            )
                        },
                        selectedOptionId = if (groupDto.options.isNotEmpty()) groupDto.options.first().id else null
                    )
                }
            )
            Result.success(menuDetail)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 주문 생성
    override suspend fun createOrder(paymentMethod: String): Result<List<CurrentOrderInfo>> {
        return try {
            val request = OrderRequestDto(request = paymentMethod)
            val dtoList = remote.createOrder(request)

            // API가 반환하는 필드(orderId, orderNumber, myTurn, etaMinutes)에 맞춰 매핑
            // 기타 정보는 서버 응답에 없으므로 기본값으로 채움
            val domainList = dtoList.map { dto ->
                CurrentOrderInfo(
                    orderId = dto.orderId,
                    orderNumber = "${dto.orderNumber}",
                    shopName = "", // 응답에 없음
                    myTurn = dto.myTurn,
                    etaMinutes = dto.etaMinutes,
                    estimatedWaitTime = "약 ${dto.etaMinutes}분 예상",
                    totalPrice = 0, // 응답에 없음
                    orderedAt = "", // 응답에 없음
                    items = emptyList() // 응답에 없음
                )
            }
            Result.success(domainList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 장바구니 정보 조회
    override suspend fun getShoppingCartInfo(): Result<List<CartStore>> {
        return try {
            val dto = remote.fetchShoppingCartInfo()

            // 단일 CartResponseDto -> Domain 모델 리스트로 변환
            // 서버가 반환하는 price는 옵션 가격을 포함하지 않은 기본 가격이므로,
            // 클라이언트에서 옵션 가격을 포함한 총 가격을 계산
            val cartMenus = dto.items.map { menuDto ->
                // 저장된 menuId와 selectedOptions 가져오기
                val (menuId, selectedOptions) = cartItemInfoMutex.withLock {
                    cartItemInfoMap[menuDto.cartItemId] ?: Pair(0, emptyList())
                }
                
                // 옵션 가격을 포함한 총 가격 계산
                val totalPrice = if (menuId > 0 && selectedOptions.isNotEmpty()) {
                    // 저장된 menuId로 메뉴 상세 정보를 가져와서 옵션 가격 계산
                    // 서버의 menuDto.price는 수량 1개당 기본 가격이므로, 옵션 가격을 추가하여 계산
                    calculateCartItemPriceWithOptions(menuId, menuDto.price, selectedOptions, menuDto.quantity)
                } else {
                    // menuId가 없거나 옵션이 없으면 서버가 반환한 가격 사용
                    // 서버의 price가 이미 수량이 곱해진 가격일 수도 있으므로, 수량을 곱하지 않음
                    menuDto.price
                }
                
                // 옵션 ID를 옵션 이름으로 변환
                val optionNames = if (menuId > 0 && selectedOptions.isNotEmpty()) {
                    convertOptionIdsToNames(menuId, selectedOptions)
                } else {
                    // menuId가 없으면 optionsText를 그대로 사용 (이미 옵션 이름일 수 있음)
                    menuDto.optionsText.split(",")
                        .map { it.trim() }
                        .filter { it.isNotEmpty() }
                }
                
                CartMenu(
                    cartItemId = menuDto.cartItemId,
                    menuId = menuId,
                    menuName = menuDto.menuName,
                    quantity = menuDto.quantity,
                    options = optionNames.map { optionName -> CartOption(name = optionName) },
                    totalPrice = totalPrice,
                    storeId = 0
                )
            }
            
            val domainList = listOf(
                CartStore(
                    storeId = 0,
                    storeName = "",
                    cartMenus = cartMenus
                )
            )
            Result.success(domainList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 장바구니 항목의 옵션 가격을 포함한 총 가격 계산
    private suspend fun calculateCartItemPriceWithOptions(menuId: Int, basePrice: Int, selectedOptions: List<Long>, quantity: Int): Int {
        return try {
            // 메뉴 상세 정보를 가져와서 옵션 가격 계산
            val menuDetailDto = remote.fetchMenuDetail(menuId)
            
            // 모든 옵션을 ID -> 가격 맵으로 변환
            val optionIdToPriceMap = menuDetailDto.optionGroups.flatMap { group ->
                group.options.map { option ->
                    option.id.toLong() to option.extraPrice
                }
            }.toMap()
            
            // 선택된 옵션들의 가격 합계 계산 (수량 1개당 옵션 가격)
            val totalOptionPrice = selectedOptions.sumOf { optionId ->
                optionIdToPriceMap[optionId] ?: 0
            }
            
            // 단가 계산: 메뉴 기본 가격 + 옵션 가격 합계
            val unitPrice = menuDetailDto.price + totalOptionPrice
            
            // 단가 * 수량
            unitPrice * quantity
        } catch (e: Exception) {
            // 에러 발생 시 서버가 반환한 가격이 이미 수량이 곱해진 가격인지 확인 필요
            // 일단 서버 가격을 그대로 사용 (서버가 이미 올바른 총 가격을 반환할 수 있음)
            basePrice
        }
    }

    // 옵션 ID를 옵션 이름으로 변환
    private suspend fun convertOptionIdsToNames(menuId: Int, selectedOptions: List<Long>): List<String> {
        return try {
            // 메뉴 상세 정보를 가져와서 옵션 정보 확인
            val menuDetailDto = remote.fetchMenuDetail(menuId)
            
            // 모든 옵션을 ID -> 이름 맵으로 변환
            val optionIdToNameMap = menuDetailDto.optionGroups.flatMap { group ->
                group.options.map { option ->
                    option.id.toLong() to option.name
                }
            }.toMap()
            
            // 선택된 옵션 ID들을 옵션 이름으로 변환
            selectedOptions.mapNotNull { optionId ->
                optionIdToNameMap[optionId]
            }
        } catch (e: Exception) {
            // 에러 발생 시 빈 리스트 반환
            emptyList()
        }
    }

    // 장바구니 추가
    override suspend fun addCart(menuId: Int, quantity: Int, selectedOptions: List<Long>): Result<Int> {
        return try {
            val request = CartAddRequestDto(
                menuId = menuId,
                quantity = quantity,
                selectedOptions = selectedOptions
            )
            val response = remote.addCart(request)
            
            // 장바구니 추가 성공 시, cartItemId와 menuId, selectedOptions를 저장
            // 장바구니 조회 시 옵션 가격을 재계산하기 위해 필요
            cartItemInfoMutex.withLock {
                cartItemInfoMap[response.cartItemId] = Pair(menuId, selectedOptions)
            }
            
            Result.success(response.cartCount)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 장바구니 항목 수량 변경
    override suspend fun updateCartItemQuantity(cartItemId: Int, quantity: Int): Result<Unit> {
        return runCatching {
            remote.updateCartItemQuantity(cartItemId, quantity)
            Unit
        }
    }

    // 장바구니 항목 삭제 (메뉴 삭제)
    override suspend fun deleteCartItem(cartItemId: Int): Result<Int> {
        return try {
            val response = remote.deleteCartItem(cartItemId)
            
            // 장바구니 항목 삭제 시 저장된 정보도 삭제
            cartItemInfoMutex.withLock {
                cartItemInfoMap.remove(cartItemId)
            }
            
            Result.success(response.cartCount)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 장바구니 전체 비우기 (가게 전체 삭제 대신 사용)
    override suspend fun clearShoppingCart(): Result<Unit> {
        return try {
            remote.clearShoppingCart()
            
            // 장바구니 전체 비우기 시 저장된 정보도 모두 삭제
            cartItemInfoMutex.withLock {
                cartItemInfoMap.clear()
            }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}