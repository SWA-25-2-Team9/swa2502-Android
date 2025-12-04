package com.example.swa2502.domain.usecase.queue

import com.example.swa2502.domain.model.RestaurantInfo
import com.example.swa2502.domain.repository.QueueRepository
import javax.inject.Inject

class GetQueueInfoUseCase @Inject constructor(
    private val repository: QueueRepository
) {
    /**
     * 모든 레스토랑의 대기열 및 혼잡도 정보 조회
     * @return 레스토랑 대기열 정보 목록
     */
    suspend operator fun invoke(): Result<List<RestaurantInfo>> {
        return repository.getQueueInfo()
    }
}
