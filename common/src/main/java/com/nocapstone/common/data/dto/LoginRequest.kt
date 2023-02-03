package com.nocapstone.common.data.dto

data class LoginRequest(
    val accessToken: String,
    val providerType: String
)
