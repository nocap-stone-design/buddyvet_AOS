package com.nocapstone.diary.ui

import android.os.Bundle
import android.view.*
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.diary.R
import com.example.diary.databinding.FragmentPutDiaryBinding
import com.nocapstone.common_ui.*
import com.nocapstone.diary.domain.CreateDiaryRequest
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PutDiaryFragment : Fragment() {

    private val args: PutDiaryFragmentArgs by navArgs()
    private val diaryViewModel: DiaryViewModel by viewModels({ requireActivity() })
    lateinit var existingList: List<ImageInfo>
    private var _binding: FragmentPutDiaryBinding? = null
    private val binding get() = _binding!!
    private var diaryId = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPutDiaryBinding.inflate(inflater, container, false)
        diaryId = args.diaryId


        (activity as MainActivityUtil).run {
            setToolbarTitle("일기 수정")
        }

        existingList = diaryViewModel.detailData.value.images
        diaryViewModel.setImage(existingList.map { it.url.toUri() })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //기존 거의

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = diaryViewModel
            adapter = ImageAdapter()

            picture.setOnClickListener {
                TedImagePicker.with(requireContext())
                    .startMultiImage { diaryViewModel.setImage(it) }
            }

            imageCancle.setOnClickListener {
                TedImagePicker.with(requireContext()).selectedUri(diaryViewModel.imageUriList.value)
                    .startMultiImage {
                        diaryViewModel.setImage(it)
                    }
            }
        }
        initMenu()
        observeToast()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }


    private fun observeToast() {
        diaryViewModel.setToastMessage(null)
        lifecycleScope.launch {
            diaryViewModel.toastMessage.collectLatest {
                if (it != null) {
                    CustomToast.createToast(this@PutDiaryFragment, it.message, it.type)
                }
            }
        }
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
                                diaryViewModel.deleteDiary(diaryId)
                                diaryViewModel.createDiary(it) {
                                    diaryViewModel.setToastMessage(ToastSet("일기 수정 완료", ToastType.SUCCESS))
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

}