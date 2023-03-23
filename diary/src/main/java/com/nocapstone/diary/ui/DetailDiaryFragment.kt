package com.nocapstone.diary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.diary.databinding.FragmentDetailDiaryBinding
import com.nocapstone.common_ui.ImageDetailAdapter
import com.nocapstone.common_ui.MainActivityUtil
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailDiaryFragment : Fragment() {

    private val args: DetailDiaryFragmentArgs by navArgs()
    private val diaryViewModel: DiaryViewModel by viewModels()
    private var _binding: FragmentDetailDiaryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailDiaryBinding.inflate(inflater, container, false)

        (activity as MainActivityUtil).run {
            setToolbarTitle("일기 상세 조회")
            setVisibilityBottomAppbar(View.GONE)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        diaryViewModel.readDetailDiary(args.diaryId)
        binding.apply {
            lifecycleOwner =  viewLifecycleOwner
            viewModel = diaryViewModel
            adapter = ImageDetailAdapter()
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