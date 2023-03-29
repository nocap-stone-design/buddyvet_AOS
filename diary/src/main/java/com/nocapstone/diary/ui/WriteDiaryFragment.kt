package com.nocapstone.diary.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.diary.R
import com.example.diary.databinding.FragmentWriteDiaryBinding
import com.nocapstone.common_ui.CalendarUtil.Companion.getTodayDate
import com.nocapstone.common_ui.CalendarUtil.Companion.parseDateToFormatString
import com.nocapstone.common_ui.CalendarUtil.Companion.parseStringToDate
import com.nocapstone.common_ui.DialogForDatePicker
import com.nocapstone.common_ui.ImageAdapter
import com.nocapstone.common_ui.MainActivityUtil
import com.nocapstone.diary.domain.CreateDiaryRequest
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class WriteDiaryFragment : Fragment() {

    private val diaryViewModel: DiaryViewModel by viewModels()
    private var _binding: FragmentWriteDiaryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWriteDiaryBinding.inflate(inflater, container, false)

        (activity as MainActivityUtil).run {
            setToolbarTitle("일기작성")
            setVisibilityBottomAppbar(View.GONE)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMenu()

        binding.apply {
            viewModel = diaryViewModel
            lifecycleOwner = viewLifecycleOwner
            adapter = ImageAdapter()
            date.text = getTodayDate()
            date.setOnClickListener {
                DialogForDatePicker.Builder(requireContext())
                    .setInitDate(parseStringToDate(binding.date.text.toString())!!)
                    .setOnClickPositiveButton { newDate ->
                        binding.date.text = parseDateToFormatString(newDate)
                    }
                    .build().show()
            }

            picture.setOnClickListener {
                TedImagePicker.with(requireContext())
                    .startMultiImage { diaryViewModel.setImage(it) }
            }
        }
        observeToast()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivityUtil).run {
            setVisibilityBottomAppbar(View.VISIBLE)
        }
        _binding = null

    }


    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.writing_diary_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    android.R.id.home -> {
                        findNavController().popBackStack()
                    }
                    R.id.menu_createDiary -> {
                        // 일기 작성 완료
                        with(binding) {
                            CreateDiaryRequest(
                                date.text.toString(),
                                titleTv.text.toString(),
                                contentTv.text.toString()
                            ).let {
                                diaryViewModel.createDiary(it){
                                    findNavController().popBackStack()
                                }
                            }
                        }
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun observeToast() {
        lifecycleScope.launch {
            diaryViewModel.toastMessage.collectLatest {
                if (it.isNotEmpty()) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}