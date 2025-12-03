package com.example.swa2502.domain.repository

import com.example.swa2502.domain.model.Order

interface ManageRepository {
    // 관리자가 주문을 관리하는 작업 수행
    suspend fun getShopOrders(shopId: Int): Result<List<Order>>
    suspend fun getShopOrdersByStatus(shopId: Int, status: String): Result<List<Order>>
    suspend fun updateOrderStatusToNext(orderItemId: Int): Result<Order>
}