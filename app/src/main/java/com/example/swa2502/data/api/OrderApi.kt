package com.example.swa2502.data.api

import com.example.swa2502.domain.model.MenuItem
interface OrderApi {
    // 실제 API 요청을 보내는 함수를 작성
    suspend fun getMenuList(
        restaurantId: Int
    ): List<MenuItem>
}