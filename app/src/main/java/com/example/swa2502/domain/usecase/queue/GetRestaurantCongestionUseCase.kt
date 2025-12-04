package com.example.swa2502.domain.usecase.queue

import com.example.swa2502.domain.model.RestaurantQueueInfo
import com.example.swa2502.domain.repository.QueueRepository
import javax.inject.Inject

class GetRestaurantCongestionUseCase @Inject constructor(
    private val repository: QueueRepository
) {
    /**
     * 특정 레스토랑의 가게별 혼잡도 정보 조회
     * @param restaurantId 레스토랑 ID
     * @return 레스토랑 혼잡도 및 가게 정보
     */
    suspend operator fun invoke(restaurantId: Int): Result<RestaurantQueueInfo> {
        return repository.getRestaurantCongestion(restaurantId)
    }
}

