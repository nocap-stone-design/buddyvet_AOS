package com.nocapstone.onboarding.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nocapstone.onboarding.R
import com.nocapstone.onboarding.databinding.FragmentLoginBinding
import com.nocapstone.onboarding.util.LoginUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {


    @Inject
    lateinit var mainActivityClass: Class<*>


    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //todo recycle
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, callback
        )

        binding.kakaoLoginBtn.setOnClickListener {
            LoginUtil.loginWithKaKao(requireContext()) { token ->
                splashViewModel.signup(token!!, {
                    LoginUtil.startMainActivity(requireActivity(), mainActivityClass) }, { findNavController().navigate(R.id.next) })
            }
        }
    }
}