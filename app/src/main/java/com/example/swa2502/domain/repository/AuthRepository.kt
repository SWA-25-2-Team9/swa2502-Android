package com.example.swa2502.domain.repository

import com.example.swa2502.data.dto.auth.response.LoginResponse

interface AuthRepository {
    // 로그인, 회원가입 등의 작업을 수행
    suspend fun login(userId: String, password: String): Result<LoginResponse>
}