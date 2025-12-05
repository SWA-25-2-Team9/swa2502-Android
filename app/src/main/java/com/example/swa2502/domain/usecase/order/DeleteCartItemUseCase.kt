package com.example.swa2502.domain.usecase.order

import com.example.swa2502.domain.repository.OrderRepository
import javax.inject.Inject

class DeleteCartItemUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    /**
     * 장바구니 항목 삭제
     * @param cartItemId 카트 항목 ID
     * @return 남은 카트 항목 수
     */
    suspend operator fun invoke(cartItemId: Int): Result<Int> {
        return repository.deleteCartItem(cartItemId)
    }
}