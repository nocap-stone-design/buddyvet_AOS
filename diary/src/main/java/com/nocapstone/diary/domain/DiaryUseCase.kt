package com.nocapstone.diary.domain

import android.util.Log
import com.nocapstone.common.data.dto.CommonResponse
import com.nocapstone.diary.data.DiaryService
import com.nocapstone.diary.dto.Diary
import com.nocapstone.diary.dto.DiaryData
import com.nocapstone.diary.dto.DiaryDetailData
import com.nocapstone.diary.dto.DiaryId
import okhttp3.MultipartBody
import javax.inject.Inject

class DiaryUseCase @Inject constructor(
    private val diaryService: DiaryService
) {
    // response를 entity로 변경하는 mapper 역할까지
    suspend fun readDiaryList(token: String, year: Int, month: Int): DiaryData {
        val requestMonth = if (month >= 10) month.toString() else "0$month"
        return diaryService.readDiaryList(token, year.toString(), requestMonth).data
    }

    // view -> domain
    suspend fun createDiary(token: String, createDiaryRequest: CreateDiaryRequest): Long {
        return diaryService.createDiary(token, createDiaryRequest).data.diaryId
    }

    suspend fun createDiaryImage(
        token: String,
        diaryId: Long,
        image: List<MultipartBody.Part>
    ): CommonResponse<String?> {
        return diaryService.createDiaryImage(token, diaryId, image)
    }

    suspend fun readDiaryDetail(token: String, diaryId: Long): DiaryDetailData {
        return diaryService.readDiaryDetail(token, diaryId).data
    }

    suspend fun putDiary(token: String, diaryId: Long, createDiaryRequest: CreateDiaryRequest) {
        diaryService.putDiary(token, diaryId, createDiaryRequest)
    }

    suspend fun deleteDiary(token:String, diaryId: Long) {
        diaryService.deleteDiary(token,diaryId)
    }

}