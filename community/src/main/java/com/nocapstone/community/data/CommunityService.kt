package com.nocapstone.community.data

import com.nocapstone.common.data.dto.CommonResponse
import com.nocapstone.community.domain.CreatePostRequest
import com.nocapstone.community.dto.CommunityData
import com.nocapstone.community.dto.PostDetailData
import com.nocapstone.community.dto.PostId
import okhttp3.MultipartBody
import retrofit2.http.*

interface CommunityService {

    @GET("community")
    suspend fun readDiaryList(
        @Header("Authorization") token: String
    ): CommonResponse<CommunityData>

    @POST("community")
    suspend fun createDiary(
        @Header("Authorization") token: String,
        @Body createDiaryRequest: CreatePostRequest
    ): CommonResponse<PostId>


    @Multipart
    @POST("community/{postId}/image")
    suspend fun createDiaryImage(
        @Header("Authorization") token: String,
        @Path("postId") postId: Long,
        @Part image: List<MultipartBody.Part>
    ): CommonResponse<String?>


    @GET("community/{postId}")
    suspend fun readDiaryDetail(
        @Header("Authorization") token: String,
        @Path("postId") postId: Long,
    ): CommonResponse<PostDetailData>

}