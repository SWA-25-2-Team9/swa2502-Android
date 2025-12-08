package com.example.swa2502.data.dto.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
//사용
@Serializable
data class CurrentOrderResponseDto(
    @SerialName("orderId")
    val orderId: Int,
    @SerialName("orderNumber")
    val orderNumber: Int,
    @SerialName("shopName")
    val shopName: String,
    @SerialName("myTurn")
    val myTurn: Int,
    @SerialName("etaMinutes")
    val etaMinutes: Int,
    @SerialName("totalPrice")
    val totalPrice: Int,
    @SerialName("orderedAt")
    val orderedAt: String,
    @SerialName("items")
    val items: List<ItemDto>
)

@Serializable
data class ItemDto(
    @SerialName("menuName")
    val menuName: String,
    @SerialName("quantity")
    val quantity: Int,
    @SerialName("price")
    val price: Int,
    @SerialName("options")
    val options: List<String>
)