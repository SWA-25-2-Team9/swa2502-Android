package com.example.swa2502.data.api

import com.example.swa2502.data.dto.order.CurrentOrderResponseDto
import com.example.swa2502.data.dto.order.MenuDto
import com.example.swa2502.data.dto.order.MenuDetailDto
import com.example.swa2502.data.dto.order.CartItemUpdateDto
import com.example.swa2502.data.dto.order.OrderRequestDto
import com.example.swa2502.data.dto.order.OrderResponseDto
import com.example.swa2502.data.dto.order.CartAddRequestDto
import com.example.swa2502.data.dto.order.CartAddResponseDto
import com.example.swa2502.data.dto.order.CartResponseDto
import com.example.swa2502.data.dto.order.ClearShoppingCartDto
import com.example.swa2502.data.dto.order.DeleteCartItemResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.Body
interface OrderApi {
    // 실제 API 요청을 보내는 함수를 작성

    // 메뉴 목록 화면 API (구현완료)
    @GET("api/v1/shops/{shopId}/menus")
    suspend fun getMenuList(
        @Path("shopId") shopId: Int
    ): List<MenuDto>

    // 나의 현재 주문 정보 조회 API 추가 (구현완료)
    @GET("api/v1/orders/current")
    suspend fun getCurrentOrder(): List<CurrentOrderResponseDto>

    // 메뉴 옵션 API (구현완료)
    @GET("api/v1/menus/{menuId}")
    suspend fun getMenuDetail(
        @Path("menuId") menuId: Int
    ): MenuDetailDto

    // 주문 생성 API (구현완료)
    @POST("api/v1/orders")
    suspend fun createOrder(
        @Body request: OrderRequestDto
    ): List<OrderResponseDto>

    // 장바구니 조회 API (구현완료)
    @GET("api/v1/carts")
    suspend fun getShoppingCartInfo(): CartResponseDto

    // 장바구니 추가 (구현완료)
    @POST("api/v1/carts")
    suspend fun addCart(
        @Body request: CartAddRequestDto
    ): CartAddResponseDto

    // 장바구니 전체 비우기 API (구현완료)
    @DELETE("api/v1/carts")
    suspend fun clearShoppingCart(): ClearShoppingCartDto

    // 장바구니 항목 삭제 API (구현완료)
    @DELETE("api/v1/carts/{cartItemId}")
    suspend fun deleteCartItem(
        @Path("cartItemId") cartItemId: Int
    ): DeleteCartItemResponseDto

    // 장바구니 수량 변경 API (구현완료)
    @PATCH("api/v1/carts/{cartItemId}")
    suspend fun updateCartItemQuantity(
        @Path("cartItemId") cartItemId: Int,
        @Body request: CartItemUpdateDto
    )




}