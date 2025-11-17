package com.example.swa2502.domain.repository

import com.example.swa2502.domain.model.MenuItem

interface OrderRepository {
    suspend fun getMenuList(restaurantId: Int): Result<List<MenuItem>>
}