package com.example.swa2502.domain.usecase.order

import com.example.swa2502.domain.model.CartStore
import com.example.swa2502.domain.repository.OrderRepository
import javax.inject.Inject

class GetShoppingCartInfoUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    /**
     * 장바구니 정보 조회
     * @return 장바구니 가게 정보 목록
     */
    suspend operator fun invoke(): Result<List<CartStore>> {
        return repository.getShoppingCartInfo()
    }
}