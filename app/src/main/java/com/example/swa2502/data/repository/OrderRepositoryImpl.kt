package com.example.swa2502.data.repository

import com.example.swa2502.data.datasource.OrderDataSource
import com.example.swa2502.domain.repository.OrderRepository

class OrderRepositoryImpl(
    private val remote: OrderDataSource
): OrderRepository {

}