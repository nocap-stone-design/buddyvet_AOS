package com.nocapstone.onboarding.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nocapstone.onboarding.R
import com.nocapstone.onboarding.databinding.FragmentInputBuddyInfo2Binding
import com.nocapstone.onboarding.databinding.FragmentOnBoarding1Binding


class InputBuddyInfo2Fragment : Fragment() {

    private var _binding: FragmentInputBuddyInfo2Binding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInputBuddyInfo2Binding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = InputBuddyInfo2Fragment()
    }

}