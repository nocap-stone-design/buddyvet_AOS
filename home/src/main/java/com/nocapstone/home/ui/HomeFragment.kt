package com.nocapstone.home.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nocapstone.buddyvet.buddy.BuddyListAdapter
import com.nocapstone.buddyvet.buddy.domain.entity.BuddyData
import com.nocapstone.buddyvet.buddy.ui.BuddyViewModel
import com.nocapstone.common_ui.MainActivityUtil
import com.nocapstone.eye_check.ui.EyeCheckViewModel
import com.nocapstone.home.R
import com.nocapstone.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val buddyViewModel: BuddyViewModel by viewModels({ requireActivity() })
    private val eyeViewModel: EyeCheckViewModel by viewModels({ requireActivity() })
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            adapter = BuddyListAdapter(false) {
                buddyViewModel.setSelectCheckBuddy(it)
            }
            viewModel = buddyViewModel
        }

        (activity as MainActivityUtil).run {
            setToolbarTitle("홈")
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        buddyViewModel.readBuddyList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.eyeCheckBtn.setOnClickListener {
            DialogForSelectBuddy.Builder(requireContext(), buddyViewModel)
                .setOnClickButton {
                    TedImagePicker.with(requireContext())
                        .errorListener { }
                        .start { uri ->
                            //todo eyechackViewModel
                            eyeViewModel.setImage(uri)
                            findNavController().navigate(R.id.next)
                        }
                }
                .build().show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}