package com.nocapstone.common.data.dto

import com.nocapstone.common.data.entity.Data

data class LoginResponse(
    val `data`: Data,
    val success: Boolean
)