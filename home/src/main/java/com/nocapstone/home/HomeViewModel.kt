package com.nocapstone.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.nocapstone.home.dto.BuddyData
import com.nocapstone.home.dto.CheckResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {

    private val _selectImage = MutableStateFlow<Uri?>(null)
    val selectImage: StateFlow<Uri?> = _selectImage

    private val _toastMessage = MutableStateFlow("")
    val toastMessage: StateFlow<String> = _toastMessage

    private val _checkResultList = MutableStateFlow<MutableList<CheckResultData>>(mutableListOf())
    val checkResultList: StateFlow<List<CheckResultData>> = _checkResultList

    private val _buddyList = MutableStateFlow<MutableList<BuddyData>>(mutableListOf())
    val buddyList: StateFlow<List<BuddyData>> = _buddyList

    private val _selectBuddy = MutableStateFlow<Int?>(null)
    val selectBuddy: StateFlow<Int?> = _selectBuddy
    fun setImage(uri: Uri) {
        _selectImage.value = uri
    }

    fun setSelectCheckBuddy(position: Int) {
        _selectBuddy.value = position
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

    fun setBuddyListDummy() {
        _buddyList.value = buddyDummy
    }

//    fun settingHomeData() {
//        viewModelScope.launch {
//            var jwt = dataStoreUseCase.bearerJsonWebToken.first()
//            //todo 삭제
//            if (jwt == null) jwt = "bearer test"
//            try {
//                homeUseCase.readHomeInfo(jwt).let {
//                    if (it != null){
//                        if (it.newStory.postId == -1){
//                            _isNewStory.value = true
//                        }
//                        _homeData.value = it
//                    } else {
//                        Log.d("TESTAPI","API실패")
//                    }
//                }
//            }catch (e : Exception) {
//                setToastMessage("메인 가져오는 거 실패")
//            }
//        }
//    }

    private fun setToastMessage(newMessage: String) {
        _toastMessage.value = ""
        _toastMessage.value = newMessage
    }

}