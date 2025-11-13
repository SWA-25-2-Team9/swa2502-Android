package com.example.swa2502.data.datasource

import com.example.swa2502.data.api.RestaurantApi
import javax.inject.Inject

class RestaurantDataSource @Inject constructor(
    private val api: RestaurantApi
) {
}