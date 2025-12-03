package com.example.swa2502.data.repository

import com.example.swa2502.data.datasource.ManageDataSource
import com.example.swa2502.data.dto.manage.response.AdminOrderItemDto
import com.example.swa2502.domain.model.Order
import com.example.swa2502.domain.repository.ManageRepository
import javax.inject.Inject

class ManageRepositoryImpl @Inject constructor(
    private val remote: ManageDataSource
): ManageRepository{
    override suspend fun getShopOrders(shopId: Int): Result<List<Order>> {
        return runCatching {
            val orderItems = remote.getShopOrders(shopId)
            convertToOrderList(orderItems)
        }
    }
    
    override suspend fun getShopOrdersByStatus(shopId: Int, status: String): Result<List<Order>> {
        return runCatching {
            val orderItems = remote.getShopOrdersByStatus(shopId, status)
            convertToOrderList(orderItems)
        }
    }
    
    /**
     * AdminOrderItemDto 리스트를 Order 리스트로 변환
     * 같은 orderId를 가진 항목들을 그룹화하여 하나의 Order로 만듦
     */
    private fun convertToOrderList(orderItems: List<AdminOrderItemDto>): List<Order> {
        return orderItems.groupBy { it.orderId }
            .map { (orderId, items) ->
                Order(
                    id = items.first().orderItemId.toString(),
                    orderNumber = orderId.toString(),
                    orderState = items.first().status,
                    menus = items.map { it.menuName },
                    timeStamp = "" // API 응답에 시간 정보가 없으므로 빈 문자열로 설정
                )
            }
    }
}