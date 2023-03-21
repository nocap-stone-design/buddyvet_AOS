package com.nocapstone.onboarding.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.nocapstone.onboarding.R
import com.nocapstone.onboarding.databinding.ActivitySplashBinding
import com.nocapstone.common.util.LoginUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var mainActivityClass: Class<*>


    private lateinit var navController: NavController
    private lateinit var binding: ActivitySplashBinding
    private val splashViewModel: SplashViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkJWT()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
    }


    // todo Viewmodel에 가지고 있는 datastore객체에 값을 가져오면 실행할 callback을 정의해서 viewModel에 넘긴다
    private fun checkJWT() {
        splashViewModel.withJsonWebToken { JWT ->
            if (JWT != null) {
                Log.d("buddyTest","$JWT")
                LoginUtil.startMainActivity(this, mainActivityClass)
            } else {
                Log.d("buddyTest","JWT널")
                initNav()
            }
        }
    }

    private fun initNav() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_containerView) as NavHostFragment
        navController = navHostFragment.navController
        binding.splashScreen.visibility = View.GONE
        binding.fragmentContainerView.visibility = View.VISIBLE
    }
}



