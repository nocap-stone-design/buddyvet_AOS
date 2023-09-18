package com.nocapstone.common.data.source

import com.nocapstone.common.data.dto.CommonResponse
import com.nocapstone.common.data.dto.LoginResponse
import com.nocapstone.common.domain.entity.LoginRequest
import com.nocapstone.common.data.entity.Jwt
import com.nocapstone.common.domain.entity.UserNameRequest
import okhttp3.MultipartBody
import org.apache.commons.lang3.ObjectUtils.Null
import retrofit2.Response
import retrofit2.http.*

interface AuthService {
    @POST("oauth/login")
    suspend fun postToken(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("users/nickname")
    suspend fun postUserInfo(
        @Header("Authorization") token: String,
        @Body nickname: UserNameRequest
    ): CommonResponse<String?>

    @Multipart
    @POST("users/image")
    suspend fun uploadUserImg(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): CommonResponse<String?>


}