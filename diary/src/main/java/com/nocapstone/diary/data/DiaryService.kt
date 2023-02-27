package com.nocapstone.diary.data

import com.nocapstone.common.data.dto.CommonResponse
import com.nocapstone.diary.dto.DiaryData
import com.nocapstone.diary.dto.DiaryId
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface DiaryService {

    //일기목록조회
    @GET("diary")
    suspend fun readDiaryList(
        @Header("Authorization") token: String
    ) : CommonResponse<DiaryData>

    // 일기 작성
    @POST("diary")
    suspend fun createDiary(
        @Header("Authorization") token: String,
        @Body createDiaryRequest: CreateDiaryRequest
    ) : CommonResponse<DiaryId>

}