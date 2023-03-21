package com.nocapstone.buddyvet.buddy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nocapstone.buddyvet.buddy.R
import com.nocapstone.buddyvet.buddy.databinding.FragmentInputBuddyTypeBinding
import com.nocapstone.buddyvet.buddy.util.BuddyType
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InputBuddyTypeFragment : Fragment() {

    private var _binding: FragmentInputBuddyTypeBinding? = null
    private val binding get() = _binding!!
    private val buddyViewModel: BuddyViewModel by viewModels({ requireActivity() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInputBuddyTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.viewModel = buddyViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.dogIv.setOnClickListener {
            buddyViewModel.setSelectBuddyType(BuddyType.DOG)
        }

        binding.catIv.setOnClickListener {
            buddyViewModel.setSelectBuddyType(BuddyType.CAT)
        }

        binding.next.setOnClickListener {
            findNavController().navigate(R.id.next)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}