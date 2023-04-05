package com.nocapstone.diary.ui

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nocapstone.common.domain.usecase.DataStoreUseCase
import com.nocapstone.common.util.ImageUtil
import com.nocapstone.common.util.printLog
import com.nocapstone.common_ui.ToastSet
import com.nocapstone.common_ui.ToastType
import com.nocapstone.diary.DiaryUtil
import com.nocapstone.diary.domain.CreateDiaryRequest
import com.nocapstone.diary.domain.DiaryUseCase
import com.nocapstone.diary.dto.CalendarData
import com.nocapstone.diary.dto.DiaryDetailData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
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

    private val _diaryList = MutableStateFlow<MutableList<CalendarData>>(mutableListOf())
    val diaryList: StateFlow<List<CalendarData>> = _diaryList

    private val _toastMessage = MutableStateFlow<ToastSet?>(null)
    val toastMessage: StateFlow<ToastSet?> = _toastMessage

    fun readDiaryList(year: Int, month: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = dataStoreUseCase.bearerJsonWebToken.first()
            if (token != null) {
                try {
                    val diaryData = diaryUseCase.readDiaryList(token, year, month)
                    _diaryList.value = DiaryUtil.getDatesInMonth(year, month, diaryData)
                } catch (e: Exception) {
                    printLog("readDiaryList 오류", e)
                }
            }
        }
    }

    fun createDiary(createDiaryRequest: CreateDiaryRequest, callback: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = dataStoreUseCase.bearerJsonWebToken.first()
            if (token != null) {
                try {
                    val diaryId = diaryUseCase.createDiary(token, createDiaryRequest)
                    if (_imageUriList.value.isNotEmpty()) {
                        //이미지 업로드 비동기
                        val images = _imageUriList.value.map { imageUri ->
                            async {
                                ImageUtil.uriToMultipart(context, imageUri)
                            }
                        }.awaitAll()
                        diaryUseCase.createDiaryImage(token, diaryId, images.filterNotNull())
                        _imageUriList.value.clear()
                    }
                    callback.invoke()
                } catch (e: Exception) {

                    printLog("createDiary 오류", e)

                }
            }
        }
    }

    fun readDetailDiary(diaryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = dataStoreUseCase.bearerJsonWebToken.first()
            if (token != null) {
                try {
                    _detailData.value = diaryUseCase.readDiaryDetail(token, diaryId)
                } catch (e: Exception) {
                    printLog("readDetailDiary 오류", e)
                }
            }
        }
    }

    fun setImage(newUriList: List<Uri>) {
        _imageUriList.value = newUriList.toMutableList()
    }

    fun setToastMessage(newMessage: String, toastType: ToastType) {
        _toastMessage.value = null
        _toastMessage.value = ToastSet(newMessage,toastType)
    }
}

