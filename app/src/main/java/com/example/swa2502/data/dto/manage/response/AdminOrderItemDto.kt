package com.example.swa2502.data.dto.manage.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdminOrderItemDto(
    @SerialName("orderItemId")
    val orderItemId: Int,
    @SerialName("orderId")
    val orderId: Int,
    @SerialName("menuName")
    val menuName: String,
    @SerialName("quantity")
    val quantity: Int,
    @SerialName("price")
    val price: Int,
    @SerialName("selectedOptionIds")
    val selectedOptionIds: List<Int>,
    @SerialName("status")
    val status: String
)

