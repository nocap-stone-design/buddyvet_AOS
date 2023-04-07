package com.nocapstone.diary.data

import com.nocapstone.common.data.dto.CommonResponse
import com.nocapstone.diary.domain.CreateDiaryRequest
import com.nocapstone.diary.dto.DiaryData
import com.nocapstone.diary.dto.DiaryDetailData
import com.nocapstone.diary.dto.DiaryId
import okhttp3.MultipartBody
import retrofit2.http.*

interface DiaryService {

    //일기목록조회
    @GET("diary")
    suspend fun readDiaryList(
        @Header("Authorization") token: String,
        @Query("year") year: String,
        @Query("month") month: String
    ): CommonResponse<DiaryData>

    // 일기 작성
    @POST("diary")
    suspend fun createDiary(
        @Header("Authorization") token: String,
        @Body createDiaryRequest: CreateDiaryRequest
    ): CommonResponse<DiaryId>


    @Multipart
    @POST("diary/{diaryId}/image")
    suspend fun createDiaryImage(
        @Header("Authorization") token: String,
        @Path("diaryId") diaryId: Long,
        @Part image: List<MultipartBody.Part>
    ): CommonResponse<String?>


    @GET("diary/{diaryId}")
    suspend fun readDiaryDetail(
        @Header("Authorization") token: String,
        @Path("diaryId") diaryId: Long,
    ): CommonResponse<DiaryDetailData>

    @PUT("diary/{diaryId}")
    suspend fun putDiary(
        @Header("Authorization") token: String,
        @Path("diaryId") diaryId: Long,
        @Body createDiaryRequest: CreateDiaryRequest
    ): CommonResponse<String?>

    @DELETE("diary/{diaryId}")
    suspend fun deleteDiary(
        @Header("Authorization") token: String,
        @Path("diaryId") diaryId: Long,
    ): CommonResponse<String?>



}