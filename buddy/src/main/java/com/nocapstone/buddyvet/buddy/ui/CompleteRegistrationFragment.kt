package com.nocapstone.buddyvet.buddy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nocapstone.buddyvet.buddy.databinding.FragmentCompleteRegistrationBinding
import com.nocapstone.common.util.LoginUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CompleteRegistrationFragment : Fragment() {

    @Inject
    lateinit var mainActivityClass: Class<*>

    private var _binding: FragmentCompleteRegistrationBinding? = null
    private val binding get() = _binding!!

    private val buddyViewModel: BuddyViewModel by viewModels({ requireActivity() })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCompleteRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.next.setOnClickListener {
            LoginUtil.startMainActivity(requireActivity(), mainActivityClass)
        }

    }
}