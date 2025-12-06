package com.example.swa2502.data.dto.queue.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QueueInfoDto(
    @SerialName("restaurantId")
    val restaurantId: Int,
    @SerialName("name")
    val restaurantName: String,
    @SerialName("occupancyRate")
    val occupancyRate: Double,
    @SerialName("occupiedSeats")
    val occupiedSeats: Int,
    @SerialName("totalSeats")
    val totalSeats: Int,
    @SerialName("level")
    val level: String
)

