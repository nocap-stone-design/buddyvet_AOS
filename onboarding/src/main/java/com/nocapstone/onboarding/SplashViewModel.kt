package com.nocapstone.onboarding

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nocapstone.common.data.dto.LoginRequest
import com.nocapstone.common.data.entity.Data
import com.nocapstone.common.data.source.AuthService
import com.nocapstone.common.domain.usecase.DataStoreUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authService: AuthService,
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {

    // todo datasource의 키 값에 접근해서 refreshToken 확인
    // todo 그 토큰을 보고 받은 콜백 실행

    fun withJsonWebToken(callback: (String?) -> Unit) {

        viewModelScope.launch {
            //todo datasource에 jsonwebtoken을 가져옴
            callback.invoke(dataStoreUseCase.bearerJsonWebToken.first())
        }

    }

    //todo datasoruce에 token 넣기
    fun signup(token: String, callback: () -> Unit) {
        viewModelScope.launch {
            val data = authService.login(LoginRequest(token, "KaKao")).let {
                if (it.success) {
                    dataStoreUseCase.editJsonWebToken(it.data.jwt)
                    callback.invoke()
                }
            }
        }
    }

}