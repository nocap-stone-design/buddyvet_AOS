package com.nocapstone.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.nocapstone.onboarding.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    @Inject lateinit var mainActivityClass: Class<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        checkJWT()
    }




    // todo Viewmodel에 가지고 있는 datastore객체에 값을 가져오면 실행할 callback을 정의해서 viewModel에 넘긴다
    fun checkJWT() {
        viewModel.withJsonWebToken { JWT ->
            if (JWT != null) {
                LoginUtil.startMainActivity(this, mainActivityClass)
            } else {
                if (isFirst()) {
                    initNav()
                } else {
                    LoginUtil.loginWithKaKao(
                        this
                    ) { token ->
                        viewModel.signup(token!!) {
                            LoginUtil.startMainActivity(this, mainActivityClass)
                        }
                    }
                }
            }
        }
    }

    private fun initNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_containerView) as NavHostFragment
        navController = navHostFragment.navController
    }

    //todo 최초 접속인지 즉 온보딩안했는지
    private fun isFirst(): Boolean {
        return false
    }

}