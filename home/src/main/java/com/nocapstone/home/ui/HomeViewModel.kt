package com.nocapstone.home.ui

import androidx.lifecycle.ViewModel
import com.nocapstone.common.domain.usecase.DataStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    //private val homeUseCase: HomeUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {

    private val _toastMessage = MutableStateFlow("")
    val toastMessage: StateFlow<String> = _toastMessage




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