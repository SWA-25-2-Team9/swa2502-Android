package com.example.swa2502.data.dto.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
//사용
@Serializable
data class MenuDetailDto(
    @SerialName("menuId")
    val menuId: Int,
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: Int,
    @SerialName("optionGroups")
    val optionGroups: List<OptionGroupDto>
)