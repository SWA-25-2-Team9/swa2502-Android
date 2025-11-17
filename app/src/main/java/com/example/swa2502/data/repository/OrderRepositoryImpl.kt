package com.example.swa2502.data.repository

import com.example.swa2502.data.datasource.OrderDataSource
import com.example.swa2502.domain.repository.OrderRepository
import javax.inject.Inject
import com.example.swa2502.domain.model.MenuItem

class OrderRepositoryImpl @Inject constructor(
    private val remote: OrderDataSource
): OrderRepository {
    override suspend fun getMenuList(restaurantId: Int): Result<List<MenuItem>> {
        return try {
            val list = remote.fetchMenuList(restaurantId)
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}