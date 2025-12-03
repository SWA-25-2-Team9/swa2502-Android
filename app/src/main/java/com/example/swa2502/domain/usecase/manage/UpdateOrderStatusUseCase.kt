package com.example.swa2502.domain.usecase.manage

import com.example.swa2502.domain.model.Order
import com.example.swa2502.domain.repository.ManageRepository
import javax.inject.Inject

class UpdateOrderStatusUseCase @Inject constructor(
    private val repository: ManageRepository
) {
    /**
     * 주문 상태를 다음 단계로 변경
     * ACCEPTED -> READY -> PICKEDUP
     * @param orderItemId 주문 항목 ID
     * @return 업데이트된 주문 정보
     */
    suspend operator fun invoke(orderItemId: Int): Result<Order> {
        return repository.updateOrderStatusToNext(orderItemId)
    }
}

