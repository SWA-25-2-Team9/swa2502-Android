package com.example.swa2502.data.repository

import com.example.swa2502.data.datasource.OrderDataSource
import com.example.swa2502.domain.repository.OrderRepository
import com.example.swa2502.domain.model.CurrentOrderInfo
import com.example.swa2502.domain.model.MenuItem
import com.example.swa2502.domain.model.OrderItem
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val remote: OrderDataSource
): OrderRepository {
    override suspend fun getMenuList(restaurantId: Int): Result<List<MenuItem>> {
        return try {
            val list = remote.fetchMenuList(restaurantId)
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCurrentOrder(): Result<List<CurrentOrderInfo>> {
        return runCatching {
            remote.getCurrentOrder().map { dto ->
                CurrentOrderInfo(
                    orderId = dto.orderId,
                    orderNumber = "${dto.orderNumber}번", // UI 표시 형식으로 변환
                    shopName = dto.shopName,
                    myTurn = dto.myTurn,
                    etaMinutes = dto.etaMinutes,
                    estimatedWaitTime = "약 ${dto.etaMinutes}분 예상", // UI 표시 형식으로 변환
                    totalPrice = dto.totalPrice,
                    orderedAt = dto.orderedAt,
                    items = dto.items.map { itemDto ->
                        OrderItem(
                            menuName = itemDto.menuName,
                            quantity = itemDto.quantity,
                            price = itemDto.price,
                            options = itemDto.options
                        )
                    }
                )
            }
        }
    }
}