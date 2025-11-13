package com.example.swa2502.data.repository

import com.example.swa2502.data.datasource.AuthDataSource
import com.example.swa2502.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remote: AuthDataSource
    // 이후에 토큰 사용시 local로 Token 관련 클래스 추가
): AuthRepository {

}