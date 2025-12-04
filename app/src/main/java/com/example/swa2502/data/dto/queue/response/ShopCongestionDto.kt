package com.example.swa2502.data.dto.queue.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShopCongestionDto(
    @SerialName("shopId")
    val shopId: Int,
    @SerialName("name")
    val name: String,
    @SerialName("currentQueue")
    val currentQueue: Int,
    @SerialName("etaMinutes")
    val etaMinutes: Int,
    @SerialName("isOpen")
    val isOpen: Boolean
)

