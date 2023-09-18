package com.nocapstone.common.util

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class ImageUtil {
    companion object {
        fun uriToMultipart(context: Context, imageUri: Uri): MultipartBody.Part? {
            val inputStream = context.contentResolver.openInputStream(imageUri)
            inputStream.use {
                val byteArray = it?.readBytes()
                val contentType = context.contentResolver.getType(imageUri)
                val fileExtension = MimeTypeMap.getSingleton().getExtensionFromMimeType(contentType)
                if (byteArray != null && contentType != null && fileExtension != null) {
                    val requestBody =
                        byteArray.toRequestBody(contentType.toMediaTypeOrNull())
                    return MultipartBody.Part.createFormData(
                        "image",
                        System.currentTimeMillis().toString() + ".$fileExtension",
                        requestBody
                    )
                } else {
                    // 파일 데이터, MIME 유형, 파일 확장자 중 하나라도 null인 경우 null 반환
                    return null
                }
            }
        }
    }

}