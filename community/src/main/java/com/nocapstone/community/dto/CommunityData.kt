package com.nocapstone.community.dto

data class CommunityData(
    val posts: List<Post>?
)

data class Post(
    val postId: Long,
    val title: String,
    val thumbnail: String,
    val replyCount : Int
)

data class PostId(
    val postId: Long
)