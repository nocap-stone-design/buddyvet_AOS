package com.nocapstone.diary.domain

import android.util.Log
import com.nocapstone.common.data.dto.CommonResponse
import com.nocapstone.diary.data.DiaryService
import com.nocapstone.diary.dto.Diary
import com.nocapstone.diary.dto.DiaryDetailData
import com.nocapstone.diary.dto.DiaryId
import okhttp3.MultipartBody
import javax.inject.Inject

class DiaryUseCase @Inject constructor(
    private val diaryService: DiaryService
) {
    // response를 entity로 변경하는 mapper 역할까지
    suspend fun readDiaryList(token: String): List<Diary> {
        return diaryService.readDiaryList(token).data.diaries!!
    }

    // view -> domain
    suspend fun createDiary(token: String, createDiaryRequest: CreateDiaryRequest): Int {
        return diaryService.createDiary(token, createDiaryRequest).data.diaryId
    }

    suspend fun createDiaryImage(
        token: String,
        diaryId: Int,
        image: List<MultipartBody.Part>
    ): CommonResponse<String?> {
        return diaryService.createDiaryImage(token, diaryId.toLong(), image)
    }

    suspend fun readDiaryDetail(token: String, diaryId: Int): DiaryDetailData {
        return diaryService.readDiaryDetail(token, diaryId.toLong()).data
    }


}