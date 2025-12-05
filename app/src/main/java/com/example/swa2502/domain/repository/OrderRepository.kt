package com.example.swa2502.domain.repository

import com.example.swa2502.domain.model.MenuItem
import com.example.swa2502.domain.model.MenuDetail
import com.example.swa2502.domain.model.CurrentOrderInfo
import com.example.swa2502.domain.model.CartStore

interface OrderRepository {
    suspend fun getMenuList(shopId: Int): Result<List<MenuItem>>

    // 나의 현재 주문 정보 조회
    suspend fun getCurrentOrder(): Result<List<CurrentOrderInfo>>

    // 메뉴 옵션
    suspend fun getMenuDetail(menuId: Int): Result<MenuDetail>

    // 장바구니 정보 조회
    suspend fun getShoppingCartInfo(): Result<List<CartStore>>

    // 장바구니 항목 수량 변경
    suspend fun updateCartItemQuantity(cartItemId: Int, quantity: Int): Result<Unit>

    // 장바구니 항목 삭제
    suspend fun deleteCartItem(cartItemId: Int): Result<Int> // 남은 카트 항목 수 반환

    // 장바구니 전체 비우기
    suspend fun clearShoppingCart(): Result<Unit>
}