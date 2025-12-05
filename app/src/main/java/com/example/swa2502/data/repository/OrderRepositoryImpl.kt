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
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val remote: OrderDataSource
): OrderRepository {
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
        return runCatching {
            remote.getCurrentOrder().map { dto ->
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
        }
    }

    override suspend fun getMenuDetail(menuId: Int): Result<MenuDetail> {
        return try {
            val dto = remote.fetchMenuDetail(menuId)
            val menuDetail = MenuDetail(
                menuId = dto.menuId,
                name = dto.name,
                basePrice = dto.price,
                optionGroups = dto.optionGroups.map { groupDto ->
                    OptionGroup(
                        id = groupDto.id,
                        name = groupDto.name,
                        isRequired = groupDto.isRequired,
                        options = groupDto.options.map { itemDto ->
                            OptionItem(
                                id = itemDto.id,
                                name = itemDto.name,
                                price = itemDto.price
                            )
                        },
                        // **기본 선택 로직**: 필수 옵션 그룹이거나 선택 옵션 그룹일 경우 첫 번째 옵션을 선택
                        selectedOptionId = if (groupDto.options.isNotEmpty()) groupDto.options.first().id else null
                    )
                }
            )
            Result.success(menuDetail)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}