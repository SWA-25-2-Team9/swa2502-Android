package com.example.swa2502.domain.repository

import com.example.swa2502.domain.model.MenuItem
import com.example.swa2502.domain.model.CurrentOrderInfo

interface OrderRepository {
    suspend fun getMenuList(restaurantId: Int): Result<List<MenuItem>>

    // 나의 현재 주문 정보 조회
    suspend fun getCurrentOrder(): Result<List<CurrentOrderInfo>>
}