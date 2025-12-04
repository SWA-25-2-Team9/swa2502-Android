package com.example.swa2502.data.repository

import com.example.swa2502.data.datasource.RestaurantDataSource
import com.example.swa2502.domain.model.RestaurantWithShops
import com.example.swa2502.domain.model.Shop
import com.example.swa2502.domain.repository.RestaurantRepository
import javax.inject.Inject

class RestaurantRepositoryImpl @Inject constructor(
    private val remote: RestaurantDataSource
): RestaurantRepository {
    
    override suspend fun getRestaurantsWithShops(): Result<List<RestaurantWithShops>> {
        return runCatching {
            val dtoList = remote.getRestaurantInfos()
            dtoList.map { dto ->
                RestaurantWithShops(
                    restaurantId = dto.restaurantId,
                    name = dto.name,
                    occupancyRate = dto.occupancyRate,
                    shops = dto.shops.map { shopDto ->
                        Shop(
                            shopId = shopDto.shopId,
                            name = shopDto.name,
                            currentQueue = shopDto.currentQueue,
                            etaMinutes = shopDto.etaMinutes,
                            isOpen = shopDto.isOpen
                        )
                    }
                )
            }
        }
    }
}