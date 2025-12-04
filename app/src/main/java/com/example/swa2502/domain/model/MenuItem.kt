package com.example.swa2502.domain.model

data class MenuItem(
    val menuId: Int,
    val name: String,
    val price: Int,
    val imageUrl: String? = null,
    val isSoldOut: Boolean
)
