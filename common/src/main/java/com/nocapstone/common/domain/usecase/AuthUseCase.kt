package com.nocapstone.common.domain.usecase

import android.util.Log
import com.nocapstone.common.data.dto.LoginResponse
import com.nocapstone.common.data.entity.Jwt
import com.nocapstone.common.domain.entity.LoginRequest
import com.nocapstone.common.data.source.AuthService
import retrofit2.Response
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authService: AuthService
){
    suspend fun login(token: String, providerType: String= "KAKAO") : Response<LoginResponse> {
        val jwt = authService.postToken(LoginRequest(token, providerType))
        return jwt
    }

    suspend fun inputUserInfo(token:String , nickname: String){
        authService.postUserInfo(token,nickname)
    }
}