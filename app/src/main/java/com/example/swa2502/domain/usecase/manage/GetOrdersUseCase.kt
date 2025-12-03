package com.example.swa2502.domain.usecase.manage

import com.example.swa2502.domain.model.Order
import com.example.swa2502.domain.repository.ManageRepository
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val repository: ManageRepository
) {
    /**
     * 모든 주문 조회
     * @param shopId 가게 ID
     * @return 주문 목록
     */
    suspend operator fun invoke(shopId: Int): Result<List<Order>> {
        return repository.getShopOrders(shopId)
    }

    /**
     * 조리중인 주문 불러오기 (ACCEPTED 상태)
     * @param shopId 가게 ID
     * @return 조리중인 주문 목록
     */
    suspend fun getOrdersInProgress(shopId: Int): Result<List<Order>> {
        return repository.getShopOrdersByStatus(shopId, "ACCEPTED")
    }

    /**
     * 수령 대기중인 주문 불러오기 (READY 상태)
     * @param shopId 가게 ID
     * @return 수령 대기중인 주문 목록
     */
    suspend fun getPreparedOrders(shopId: Int): Result<List<Order>> {
        return repository.getShopOrdersByStatus(shopId, "READY")
    }
}