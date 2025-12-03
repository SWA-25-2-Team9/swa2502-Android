package com.example.swa2502.domain.usecase.auth

import com.example.swa2502.data.dto.auth.response.LoginResponse
import com.example.swa2502.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(userId: String, password: String): Result<LoginResponse> {
        return repository.login(userId, password)
    }
}

