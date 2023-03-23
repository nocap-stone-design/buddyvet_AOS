package com.nocapstone.buddyvet.buddy.data

import com.nocapstone.buddyvet.buddy.domain.entity.*
import com.nocapstone.common.data.dto.CommonResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface BuddyService {

    @GET("buddies")
    suspend fun readBuddyList(
        @Header("Authorization") token: String
    ): CommonResponse<BuddyListResponse>

    @POST("buddies")
    suspend fun createBuddy(
        @Header("Authorization") token: String,
        @Body buddyRequest: BuddyRequest
    ): CommonResponse<BuddyId>

    @GET("buddies/{buddyId}")
    suspend fun readBuddyDetail(
        @Header("Authorization") token: String,
        @Path("buddyId") buddyId: Long,
    ): CommonResponse<BuddyDetailResponse>

    //TODO Refactor PATCH


    @DELETE("buddies/{buddyId}")
    suspend fun deleteBuddy(
        @Header("Authorization") token: String,
        @Path("buddyId") buddyId: Long,
    ): CommonResponse<String?>

    @POST("buddies/{buddyId}/image")
    suspend fun uploadBuddyImg(
        @Header("Authorization") token: String,
        @Path("buddyId") buddyId: Long,
        @Part image: MultipartBody.Part
    ): CommonResponse<String?>

}