package com.nocapstone.diary.domain

import com.google.gson.annotations.SerializedName

data class CreateDiaryRequest(
    @SerializedName("date")
    val date : String,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String
)