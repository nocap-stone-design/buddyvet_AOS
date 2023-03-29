package com.nocapstone.common.util

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class ImageUtil {
    companion object {
        fun uriToMultipart(context: Context, imageUri: Uri): MultipartBody.Part? {
            // ContentResolver로부터 데이터를 읽기 위해 inputStream을 열어줍니다.
            val inputStream = context.contentResolver.openInputStream(imageUri)
            return try {
                val byteArray = inputStream!!.readBytes()
                // 읽은 데이터를 byteArray로 변환하고 RequestBody로 변환합니다.
                val requestBody =
                    byteArray.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                // MultipartBody.Part를 생성하여 반환합니다.
                //현재 시간을 기분으로 파일 이름 생성
                MultipartBody.Part.createFormData(
                    "image",
                    System.currentTimeMillis().toString(),
                    requestBody
                )
            } finally {
                inputStream?.close()
            }
        }
    }
}