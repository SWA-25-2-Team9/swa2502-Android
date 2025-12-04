package com.example.swa2502.domain.repository

import com.example.swa2502.domain.model.RestaurantInfo
import com.example.swa2502.domain.model.RestaurantQueueInfo

interface QueueRepository {
    // 대기열 조회, 혼잡도 조회와 같은 작업을 수행
    suspend fun getQueueInfo(): Result<List<RestaurantInfo>>
    suspend fun getRestaurantCongestion(restaurantId: Int): Result<RestaurantQueueInfo>
}