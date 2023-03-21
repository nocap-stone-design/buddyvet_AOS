package com.nocapstone.onboarding.ui.registrationbuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nocapstone.onboarding.R
import com.nocapstone.onboarding.databinding.FragmentInputBuddyInfo2Binding
import com.nocapstone.onboarding.ui.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InputBuddyInfo2Fragment : Fragment() {

    private var _binding: FragmentInputBuddyInfo2Binding? = null
    val binding get() = _binding!!

    private val splashViewModel: SplashViewModel by viewModels({ requireActivity() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInputBuddyInfo2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.next.setOnClickListener {
            findNavController().navigate(R.id.next)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}