package com.example.swa2502.domain.model

data class OptionGroup(
    val id: Int,
    val name: String,
    val isRequired: Boolean, // 필수 선택 여부
    val options: List<OptionItem>,
    val selectedOptionId: Int? = null // UI에서 사용: 선택된 옵션 ID (기본적으로 첫 번째 옵션 선택을 추천)
)