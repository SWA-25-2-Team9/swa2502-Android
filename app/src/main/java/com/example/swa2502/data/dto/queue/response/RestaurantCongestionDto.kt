package com.example.swa2502.data.dto.queue.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantCongestionDto(
    @SerialName("restaurantId")
    val restaurantId: Int,
    @SerialName("restaurantName")
    val name: String,
    @SerialName("occupancyRate")
    val occupancyRate: Double,
    @SerialName("shops")
    val shops: List<ShopCongestionDto>
)

