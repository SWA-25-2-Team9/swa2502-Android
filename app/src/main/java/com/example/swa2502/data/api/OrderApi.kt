package com.example.swa2502.data.api

import com.example.swa2502.data.dto.order.CurrentOrderResponseDto
import com.example.swa2502.data.dto.order.MenuDto
import retrofit2.http.GET
import retrofit2.http.Path
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
}