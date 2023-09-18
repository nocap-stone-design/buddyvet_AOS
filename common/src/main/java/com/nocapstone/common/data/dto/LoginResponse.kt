package com.nocapstone.common.data.dto

import com.nocapstone.common.data.entity.Jwt
import com.nocapstone.common.data.entity.Error

data class LoginResponse(
    val data: Jwt,
    val success: Boolean,
    val error : Error
)