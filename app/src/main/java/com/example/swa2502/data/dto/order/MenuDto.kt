package com.example.swa2502.data.dto.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuDto(
    @SerialName("menuId")
    val menuId: Int,
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: Int,
    @SerialName("imageUrl")
    val imageUrl: String? = null,
    @SerialName("isSoldOut")
    val isSoldOut: Boolean
)