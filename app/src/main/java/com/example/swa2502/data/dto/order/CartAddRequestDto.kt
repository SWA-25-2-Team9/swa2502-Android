package com.example.swa2502.data.dto.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
//사용
@Serializable
data class CartAddRequestDto (
    @SerialName("menuId")
    val menuId: Int,
    @SerialName("quantity")
    val quantity: Int,
    @SerialName("selectedOptions")
    val selectedOptions: List<Long>
)