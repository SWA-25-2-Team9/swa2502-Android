package com.example.swa2502.data.dto.auth.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("userId")
    val userId: String,
    @SerialName("password")
    val password: String
)
