package com.example.swa2502.data.dto.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ClearShoppingCartDto (
    @SerialName("message")
    val message: String
)