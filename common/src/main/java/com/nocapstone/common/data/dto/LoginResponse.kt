package com.nocapstone.common.data.dto

import com.nocapstone.common.data.entity.Jwt

data class LoginResponse(
    val data: String,
    val success: Boolean
)