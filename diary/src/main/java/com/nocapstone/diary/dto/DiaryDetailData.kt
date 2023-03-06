package com.nocapstone.diary.dto

import com.nocapstone.common_ui.ImageInfo


data class DiaryDetailData(
    val date: String,
    val title: String,
    val images: List<ImageInfo>?,
    val content: String
)

