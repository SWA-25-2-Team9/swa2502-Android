package com.example.swa2502.data.dto.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
//사용
@Serializable
data class OrderRequestDto (
    @SerialName("request")
    val request: String
)