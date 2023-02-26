package com.nocapstone.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nocapstone.home.databinding.FragmentCheckSelectImageBinding
import com.nocapstone.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckSelectImageFragment : Fragment() {

    private val storyViewModel: HomeViewModel by viewModels({ requireActivity()})
    private var _binding: FragmentCheckSelectImageBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckSelectImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = storyViewModel
            lifecycleOwner = viewLifecycleOwner
            dialogButton.setOnClickListener {

            }
        }
    }

}