package com.example.swa2502.data.repository

import com.example.swa2502.data.datasource.RestaurantDataSource
import com.example.swa2502.domain.repository.RestaurantRepository

class RestaurantRepositoryImpl(
    private val remote: RestaurantDataSource
): RestaurantRepository {

}