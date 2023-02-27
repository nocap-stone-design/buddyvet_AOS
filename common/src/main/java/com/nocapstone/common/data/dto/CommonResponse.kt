package com.nocapstone.common.data.dto
import com.nocapstone.common.data.entity.Error

data class CommonResponse<T>(
    val data: T,
    val success: Boolean,
    val error : Error
)