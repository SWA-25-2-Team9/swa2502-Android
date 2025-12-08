package com.example.swa2502.data.dto.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartAddResponseDto(
    @SerialName("cartItemId")
    val cartItemId: Int,
    @SerialName("cartCount")
    val cartCount: Int
)