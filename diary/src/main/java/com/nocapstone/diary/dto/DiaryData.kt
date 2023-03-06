package com.nocapstone.diary.dto

data class DiaryData(
    val diaries: List<Diary>?
)

data class Diary(
    val diaryId: Int,
    val thumbnail: String?
)

data class DiaryId(
    val diaryId: Int
)