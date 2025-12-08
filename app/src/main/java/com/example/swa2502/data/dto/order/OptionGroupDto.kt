package com.example.swa2502.data.dto.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
//사용
@Serializable
data class OptionGroupDto(
    @SerialName("groupId")
    val groupId: Int,
    @SerialName("name")
    val name: String,
    @SerialName("required")
    val required: Boolean,
    @SerialName("min")
    val min: Int,
    @SerialName("max")
    val max: Int,
    @SerialName("options")
    val options: List<OptionItemDto>
)