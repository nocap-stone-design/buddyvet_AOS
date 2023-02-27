package com.nocapstone.onboarding.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nocapstone.common.domain.usecase.AuthUseCase
import com.nocapstone.common.domain.usecase.DataStoreUseCase
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
    // todo datasource의 키 값에 접근해서 refreshToken 확인
    // todo 그 토큰을 보고 받은 콜백 실행
    fun withJsonWebToken(callback: (String?) -> Unit) {
        viewModelScope.launch {
            callback.invoke(dataStoreUseCase.bearerJsonWebToken.firstOrNull())
        }
    }


    fun signup(token: String, callback: () -> Unit) {
        viewModelScope.launch {
            try {
                val jwt = authUseCase.login(token)
                Log.d("test",jwt.toString())
                dataStoreUseCase.editJsonWebToken(jwt)
                callback.invoke()
            } catch (e: Exception) {
                Log.d("buddyTest", e.message.toString())
            }

        }
    }

}