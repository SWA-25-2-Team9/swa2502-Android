package com.example.swa2502.data.api

import com.example.swa2502.data.dto.order.CurrentOrderResponseDto
import com.example.swa2502.data.dto.order.MenuDto
import com.example.swa2502.data.dto.order.MenuDetailDto
import com.example.swa2502.data.dto.order.CartStoreDto
import com.example.swa2502.data.dto.order.CartItemUpdateDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.Body
interface OrderApi {
    // 실제 API 요청을 보내는 함수를 작성

    // 메뉴 목록 화면 API
    @GET("api/v1/shops/{shopId}/menus")
    suspend fun getMenuList(
        @Path("shopId") shopId: Int
    ): List<MenuDto>

    // 나의 현재 주문 정보 조회 API 추가
    @GET("api/v1/orders/current")
    suspend fun getCurrentOrder(): List<CurrentOrderResponseDto>

    // 메뉴 옵션 API
    @GET("api/v1/menus/{menuId}")
    suspend fun getMenuDetail(
        @Path("menuId") menuId: Int
    ): MenuDetailDto

    // 장바구니 조회 API
    @GET("api/v1/cart")
    suspend fun getShoppingCartInfo(): List<CartStoreDto>

    // 장바구니 수량 변경 API
    @PATCH("api/v1/carts/{cartItemId}")
    suspend fun updateCartItemQuantity(
        @Path("cartItemId") cartItemId: Int,
        @Body request: CartItemUpdateDto
    )

    // 장바구니 항목 삭제 API
    @DELETE("api/v1/carts/{cartItemId}")
    suspend fun deleteCartItem(
        @Path("cartItemId") cartItemId: Int // CartMenu에서 menuId가 cartItemId로 사용된다고 가정
    ): Map<String, Int>

    // 장바구니 전체 비우기 API
    @DELETE("api/v1/carts")
    suspend fun clearShoppingCart(): Map<String, String>
}