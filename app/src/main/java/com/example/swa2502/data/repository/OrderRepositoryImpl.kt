package com.example.swa2502.data.repository

import com.example.swa2502.data.datasource.OrderDataSource
import com.example.swa2502.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val remote: OrderDataSource
): OrderRepository {

}