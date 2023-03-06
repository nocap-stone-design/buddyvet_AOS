package com.nocapstone.diary.ui

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nocapstone.common.domain.usecase.DataStoreUseCase
import com.nocapstone.diary.domain.CreateDiaryRequest
import com.nocapstone.diary.domain.DiaryUseCase
import com.nocapstone.diary.dto.Diary
import com.nocapstone.diary.dto.DiaryDetailData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


@HiltViewModel
class DiaryViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val diaryUseCase: DiaryUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {

    private val _detailData = MutableStateFlow<DiaryDetailData?>(null)
    val detailData: StateFlow<DiaryDetailData?> = _detailData

    private val _imageUriList = MutableStateFlow<MutableList<Uri>>(mutableListOf())
    val imageUriList: StateFlow<List<Uri>> = _imageUriList

    private val _diaryList = MutableStateFlow<MutableList<Diary>>(mutableListOf())
    val diaryList: StateFlow<List<Diary>> = _diaryList

    fun readDiaryList() {
        viewModelScope.launch {
            try {
                diaryUseCase.readDiaryList(dataStoreUseCase.bearerJsonWebToken.first()!!).let {
                    _diaryList.value = it.toMutableList()
                }
            } catch (e: Exception) {

            }
        }
    }

    //todo 뷰에서 request 구성
    fun createDiary(createDiaryRequest: CreateDiaryRequest, callBack: () -> Unit) {
        viewModelScope.launch {
            try {
                val token = dataStoreUseCase.bearerJsonWebToken.first()!!
                diaryUseCase.createDiary(token, createDiaryRequest).let { diaryId ->
                    if (_imageUriList.value.size > 0) {
                        val images = _imageUriList.value.map { imageUri ->
                            Log.d("postUri", imageUri.toString())

                            // ContentResolver로부터 데이터를 읽기 위해 inputStream을 열어줍니다.
                            val inputStream = context.contentResolver.openInputStream(imageUri)

                            // 읽은 데이터를 byteArray로 변환하고 RequestBody로 변환합니다.
                            val byteArray = inputStream!!.readBytes()
                            val requestBody =
                                byteArray.toRequestBody("multipart/form-data".toMediaTypeOrNull())

                            // MultipartBody.Part를 생성하여 반환합니다.
                            MultipartBody.Part.createFormData(
                                "image",
                                getFileName(context, imageUri),
                                requestBody
                            )
                        }
                        diaryUseCase.createDiaryImage(token, diaryId, images)
                        _imageUriList.value.clear()
                    }
                }
                callBack.invoke()
            } catch (e: Exception) {
                Log.d("postTest", e.message.toString())
            }
        }
    }

    private fun getFileName(context: Context, uri: Uri): String? {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        return cursor?.use {
            it.moveToFirst()
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            Log.d("postUri", it.getString(nameIndex))
            return it.getString(nameIndex)
        }
    }


    fun readDetailDiary(diaryId: Int) {
        viewModelScope.launch {
            try {
                _detailData.value = diaryUseCase.readDiaryDetail(
                    dataStoreUseCase.bearerJsonWebToken.first()!!,
                    diaryId
                )
            } catch (e: Exception) {
                Log.d("postTest", e.message.toString())
            }
        }
    }


    fun setImage(newUriList: List<Uri>) {
        _imageUriList.value = newUriList.toMutableList()
    }

}