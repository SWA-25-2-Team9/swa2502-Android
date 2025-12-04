package com.example.swa2502.data.repository

import com.example.swa2502.data.datasource.QueueDataSource
import com.example.swa2502.domain.model.RestaurantInfo
import com.example.swa2502.domain.model.RestaurantQueueInfo
import com.example.swa2502.domain.model.ShopQueueInfo
import com.example.swa2502.domain.repository.QueueRepository
import javax.inject.Inject

class QueueRepositoryImpl @Inject constructor(
    private val remote: QueueDataSource
): QueueRepository {
    
    override suspend fun getQueueInfo(): Result<List<RestaurantInfo>> {
        return runCatching {
            val dtoList = remote.getQueueInfo()
            dtoList.map { dto ->
                val occupancyRate = dto.occupancyRate
                val restaurantState = when {
                    occupancyRate < 0.3 -> "여유"
                    occupancyRate < 0.7 -> "조금 혼잡"
                    else -> "혼잡"
                }
                
                RestaurantInfo(
                    restaurantId = dto.restaurantId.toString(),
                    restaurantName = dto.restaurantName,
                    restaurantState = restaurantState,
                    occupiedSeats = dto.occupiedSeats,
                    totalSeats = dto.totalSeats
                )
            }
        }
    }

    override suspend fun getRestaurantCongestion(restaurantId: Int): Result<RestaurantQueueInfo> {
        return runCatching {
            val dto = remote.getRestaurantCongestion(restaurantId)
            val occupancyRate = dto.occupancyRate
            val restaurantState = when {
                occupancyRate < 0.3 -> "여유"
                occupancyRate < 0.7 -> "조금 혼잡"
                else -> "혼잡"
            }
            
            RestaurantQueueInfo(
                restaurantId = dto.restaurantId.toString(),
                restaurantName = dto.name,
                restaurantState = restaurantState,
                occupancyRate = dto.occupancyRate,
                occupiedSeats = 0, // API 응답에 없으므로 기본값
                totalSeats = 0, // API 응답에 없으므로 기본값
                shops = dto.shops.map { shopDto ->
                    val shopState = when {
                        shopDto.currentQueue < 5 -> "여유"
                        shopDto.currentQueue < 10 -> "조금 혼잡"
                        else -> "혼잡"
                    }
                    
                    ShopQueueInfo(
                        shopId = shopDto.shopId.toString(),
                        shopName = shopDto.name,
                        shopState = shopState,
                        waitingTime = shopDto.etaMinutes,
                        ordersDone = emptyList(), // API 응답에 없으므로 빈 리스트
                        ordersInProgress = List(shopDto.currentQueue) { it } // currentQueue 수만큼 생성
                    )
                }
            )
        }
    }
}