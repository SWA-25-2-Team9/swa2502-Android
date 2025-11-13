package com.example.swa2502.data.datasource

import com.example.swa2502.data.api.OrderApi
import javax.inject.Inject

class OrderDataSource @Inject constructor(
    private val api: OrderApi
) {

}