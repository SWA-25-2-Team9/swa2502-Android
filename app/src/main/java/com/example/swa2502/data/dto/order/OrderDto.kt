package com.example.swa2502.data.dto.order

import com.google.gson.annotations.SerializedName

data class OrderRequestDto(
    @SerializedName("paymentMethod")
    val paymentMethod: String // "CARD", "SIMPLE"
)

data class OrderResponseDto(
    @SerializedName("orderId")
    val orderId: Int,
    @SerializedName("orderNumber")
    val orderNumber: Int,
    @SerializedName("shopName")
    val shopName: String,
    @SerializedName("myTurn")
    val myTurn: Int,
    @SerializedName("etaMinutes")
    val etaMinutes: Int,
    @SerializedName("totalPrice")
    val totalPrice: Int,
    @SerializedName("orderedAt")
    val orderedAt: String,
    @SerializedName("items")
    val items: List<OrderItemDto>
)

data class OrderItemDto(
    @SerializedName("menuName")
    val menuName: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("options")
    val options: List<String>
)

class OrderDto {
}
