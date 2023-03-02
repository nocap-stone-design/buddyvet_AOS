package com.nocapstone.diary.ui

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nocapstone.common.domain.usecase.DataStoreUseCase
import com.nocapstone.diary.domain.CreateDiaryRequest
import com.nocapstone.diary.domain.DiaryUseCase
import com.nocapstone.diary.dto.Diary
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject


@HiltViewModel
class DiaryViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val diaryUseCase: DiaryUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {


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
                        val fileParts = mutableListOf<MultipartBody.Part>()
                        _imageUriList.value.forEach {
                            val inputStream = context.contentResolver.openInputStream(it)
                            val file = File(context.cacheDir, it.lastPathSegment!!)
                            inputStream?.use { input ->
                                file.outputStream().use { output ->
                                    input.copyTo(output)
                                }
                            }
                            val requestFile =
                                file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                            val part =
                                MultipartBody.Part.createFormData("image", file.name, requestFile)
                            fileParts.add(part)
                        }
                        diaryUseCase.createDiaryImage(token, fileParts, diaryId)
                        _imageUriList.value.clear()
                    }
                }
                callBack.invoke()
            } catch (e: Exception) {
                Log.d("postTest",e.message.toString())
            }
        }
    }


    fun setImage(newUriList: List<Uri>) {
        _imageUriList.value = newUriList.toMutableList()
    }

}