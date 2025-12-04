package com.example.swa2502.domain.model

data class Shop(
    val shopId: Int,
    val name: String,
    val currentQueue: Int,
    val etaMinutes: Int,
    val isOpen: Boolean
)

