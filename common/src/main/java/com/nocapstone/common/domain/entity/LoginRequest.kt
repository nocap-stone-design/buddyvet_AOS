package com.nocapstone.common.domain.entity

data class LoginRequest(
    val accessToken: String,
    val providerType: String
)
