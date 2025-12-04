package com.example.swa2502.data.datasource

import com.example.swa2502.data.api.RestaurantApi
import com.example.swa2502.data.dto.restaurant.response.RestaurantWithShopsDto
import javax.inject.Inject

class RestaurantDataSource @Inject constructor(
    private val api: RestaurantApi
) {
    /**
     * API를 통해 레스토랑과 가게 정보 목록 가져옴
     * @return List<RestaurantWithShopsDto>
     */
    suspend fun getRestaurantInfos(): List<RestaurantWithShopsDto> {
        return api.getRestaurantInfos()
    }
}