package com.nocapstone.diary

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nocapstone.common.domain.usecase.DataStoreUseCase
import com.nocapstone.diary.domain.DiaryUseCase
import com.nocapstone.diary.dto.Diary
import com.nocapstone.diary.dto.DiaryData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

val dummy = mutableListOf<Diary>().apply {
    add(Diary(1,"https://upload3.inven.co.kr/upload/2022/03/15/bbs/i16343629296.jpg?MW=800"))
    add(Diary(2,"https://upload3.inven.co.kr/upload/2022/03/15/bbs/i16343629296.jpg?MW=800"))
    add(Diary(3,"https://upload3.inven.co.kr/upload/2022/03/15/bbs/i16343629296.jpg?MW=800"))
    add(Diary(4,"https://upload3.inven.co.kr/upload/2022/03/15/bbs/i16343629296.jpg?MW=800"))
    add(Diary(5,"https://upload3.inven.co.kr/upload/2022/03/15/bbs/i16343629296.jpg?MW=800"))
    add(Diary(6,"https://upload3.inven.co.kr/upload/2022/03/15/bbs/i16343629296.jpg?MW=800"))
    add(Diary(7,"https://upload3.inven.co.kr/upload/2022/03/15/bbs/i16343629296.jpg?MW=800"))
}

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val diaryUseCase: DiaryUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {

    private val _diaryList = MutableStateFlow<MutableList<Diary>>(mutableListOf())
    val diaryList: StateFlow<List<Diary>> = _diaryList

    fun readDiaryList() {
        viewModelScope.launch {
            try {
                diaryUseCase.readDiaryList(dataStoreUseCase.bearerJsonWebToken.first()!!).let {
                    _diaryList.value = it.toMutableList()
                }
            }catch (e : Exception){

            }
            Log.d("diaryTest", dummy.toString())
            _diaryList.value = dummy
        }
    }

    fun createDiary() {

    }


}