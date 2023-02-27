package com.nocapstone.common.domain.usecase

import android.util.Log
import com.nocapstone.common.domain.entity.LoginRequest
import com.nocapstone.common.data.source.AuthService
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authService: AuthService
){
    suspend fun login(token: String, providerType: String= "KAKAO") : String{
        val jwt = authService.postToken(LoginRequest(token, providerType)).data.jwt
        Log.d("buddyTest","jwt 반환 성공 $jwt")
        return jwt
    }
}