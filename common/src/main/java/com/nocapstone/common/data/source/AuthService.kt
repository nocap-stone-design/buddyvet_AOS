package com.nocapstone.common.data.source

import com.nocapstone.common.data.dto.CommonResponse
import com.nocapstone.common.data.dto.LoginResponse
import com.nocapstone.common.domain.entity.LoginRequest
import com.nocapstone.common.data.entity.Jwt
import com.nocapstone.common.domain.entity.UserNameRequest
import org.apache.commons.lang3.ObjectUtils.Null
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("oauth/login")
    suspend fun postToken(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("users/nickname")
    suspend fun postUserInfo(
        @Header("Authorization") token: String,
        @Body nickname: UserNameRequest
    ): CommonResponse<String?>

}