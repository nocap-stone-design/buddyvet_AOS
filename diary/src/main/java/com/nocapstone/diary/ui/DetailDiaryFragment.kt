package com.nocapstone.diary.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.diary.R
import com.example.diary.databinding.FragmentDetailDiaryBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nocapstone.common_ui.ImageDetailAdapter
import com.nocapstone.common_ui.MainActivityUtil
import com.nocapstone.common_ui.ToastSet
import com.nocapstone.common_ui.ToastType
import dagger.hilt.android.AndroidEntryPoint
import com.nocapstone.common_ui.R.menu.common_menu


@AndroidEntryPoint
class DetailDiaryFragment : Fragment() {

    private val args: DetailDiaryFragmentArgs by navArgs()
    private val diaryViewModel: DiaryViewModel by viewModels({ requireActivity() })
    private var _binding: FragmentDetailDiaryBinding? = null
    private val binding get() = _binding!!
    private var diaryId = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailDiaryBinding.inflate(inflater, container, false)

        (activity as MainActivityUtil).run {
            setToolbarTitle("일기 상세 조회")
            setVisibilityBottomAppbar(View.GONE)
        }

        diaryId = args.diaryId
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMenu()
        diaryViewModel.readDetailDiary(diaryId)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = diaryViewModel
            adapter = ImageDetailAdapter()
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
                menuInflater.inflate(common_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    android.R.id.home -> {
                        findNavController().popBackStack()
                    }
                    com.nocapstone.common_ui.R.id.put -> {
                        findNavController().navigate(
                            DetailDiaryFragmentDirections.putDiaryFragment(
                                diaryId
                            )
                        )
                    }
                    com.nocapstone.common_ui.R.id.delete -> {
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("일기 삭제")
                            .setMessage("정말 삭제하시겠습니까?")
                            .setNegativeButton("취소") { dialog, which ->
                                dialog.dismiss()
                            }.setPositiveButton("삭제") { dialog, which ->
                                diaryViewModel.deleteDiary(diaryId)
                                diaryViewModel.setToastMessage(
                                    ToastSet(
                                        "일기 삭제 완료",
                                        ToastType.SUCCESS
                                    )
                                )
                                dialog.dismiss()
                                findNavController().popBackStack()
                            }.show()
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


}