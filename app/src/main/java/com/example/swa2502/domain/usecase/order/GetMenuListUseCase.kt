package com.example.swa2502.domain.usecase.order

import com.example.swa2502.domain.model.MenuItem
import com.example.swa2502.domain.repository.OrderRepository
import javax.inject.Inject

class GetMenuListUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    /**
     * 특정 가게의 메뉴 목록을 조회
     * @param shopId
     */
    suspend operator fun invoke(shopId: Int): Result<List<MenuItem>> {
        return repository.getMenuList(shopId)
    }
}