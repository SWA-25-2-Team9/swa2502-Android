package com.example.swa2502.domain.model

data class MenuDetail(
    val menuId: Int,
    val name: String,
    val basePrice: Int,
    val optionGroups: List<OptionGroup>
)
