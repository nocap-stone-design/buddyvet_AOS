package com.nocapstone.onboarding

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginUtil {

    companion object {

        fun loginWithKaKao(
            context: Context,
            onLoginSuccess: (OAuthToken?, Throwable?) -> Unit,
            onLoginFailure: (OAuthToken?, Throwable?) -> Unit
        ) {
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                // 에러가 비어있지 않다면 즉 에러가 존재한다면
                if (error != null) onLoginFailure(token, error)
                // 토큰이 비어있지 않다면 즉 토근이 존재한다면
                else if (token != null) onLoginSuccess(token, error)
            }

            // 카카오톡이 설치되어 있다면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도한다.
                    if (error != null) {
                        onLoginFailure(token, error)

                        // 단, 사용자가 의독적로 로그인을 취소한 경우는 제외
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            onLoginFailure(token, error)
                            return@loginWithKakaoTalk
                        }
                        //카카오 계정으로 로그인
                        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                    } else if (token != null) onLoginSuccess(token, error)
                }
            }
            // 카카오톡이 설치되어 있지 않다면 카카오 계정으로 로그인
            else {
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            }

        }
    }

}