package com.example.swa2502.data.api

import com.example.swa2502.data.dto.queue.response.QueueInfoDto
import com.example.swa2502.data.dto.queue.response.RestaurantCongestionDto
import retrofit2.http.GET
import retrofit2.http.Path

interface QueueApi {
    // 실제 API 요청을 보내는 함수를 작성
    @GET("api/v1/restaurants")
    suspend fun getQueueInfo(): List<QueueInfoDto>

    @GET("api/v1/restaurants/{restaurantId}/shops/congestion")
    suspend fun getRestaurantCongestion(
        @Path("restaurantId") restaurantId: Int
    ): RestaurantCongestionDto
}