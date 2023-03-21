package com.nocapstone.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nocapstone.common_ui.MainActivityUtil
import com.nocapstone.home.databinding.FragmentCheckResultBinding
import com.nocapstone.home.databinding.FragmentCheckSelectImageBinding
import gun0912.tedimagepicker.builder.TedImagePicker


class CheckResultFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels( { requireActivity()} )
    private var _binding:FragmentCheckResultBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckResultBinding.inflate(inflater, container, false)
        (activity as MainActivityUtil).run {
            setToolbarTitle("눈 체크 결과")
            setVisibilityBottomAppbar(View.GONE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.setDummyCheckResultList()
        binding.apply {
            viewModel = homeViewModel
            lifecycleOwner = viewLifecycleOwner
            adapter = CheckResultAdapter()
            retry.setOnClickListener {
                TedImagePicker.with(requireContext())
                    .errorListener { }
                    .start { uri ->
                        homeViewModel.setImage(uri)
                        findNavController().navigate(R.id.action_checkResultFragment_to_checkSelectImageFragment)
                    }
            }
            complete.setOnClickListener {
                findNavController().popBackStack()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivityUtil).run {
            setVisibilityBottomAppbar(View.VISIBLE)
        }
        _binding = null
    }

}