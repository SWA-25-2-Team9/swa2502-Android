package com.example.swa2502.domain.usecase.order

import com.example.swa2502.domain.repository.OrderRepository
import javax.inject.Inject

class AddCartUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    /**
     * 장바구니에 메뉴 추가
     * @param menuId 메뉴 ID
     * @param quantity 수량
     * @param selectedOptions 선택된 옵션 ID 목록
     * @return 장바구니 항목 수
     */
    suspend operator fun invoke(
        menuId: Int,
        quantity: Int,
        selectedOptions: List<Long>
    ): Result<Int> {
        return repository.addCart(menuId, quantity, selectedOptions)
    }
}

