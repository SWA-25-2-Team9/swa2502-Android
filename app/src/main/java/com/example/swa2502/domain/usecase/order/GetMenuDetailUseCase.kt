package com.example.swa2502.domain.usecase.order

import com.example.swa2502.domain.model.MenuDetail
import com.example.swa2502.domain.repository.OrderRepository
import javax.inject.Inject

class GetMenuDetailUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    /**
     * 특정 메뉴의 상세 옵션 정보 조회
     * @param menuId 메뉴 ID
     * @return 메뉴 상세 정보
     */
    suspend operator fun invoke(menuId: Int): Result<MenuDetail> {
        return repository.getMenuDetail(menuId)
    }
}