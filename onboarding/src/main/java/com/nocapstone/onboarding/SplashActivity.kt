package com.nocapstone.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.nocapstone.onboarding.databinding.ActivitySplashBinding
import javax.inject.Inject


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    @Inject lateinit var mainActivityClass: Class<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        checkJWT()
    }


    // todo Viewmodel에 가지고 있는 datastore객체에 값을 가져오면 실행할 callback을 정의해서 viewModel에 넘긴다
    fun checkJWT() {

        viewModel.withJsonWebToken { JWT ->
            if (JWT != null) {
                LoginUtil.startMainActivity(this,mainActivityClass)
            } else {
                if (isFirst()) {
                    //todo onboarding
                } else {
                    LoginUtil.loginWithKaKao(
                        this
                    ) { token ->
                        viewModel.signup(token!!){
                            LoginUtil.startMainActivity(this,mainActivityClass)
                        }
                    }
                }
            }
        }

    }



    //todo 최초 접속인지 즉 온보딩안했는지
    private fun isFirst(): Boolean {
        return false
    }

}