package com.nocapstone.diary.domain

import com.nocapstone.diary.data.DiaryService
import com.nocapstone.diary.dto.Diary
import com.nocapstone.diary.dto.DiaryData
import javax.inject.Inject

class DiaryUseCase @Inject constructor(
    private val diaryService: DiaryService
) {
    // response를 entity로 변경하는 mapper 역할까지
    suspend fun readDiaryList(token: String): List<Diary> {
        return diaryService.readDiaryList(token).data.diaries!!
    }

    suspend fun createDiaryList(token: String): List<Diary> {
        return diaryService.createDiary(token,)
    }
}