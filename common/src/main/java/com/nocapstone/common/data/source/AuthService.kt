package com.nocapstone.common.data.source

import com.nocapstone.common.data.dto.LoginRequest
import com.nocapstone.common.data.dto.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("api/v1/oauth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}