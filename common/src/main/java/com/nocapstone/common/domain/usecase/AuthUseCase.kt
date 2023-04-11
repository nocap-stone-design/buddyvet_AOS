package com.nocapstone.common.domain.usecase

import android.util.Log
import com.nocapstone.common.data.dto.LoginResponse
import com.nocapstone.common.data.entity.Jwt
import com.nocapstone.common.domain.entity.LoginRequest
import com.nocapstone.common.data.source.AuthService
import com.nocapstone.common.domain.entity.UserNameRequest
import retrofit2.Response
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authService: AuthService
){
    suspend fun login(token: String, providerType: String = "KAKAO"): Response<LoginResponse> {
        return authService.postToken(LoginRequest(token, providerType))
    }

    suspend fun inputUserInfo(token:String , nickname: UserNameRequest){
        authService.postUserInfo(token,nickname)
    }
}