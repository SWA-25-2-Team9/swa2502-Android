package com.example.swa2502.core.network

import android.util.Log
import com.example.swa2502.BuildConfig
import com.example.swa2502.data.api.TokenApi
import com.example.swa2502.data.dto.auth.request.TokenReissueRequest
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        // 이미 재시도했던 요청인 경우 null을 반환하여 재시도 중단
        if (response.request.header("Retry-With-New-Token") != null) {
            return null
        }

        return runBlocking {
            try {
                val refreshToken = tokenManager.getRefreshToken().first()
                if (refreshToken == null) {
                    tokenManager.clearToken()
                    return@runBlocking null
                }

                // 토큰 재발급 요청
                val tokenService = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(OkHttpClient.Builder().build())
                    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                    .build()
                    .create(TokenApi::class.java)
                val tokenResponse = tokenService.reissueAccessToken(
                    TokenReissueRequest(refreshToken = refreshToken)
                )

                // 새로운 토큰 저장
                tokenManager.saveAccessToken(tokenResponse.accessToken)
                tokenManager.saveRefreshToken(tokenResponse.refreshToken)
                Log.d("TokenAuthenticator", "토큰 재발급 성공: ${tokenResponse.accessToken}")

                // 기존 요청에 새로운 토큰으로 헤더를 추가하여 재시도
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${tokenResponse.accessToken}")
                    .header("Retry-With-New-Token", "true")
                    .build()
            } catch (e: Exception) {
                // 토큰 재발급 실패 시 토큰 클리어
                Log.d("TokenAuthenticator", "토큰 재발급 실패: ${e.message}")
                tokenManager.clearToken()
                null
            }
        }
    }
}
