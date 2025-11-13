package com.example.swa2502.data.api

import com.example.swa2502.data.dto.auth.request.TokenReissueRequest
import com.example.swa2502.data.dto.auth.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenApi {
    // TODO: 토큰 재발급시에 사용, 엔드포인트는 수정 필요
    @POST("reissue url")
    suspend fun reissueAccessToken(
        @Body request: TokenReissueRequest
    ): TokenResponse
}