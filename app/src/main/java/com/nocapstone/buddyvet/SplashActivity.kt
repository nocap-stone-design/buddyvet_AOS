package com.nocapstone.buddyvet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nocapstone.buddyvet.databinding.ActivitySplashBinding
import com.nocapstone.onboarding.LoginUtil
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }


    private fun tryAutoLogin() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    // 하나라도 실패했을 경우
                    viewModel.failure.collectLatest {
                        if (it >= 1){} // Todo Login 실패 로직
                    }
                }
                launch {
                    viewModel.success.collectLatest {
                        if (it == 2){} // Todo Login 성공 로직
                    }
                }
            }
        }

        viewModel.with

    }


    //LoginUtil에 Login로직 시행
    private fun loginWithKakao() {
        LoginUtil.loginWithKaKao(this, { _, _ ->
            viewModel.addSuccess()
        }, { _, _ ->
            viewModel.addFailure()
        })
    }

}