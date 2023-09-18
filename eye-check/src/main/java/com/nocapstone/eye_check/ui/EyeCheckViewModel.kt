package com.nocapstone.eye_check.ui

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.nocapstone.common.domain.usecase.DataStoreUseCase
import com.nocapstone.eye_check.domain.entity.CheckResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class EyeCheckViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    //private val eyeUseCase: EyeUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {

    private val _selectImage = MutableStateFlow<Uri?>(null)
    val selectImage : StateFlow<Uri?> = _selectImage

    private val _checkResultList = MutableStateFlow<MutableList<CheckResultData>>(mutableListOf())
    val checkResultList: StateFlow<List<CheckResultData>> = _checkResultList


    fun setImage(uri: Uri){
        _selectImage.value = uri
    }


    fun setDummyCheckResultList() {
        val dummyList = mutableListOf<CheckResultData>()
        for (i in 0 until 20) {
            dummyList.add(
                CheckResultData(
                    "https://upload3.inven.co.kr/upload/2022/03/15/bbs/i16343629296.jpg?MW=800",
                    "질병 이름 $i"
                )
            )
        }
        _checkResultList.value = dummyList
    }


}