package com.example.swa2502.domain.usecase.order

import com.example.swa2502.domain.repository.OrderRepository
import javax.inject.Inject

class UpdateCartItemQuantityUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    /**
     * 장바구니 항목 수량 변경
     * @param cartItemId 카트 항목 ID
     * @param quantity 변경할 수량
     */
    suspend operator fun invoke(cartItemId: Int, quantity: Int): Result<Unit> {
        return repository.updateCartItemQuantity(cartItemId, quantity)
    }
}