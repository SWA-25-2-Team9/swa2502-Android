package com.example.swa2502.domain.usecase.order

import com.example.swa2502.domain.model.CurrentOrderInfo
import com.example.swa2502.domain.repository.OrderRepository
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    /**
     * 주문 생성 (결제 성공 후 호출)
     * @param paymentMethod 선택된 결제 수단 (CARD 또는 SIMPLE)
     * @return 생성된 주문 정보 목록
     */
    suspend operator fun invoke(paymentMethod: String): Result<List<CurrentOrderInfo>> {
        return repository.createOrder(paymentMethod)
    }
}