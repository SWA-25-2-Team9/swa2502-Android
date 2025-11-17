package com.example.swa2502.data.datasource

import com.example.swa2502.data.api.OrderApi
import com.example.swa2502.domain.model.MenuItem
import javax.inject.Inject


class OrderDataSource @Inject constructor(
    private val api: OrderApi
) {
    /**
     * API를 통해 메뉴 목록 가져옴
     * @param restaurantId
     * **/
    suspend fun fetchMenuList(restaurantId: Int): List<MenuItem>{
        return api.getMenuList(restaurantId)
    }
}