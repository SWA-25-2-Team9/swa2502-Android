package com.example.swa2502.domain.usecase.order

import com.example.swa2502.domain.model.CurrentOrderInfo
import com.example.swa2502.domain.repository.OrderRepository
import javax.inject.Inject

class GetCurrentOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    /**
     * 현재 로그인된 사용자의 모든 주문 정보를 조회
     * @return 현재 주문 정보 목록
     */
    suspend operator fun invoke(): Result<List<CurrentOrderInfo>> {
        return repository.getCurrentOrder()
    }
}