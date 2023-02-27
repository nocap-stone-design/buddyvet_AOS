package com.nocapstone.diary.data

data class CreateDiaryRequest(
    val data : String,
    val title: String,
    val content: String
)