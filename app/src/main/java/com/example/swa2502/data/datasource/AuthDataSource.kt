package com.example.swa2502.data.datasource

import com.example.swa2502.data.api.AuthApi
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val api: AuthApi
) {

}