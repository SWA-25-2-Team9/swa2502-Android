package com.example.swa2502.domain.model

data class RestaurantWithShops(
    val restaurantId: Int,
    val name: String,
    val occupancyRate: Double,
    val shops: List<Shop>
)

