package com.example.swa2502.domain.repository

import com.example.swa2502.domain.model.RestaurantWithShops

interface RestaurantRepository {
    // 식당 조회, 레스토랑 조회와 같은 작업을 수행
    suspend fun getRestaurantsWithShops(): Result<List<RestaurantWithShops>>
}