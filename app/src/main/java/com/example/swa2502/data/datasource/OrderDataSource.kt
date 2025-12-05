package com.example.swa2502.data.datasource

import com.example.swa2502.data.api.OrderApi
import com.example.swa2502.domain.model.MenuItem
import com.example.swa2502.data.dto.order.MenuDto
import com.example.swa2502.data.dto.order.MenuDetailDto
import javax.inject.Inject
import com.example.swa2502.data.dto.order.CurrentOrderResponseDto
import com.example.swa2502.data.dto.order.CartStoreDto
import com.example.swa2502.data.dto.order.CartItemUpdateDto
import com.example.swa2502.data.dto.order.OrderRequestDto
import com.example.swa2502.data.dto.order.OrderResponseDto

class OrderDataSource @Inject constructor(
    private val api: OrderApi
) {
    /**
     * API를 통해 메뉴 목록 가져옴
     * @param shopId
     * **/
    suspend fun fetchMenuList(shopId: Int): List<MenuDto>{
        return api.getMenuList(shopId)
    }

    /**
     * API를 통해 나의 현재 주문 정보 가져옴
     * @return List<CurrentOrderResponseDto>
     */
    suspend fun getCurrentOrder(): List<CurrentOrderResponseDto> {
        return api.getCurrentOrder()
    }

    /**
     * API를 통해 특정 메뉴의 상세 옵션 정보 가져옴
     * @param menuId
     */
    suspend fun fetchMenuDetail(menuId: Int): MenuDetailDto {
        return api.getMenuDetail(menuId)
    }

    // 주문 생성
    suspend fun createOrder(request: OrderRequestDto): List<OrderResponseDto> {
        return api.createOrder(request)
    }

    // 장바구니 정보 조회
    suspend fun fetchShoppingCartInfo(): List<CartStoreDto> {
        return api.getShoppingCartInfo()
    }

    // 장바구니 수량 변경
    suspend fun updateCartItemQuantity(cartItemId: Int, quantity: Int) {
        return api.updateCartItemQuantity(cartItemId, CartItemUpdateDto(quantity))
    }

    // 장바구니 항목 삭제
    suspend fun deleteCartItem(cartItemId: Int): Int {
        val response = api.deleteCartItem(cartItemId)
        return response["cartCount"] ?: 0
    }

    // 장바구니 전체 비우기
    suspend fun clearShoppingCart(): String {
        val response = api.clearShoppingCart()
        return response["message"] ?: "failed"
    }
}