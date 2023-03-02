package com.nocapstone.diary.dto

import android.net.Uri

data class DiaryDetailData(
    val date: String,
    val title: String,
    val images: List<String>?,
    val content: String
)