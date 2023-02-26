package com.nocapstone.home

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.URI
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {

    private val _selectImage = MutableStateFlow<Uri?>(null)
    val selectImage : StateFlow<Uri?> = _selectImage

    private val _toastMessage = MutableStateFlow("")
    val toastMessage: StateFlow<String> = _toastMessage


    fun setImage(uri: Uri){
        _selectImage.value = uri
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

    private fun setToastMessage(newMessage : String){
        _toastMessage.value = ""
        _toastMessage.value = newMessage
    }

}