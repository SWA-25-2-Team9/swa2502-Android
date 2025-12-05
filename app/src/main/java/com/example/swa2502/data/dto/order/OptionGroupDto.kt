package com.example.swa2502.data.dto.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OptionGroupDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("isRequired")
    val isRequired: Boolean, // API에서 필수 여부 제공 가정
    @SerialName("options")
    val options: List<OptionItemDto>
)