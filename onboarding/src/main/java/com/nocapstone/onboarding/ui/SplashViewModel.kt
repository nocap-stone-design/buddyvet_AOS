package com.nocapstone.onboarding.ui

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nocapstone.common.domain.usecase.AuthUseCase
import com.nocapstone.common.domain.usecase.DataStoreUseCase
import com.nocapstone.onboarding.domain.BuddyRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {

    private val _selectType = MutableStateFlow(BuddyType.NULL)
    val selectType: StateFlow<BuddyType> = _selectType

    private val _newBuddy = MutableStateFlow<BuddyRequest?>(null)
    val newBuddy : StateFlow<BuddyRequest?> = _newBuddy

    fun setSelectBuddyType(buddyType : BuddyType){
        if (_selectType.value == buddyType){
            _selectType.value = BuddyType.NULL
        }else{
            _newBuddy.value = BuddyRequest(buddyType.kind)
            _selectType.value = buddyType
        }
    }



    fun withJsonWebToken(callback: (String?) -> Unit) {
        viewModelScope.launch {
            callback.invoke(dataStoreUseCase.bearerJsonWebToken.firstOrNull())
        }
    }


    fun signup(token: String, createCallback: () -> Unit, loginCallback: () -> Unit) {

        createCallback.invoke()

//        viewModelScope.launch {
//            try {
//                val response = authUseCase.login(token)
//                dataStoreUseCase.editJsonWebToken(response.body()?.data?.jwt!!)
//                if (response.code() == 200) {
//                    loginCallback.invoke()
//                } else {
//                    createCallback.invoke()
//                }
//            } catch (e: Exception) {
//                Log.d("buddyTest", e.message.toString())
//            }
//
//        }
    }


    fun postUserInfo(nickname: String) {
        viewModelScope.launch {
            try {
                authUseCase.inputUserInfo(dataStoreUseCase.bearerJsonWebToken.first()!!, nickname)
            } catch (e: Exception) {
                Log.d("postTest", e.message.toString())
            }
        }
    }

}


enum class BuddyType(val kind : String) {
    NULL(""),DOG("D"), CAT("C")
}