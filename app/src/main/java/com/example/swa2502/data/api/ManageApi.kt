package com.example.swa2502.data.api

import com.example.swa2502.data.dto.manage.response.AdminOrderItemDto
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface ManageApi {
    // 전체 주문 목록 조회
    @GET("api/v1/admin/shops/orders")
    suspend fun getShopOrders(
        @Query("shopId") shopId: Int
    ): List<AdminOrderItemDto>
    
    // 상태별 주문 목록 조회
    @GET("api/v1/admin/shops/status")
    suspend fun getShopOrdersByStatus(
        @Query("shopId") shopId: Int,
        @Query("status") status: String
    ): List<AdminOrderItemDto>
    
    // 주문 상태 변경 (다음 상태로 변경)
    @PATCH("api/v1/admin/orders/{orderItemId}/next")
    suspend fun updateOrderStatusToNext(
        @Path("orderItemId") orderItemId: Int
    ): AdminOrderItemDto
}