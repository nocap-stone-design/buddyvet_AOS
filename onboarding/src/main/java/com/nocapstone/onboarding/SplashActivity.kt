package com.nocapstone.onboarding

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.nocapstone.onboarding.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    @Inject
    lateinit var mainActivityClass: Class<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkJWT()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
    }


    // todo Viewmodel에 가지고 있는 datastore객체에 값을 가져오면 실행할 callback을 정의해서 viewModel에 넘긴다
    fun checkJWT() {
        Log.d("test", "checkJWT")
        viewModel.withJsonWebToken { JWT ->
            if (JWT != null) {
                LoginUtil.startMainActivity(this, mainActivityClass)
            } else {
                isFirst { permissionNum ->
                    // 처음이아님 => 카카오 로그인만 다시
                    if (permissionNum == OnBoardingViewPagerFragment.viewpagerNum) {
                        LoginUtil.loginWithKaKao(this) { token ->
                            viewModel.signup(token = token!!) {
                                // 로그인 됐다면 다시 jwt 확인
                                checkJWT()
                            }
                        }
                    } else {
                        //처음이면 온보딩
                        initNav()
                    }
                }
            }
        }
    }

    private fun initNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_containerView) as NavHostFragment
        navController = navHostFragment.navController
        binding.splashScreen.visibility = View.GONE
        binding.fragmentContainerView.visibility = View.VISIBLE
    }

    //todo 최초 접속인지 즉 온보딩안했는지
    private fun isFirst(callback: (Int?) -> Unit) {
        viewModel.checkPermission { permissionNum ->
            callback.invoke(permissionNum)
        }
    }

}