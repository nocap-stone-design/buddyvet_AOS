package com.nocapstone.eye_check.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.eye_check.R
import com.example.eye_check.databinding.FragmentCheckResultBinding
import com.nocapstone.common.util.LoginUtil
import com.nocapstone.common_ui.MainActivityUtil
import com.nocapstone.eye_check.CheckResultAdapter
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import javax.inject.Inject

@AndroidEntryPoint
class CheckResultFragment : Fragment() {
    @Inject
    lateinit var mainActivityClass: Class<*>

    private val eyeCheckViewModel: EyeCheckViewModel by viewModels( { requireActivity()} )
    private var _binding: FragmentCheckResultBinding? = null
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
        eyeCheckViewModel.setDummyCheckResultList()
        binding.apply {
            viewModel = eyeCheckViewModel
            lifecycleOwner = viewLifecycleOwner
            adapter = CheckResultAdapter()
            retry.setOnClickListener {
                TedImagePicker.with(requireContext())
                    .errorListener { }
                    .start { uri ->
                        eyeCheckViewModel.setImage(uri)
                        findNavController().navigate(R.id.action_checkResultFragment_to_checkSelectImageFragment)
                    }
            }
            complete.setOnClickListener {
                LoginUtil.startMainActivity(requireActivity(), mainActivityClass)
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