package com.example.swa2502.data.api

import com.example.swa2502.data.dto.auth.request.LoginRequest
import com.example.swa2502.data.dto.auth.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    // 실제 API 요청을 보내는 함수를 작성
    @POST("api/v1/members/log-in")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse
}
