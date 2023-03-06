package com.nocapstone.community.dto

import com.nocapstone.common_ui.ImageInfo

data class PostDetailData(
    val post : PostData
)

data class PostData(
    val id: Int,
    val date: String,
    val title: String,
    val images: List<ImageInfo>?,
    val content: String,
    val author : Int
)

