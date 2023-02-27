package com.nocapstone.common.data.source

import com.nocapstone.common.data.dto.CommonResponse
import com.nocapstone.common.data.dto.LoginResponse
import com.nocapstone.common.domain.entity.LoginRequest
import com.nocapstone.common.data.entity.Jwt
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("oauth/login")
    suspend fun postToken(@Body loginRequest: LoginRequest): LoginResponse

}