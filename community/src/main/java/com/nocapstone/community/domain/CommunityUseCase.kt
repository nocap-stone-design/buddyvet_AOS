package com.nocapstone.community.domain

import com.nocapstone.common.data.dto.CommonResponse
import com.nocapstone.community.data.CommunityService
import com.nocapstone.community.dto.Content
import com.nocapstone.community.dto.Post
import com.nocapstone.community.dto.PostDetailData
import com.nocapstone.community.dto.PostId
import okhttp3.MultipartBody
import javax.inject.Inject

class CommunityUseCase @Inject constructor(
    private val communityService: CommunityService
) {
    suspend fun readPostList(token: String): List<Post> {
        return communityService.readDiaryList(token).data.posts!!
    }

    suspend fun createPost(token: String, createDiaryRequest: CreatePostRequest): Long {
        return communityService.createDiary(token, createDiaryRequest).data.postId
    }

    suspend fun createPostImage(
        token: String,
        postId: Long,
        images: List<MultipartBody.Part>
    ): CommonResponse<String?> {
        return communityService.createDiaryImage(token, postId, images)
    }

    suspend fun readPostDetail(token: String, postId: Long): PostDetailData {
        return communityService.readDiaryDetail(token, postId).data
    }


    suspend fun createReply(
        token: String,
        postId: Long,
        content: Content
    ): CommonResponse<String?> {
        return communityService.createReply(token, postId, content)
    }

    suspend fun deleteReply(
        token: String,
        postId: Long
    ): CommonResponse<String?> {
        return communityService.deleteReply(token, postId)
    }

}