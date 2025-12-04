package com.example.swa2502.domain.usecase.shop

import com.example.swa2502.domain.model.RestaurantWithShops
import com.example.swa2502.domain.repository.RestaurantRepository
import javax.inject.Inject

class GetRestaurantsWithShopsUseCase @Inject constructor(
    private val repository: RestaurantRepository
) {
    /**
     * 모든 레스토랑과 해당 레스토랑에 속한 가게 정보 조회
     * @return 레스토랑과 가게 목록
     */
    suspend operator fun invoke(): Result<List<RestaurantWithShops>> {
        return repository.getRestaurantsWithShops()
    }
}

