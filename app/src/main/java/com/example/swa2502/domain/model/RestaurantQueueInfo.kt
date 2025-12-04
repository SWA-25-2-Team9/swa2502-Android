package com.example.swa2502.domain.model

data class RestaurantQueueInfo(
    val restaurantId: String,
    val restaurantName: String,
    val restaurantState: String,
    val occupancyRate: Double,
    val occupiedSeats: Int,
    val totalSeats: Int,
    val shops: List<ShopQueueInfo>
)

