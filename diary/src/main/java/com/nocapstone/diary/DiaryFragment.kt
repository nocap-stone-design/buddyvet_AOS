package com.nocapstone.diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.diary.databinding.FragmentDiaryBinding
import com.nocapstone.common_ui.MainActivityUtil
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DiaryFragment : Fragment() {

    private val diaryViewModel: DiaryViewModel by viewModels()
    private var _binding: FragmentDiaryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiaryBinding.inflate(inflater, container, false)

        (activity as MainActivityUtil).run {
            setToolbarTitle("일기")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            this.lifecycleOwner = viewLifecycleOwner
            this.adapter = DiaryAdapter()
            this.viewModel = diaryViewModel
        }

        diaryViewModel.readDiaryList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}