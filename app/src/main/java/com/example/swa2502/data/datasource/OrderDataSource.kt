package com.example.swa2502.data.datasource

import com.example.swa2502.data.api.OrderApi
import com.example.swa2502.domain.model.MenuItem
import com.example.swa2502.data.dto.order.MenuDto
import javax.inject.Inject
import com.example.swa2502.data.dto.order.CurrentOrderResponseDto

class OrderDataSource @Inject constructor(
    private val api: OrderApi
) {
    /**
     * API를 통해 메뉴 목록 가져옴
     * @param shopId
     * **/
    suspend fun fetchMenuList(shopId: Int): List<MenuDto>{
        return api.getMenuList(shopId)
    }

    /**
     * API를 통해 나의 현재 주문 정보 가져옴
     * @return List<CurrentOrderResponseDto>
     */
    suspend fun getCurrentOrder(): List<CurrentOrderResponseDto> {
        return api.getCurrentOrder()
    }
}