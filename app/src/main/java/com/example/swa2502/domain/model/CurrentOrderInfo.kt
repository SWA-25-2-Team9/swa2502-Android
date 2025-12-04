package com.example.swa2502.domain.model

data class OrderItem(
    val menuName: String,
    val quantity: Int,
    val price: Int,
    val options: List<String>
)

data class CurrentOrderInfo(
    val orderId: Int,
    val orderNumber: String,
    val shopName: String,
    val myTurn: Int,
    val etaMinutes: Int,
    val estimatedWaitTime: String,
    val totalPrice: Int,
    val orderedAt: String,
    val items: List<OrderItem>
)
