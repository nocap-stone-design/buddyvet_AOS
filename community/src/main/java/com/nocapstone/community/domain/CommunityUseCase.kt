package com.nocapstone.community.domain

import com.nocapstone.common.data.dto.CommonResponse
import com.nocapstone.community.data.CommunityService
import com.nocapstone.community.dto.Post
import com.nocapstone.community.dto.PostDetailData
import okhttp3.MultipartBody
import javax.inject.Inject

class CommunityUseCase @Inject constructor(
    private val communityService: CommunityService
){
    suspend fun readPostList(token: String): List<Post> {
        return communityService.readDiaryList(token).data.posts!!
    }

    suspend fun createPost(token: String, createDiaryRequest: CreatePostRequest): Int {
        return communityService.createDiary(token, createDiaryRequest).data.postId
    }

    suspend fun createPostImage(
        token: String,
        postId: Int,
        images: List<MultipartBody.Part>
    ): CommonResponse<String?> {
        return communityService.createDiaryImage(token, postId.toLong(), images)
    }

    suspend fun readPostDetail(token: String, diaryId: Int): PostDetailData {
        return communityService.readDiaryDetail(token, diaryId.toLong()).data
    }

}