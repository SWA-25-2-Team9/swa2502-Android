package com.example.swa2502.domain.usecase.order

import com.example.swa2502.domain.repository.OrderRepository
import javax.inject.Inject

class ClearShoppingCartUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    /**
     * 장바구니 전체 비우기
     */
    suspend operator fun invoke(): Result<Unit> {
        return repository.clearShoppingCart()
    }
}