package com.example.swa2502.data.dto.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
//시용

@Serializable
data class ClearShoppingCartDto (
    @SerialName("message")
    val message: String
)