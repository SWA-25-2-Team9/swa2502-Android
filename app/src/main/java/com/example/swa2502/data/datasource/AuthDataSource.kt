package com.example.swa2502.data.datasource

import com.example.swa2502.data.api.AuthApi
import com.example.swa2502.data.dto.auth.request.LoginRequest
import com.example.swa2502.data.dto.auth.response.LoginResponse
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val api: AuthApi
) {
    /**
     * API를 통해 로그인 요청
     * @param userId 사용자 ID
     * @param password 비밀번호
     * @return LoginResponse
     */
    suspend fun login(userId: String, password: String): LoginResponse {
        return api.login(LoginRequest(userId, password))
    }
}