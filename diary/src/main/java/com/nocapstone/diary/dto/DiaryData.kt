package com.nocapstone.diary.dto

data class DiaryData(
    val diaries: List<Diary>?
)

data class Diary(
    val day : Int,
    val diaryId: Long,
    val thumbnail: String?
)

data class DiaryId(
    val diaryId: Long
)