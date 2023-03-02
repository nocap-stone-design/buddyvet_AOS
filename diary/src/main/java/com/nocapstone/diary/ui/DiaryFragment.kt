package com.nocapstone.diary.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.diary.R
import com.example.diary.databinding.FragmentDiaryBinding
import com.nocapstone.common_ui.MainActivityUtil
import com.nocapstone.diary.DiaryAdapter
import com.nocapstone.diary.domain.CreateDiaryRequest
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

        initMenu()

        binding.apply {
            this.lifecycleOwner = viewLifecycleOwner
            this.adapter = DiaryAdapter(this@DiaryFragment)
            this.viewModel = diaryViewModel
        }

        diaryViewModel.readDiaryList()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun initMenu() {
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