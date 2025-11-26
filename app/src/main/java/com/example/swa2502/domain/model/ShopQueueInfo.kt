package com.example.swa2502.domain.model

data class ShopQueueInfo(
    val shopId: String,
    val shopName: String,
    val shopState: String,
    val ordersDone: List<Int>,
    val ordersInProgress: List<Int>
)
