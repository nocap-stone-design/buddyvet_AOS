package com.nocapstone.community.dto

import com.nocapstone.common_ui.ImageInfo

data class PostDetailData(
    val post: PostData
)

data class PostData(
    val id: Long,
    val date: String,
    val title: String,
    val images: List<ImageInfo>?,
    val content: String,
    val authorId: Long,
    val authorNickname: String,
    val authorProfile : String,
    val replyCount: Int,
    val reply: List<Reply>
)

data class Reply(
    val id: Long,
    val authorId: Long,
    val authorNickname: String,
    val authorProfile : String,
    val content: String,
    val date: String
)

data class Content(
    val content : String

)





