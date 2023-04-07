package com.nocapstone.community.dto

data class CommunityData(
    val posts: List<Post>?
)

data class Post(
    val postId: Int,
    val title: String,
    val thumbnail: String,
    val replyCount : Int
)

data class PostId(
    val postId: Int
)