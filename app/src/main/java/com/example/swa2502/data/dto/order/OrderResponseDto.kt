package com.example.swa2502.data.dto.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
//사용
@Serializable
data class OrderResponseDto (
    @SerialName("orderId")
    val orderId: Int,
    @SerialName("orderNumber")
    val orderNumber: Int,
    @SerialName("myTurn")
    val myTurn: Int,
    @SerialName("etaMinutes")
    val etaMinutes: Int
)