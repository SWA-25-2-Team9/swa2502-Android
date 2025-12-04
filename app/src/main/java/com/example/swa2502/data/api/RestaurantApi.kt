package com.example.swa2502.data.api

import com.example.swa2502.data.dto.restaurant.response.RestaurantWithShopsDto
import retrofit2.http.GET

interface RestaurantApi {
    // 실제 API 요청을 보내는 함수를 작성
    @GET("api/v1/restaurants/with-shops")
    suspend fun getRestaurantInfos(): List<RestaurantWithShopsDto>
}