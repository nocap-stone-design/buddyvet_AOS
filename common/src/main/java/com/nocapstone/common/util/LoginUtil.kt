package com.nocapstone.common.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginUtil {

    companion object {

        fun startMainActivity(activity: Activity, mainActivityClass: Class<*>) {
            val intent = Intent(activity, mainActivityClass).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            activity.startActivity(intent)
        }


        fun loginWithKaKao(
            context: Context,
            onLoginSuccess: (String?) -> Unit
        ) {

            // 카카오계정으로 로그인 공통 callback 구성
            // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    // 카카오 계정으로 로그인 실패
                } else {
                    onLoginSuccess(token?.accessToken)
                    // 카카오 계정으로 로그인 성공
                }
            }

            // 카카오톡이 설치되어 있으면 카카오톡 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    if (error != null) {
                        // 카카오톡 로그인 실패

                        //사용자가 카카오톡 설치 후 디바이스 권한 요청화면에서 로그인을 취소한 경우
                        // 의도적 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            // todo 그대로
                            // 로그인 취소하셨습니다.
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오 계정이 없으면 카카오계정 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                    } else if (token != null) {
                        // 카카오톡로그인 로그인 성공
                        onLoginSuccess(token.accessToken)
                    }

                }
            } else {
                //카카오톡 설치 x  => 카카오 계정으로 로그인
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            }


        }
    }

}