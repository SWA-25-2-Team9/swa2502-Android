package com.example.swa2502.domain.model

// 주문 관리 화면에서 사용할 도메인 모델
data class Order(
    val id: String,
    val orderNumber: String,
    val orderState: String,
    val menus: List<String>,
    val timeStamp: String
)
