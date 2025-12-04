package com.example.swa2502.data.datasource

import com.example.swa2502.data.api.QueueApi
import com.example.swa2502.data.dto.queue.response.QueueInfoDto
import com.example.swa2502.data.dto.queue.response.RestaurantCongestionDto
import javax.inject.Inject

class QueueDataSource @Inject constructor(
    private val api: QueueApi
) {
    /**
     * API를 통해 대기열 및 혼잡도 정보 가져옴
     * @return List<QueueInfoDto>
     */
    suspend fun getQueueInfo(): List<QueueInfoDto> {
        return api.getQueueInfo()
    }

    /**
     * 특정 레스토랑의 가게별 혼잡도 정보 가져옴
     * @param restaurantId 레스토랑 ID
     * @return RestaurantCongestionDto
     */
    suspend fun getRestaurantCongestion(restaurantId: Int): RestaurantCongestionDto {
        return api.getRestaurantCongestion(restaurantId)
    }
}