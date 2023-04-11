package com.nocapstone.onboarding.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nocapstone.common.domain.entity.UserNameRequest
import com.nocapstone.common.domain.usecase.AuthUseCase
import com.nocapstone.common.domain.usecase.DataStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {

    fun withJsonWebToken(callback: (String?) -> Unit) {
        viewModelScope.launch {
            callback.invoke(dataStoreUseCase.bearerJsonWebToken.firstOrNull())
        }
    }

    fun signup(token: String, createCallback: () -> Unit, loginCallback: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = authUseCase.login(token)
                dataStoreUseCase.editJsonWebToken(response.body()?.data?.jwt!!)
                Log.d("responsecode",response.code().toString())
//                if (response.code() == 200) {
//                    loginCallback.invoke()
//                } else if (response.code() == 201) {
//              }
//            else{
//                    Log.d("responsecode",response.code().toString())
//                }
                createCallback.invoke()
            } catch (e: Exception) {
                Log.d("buddyTest", e.message.toString())
            }
        }
    }

    fun postUserInfo(nickname: String, callback: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = dataStoreUseCase.bearerJsonWebToken.first()
            if (token != null) {
                try {
                    authUseCase.inputUserInfo(
                        dataStoreUseCase.bearerJsonWebToken.first()!!,
                        UserNameRequest(nickname)
                    )
                    callback.invoke()
                } catch (e: Exception) {
                    Log.d("nickNameApi", e.message.toString())
                }
            }
        }
    }

}


