package com.example.swa2502.data.datasource

import com.example.swa2502.data.api.ManageApi
import com.example.swa2502.data.dto.manage.response.AdminOrderItemDto
import javax.inject.Inject

class ManageDataSource @Inject constructor(
    private val api: ManageApi
) {
    /**
     * API를 통해 가게의 모든 주문 목록 조회
     * @param shopId 가게 ID
     * @return List<AdminOrderItemDto>
     */
    suspend fun getShopOrders(shopId: Int): List<AdminOrderItemDto> {
        return api.getShopOrders(shopId)
    }
    
    /**
     * API를 통해 가게의 상태별 주문 목록 조회
     * @param shopId 가게 ID
     * @param status 주문 상태 (ACCEPTED, READY 등)
     * @return List<AdminOrderItemDto>
     */
    suspend fun getShopOrdersByStatus(shopId: Int, status: String): List<AdminOrderItemDto> {
        return api.getShopOrdersByStatus(shopId, status)
    }
}