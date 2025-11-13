package com.example.swa2502.data.dto.auth.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenReissueRequest(
    @SerialName("refreshToken")
    val refreshToken: String,
)