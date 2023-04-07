package com.nocapstone.diary.ui

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.diary.R
import com.example.diary.databinding.FragmentDiaryBinding
import com.nocapstone.common_ui.*
import com.nocapstone.diary.DiaryAdapter
import com.nocapstone.diary.domain.CreateDiaryRequest
import com.nocapstone.diary.dto.Diary
import com.nocapstone.diary.dto.DiaryData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class DiaryFragment : Fragment() {

    private val diaryViewModel: DiaryViewModel by viewModels({ requireActivity() })

    private var _binding: FragmentDiaryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiaryBinding.inflate(inflater, container, false)
        (activity as MainActivityUtil).run {
            setToolbarTitle("일기")
            setVisibilityBottomAppbar(View.VISIBLE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMenu()

        binding.apply {
            this.lifecycleOwner = viewLifecycleOwner
            this.adapter = DiaryAdapter(this@DiaryFragment)
            this.viewModel = diaryViewModel
            calendarDate.text = CalendarUtil.getTodayDateNoDay()
            calendarDate.setOnClickListener {
                DialogForDateNoDay.Builder(requireContext())
                    .setInitDate(CalendarUtil.parseStringToDateNoDay(it as TextView)!!)
                    .setOnClickPositiveButton { newDate ->
                        binding.calendarDate.text = newDate
                        diaryViewModel.readDiaryList(
                            newDate.substring(0, 4).toInt(),
                            newDate.substring(5).toInt()
                        )
                    }.build().show()
            }
            diaryViewModel.readDiaryList(CalendarUtil.getTodayYear(), CalendarUtil.getTodayMonth())
        }
        observeToast()
    }

    private fun observeToast() {
        lifecycleScope.launch {
            diaryViewModel.toastMessage.collectLatest {
                if (it != null) {
                    CustomToast.createToast(this@DiaryFragment, it.message, it.type)
                    diaryViewModel.setToastMessage(null)
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.diary_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.menu_writingDiary -> {
                        findNavController().navigate(R.id.next)
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


}