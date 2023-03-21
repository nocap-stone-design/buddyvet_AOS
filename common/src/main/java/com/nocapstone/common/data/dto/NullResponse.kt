package com.nocapstone.common.data.dto

import com.nocapstone.common.data.entity.Error

data class NullResponse(
    val data: String?,
    val success: Boolean,
    val error: Error?
)