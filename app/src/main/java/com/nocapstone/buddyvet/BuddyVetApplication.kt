package com.nocapstone.buddyvet

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.nocapstone.common.util.NATIVE_APP_KEY

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BuddyVetApplication  : Application(){

    override fun onCreate() {
        super.onCreate()
        //KaKao Develop이랑 연동
        KakaoSdk.init(this, NATIVE_APP_KEY)
    }

}