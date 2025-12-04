package com.example.swa2502.data.dto.restaurant.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantWithShopsDto(
    @SerialName("restaurantId")
    val restaurantId: Int,
    @SerialName("name")
    val name: String,
    @SerialName("occupancyRate")
    val occupancyRate: Double,
    @SerialName("shops")
    val shops: List<ShopDto>
)

