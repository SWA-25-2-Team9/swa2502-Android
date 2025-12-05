package com.example.swa2502.domain.model

data class OptionItem(
    val id: Int,
    val name: String,
    val price: Int, // 추가 가격 (0원 이상)
)
