package com.nocapstone.buddyvet.buddy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nocapstone.buddyvet.buddy.databinding.FragmentPutBuddyBinding
import com.nocapstone.buddyvet.buddy.domain.entity.BuddyDataLocal
import com.nocapstone.buddyvet.buddy.domain.entity.BuddyDetailDataLocal
import com.nocapstone.buddyvet.buddy.domain.entity.BuddyRequest
import com.nocapstone.common_ui.CalendarUtil
import com.nocapstone.common_ui.DialogForDatePicker
import com.nocapstone.common_ui.MainActivityUtil
import com.nocapstone.common_ui.R
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker

@AndroidEntryPoint
class PutBuddyFragment : Fragment() {

    private val args: PutBuddyFragmentArgs by navArgs()
    private var buddyId = 0L
    private val buddyViewModel: BuddyViewModel by viewModels({ requireActivity() })
    private var _binding: FragmentPutBuddyBinding? = null
    private val binding get() = _binding!!
    private val neuteredItem = listOf("예", "아니오")
    private val genderListItem = listOf("남자", "여자")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPutBuddyBinding.inflate(inflater, container, false)
        buddyId = args.buddyId

        (activity as MainActivityUtil).run {
            setToolbarTitle("버디 수정")
            setVisibilityBottomAppbar(View.GONE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val neuteredAdapter = ArrayAdapter(
            requireContext(),
            R.layout.list_item,
            neuteredItem
        )
        val genderAdapter = ArrayAdapter(
            requireContext(),
            R.layout.list_item,
            genderListItem
        )

        buddyViewModel.readBuddyDetail(buddyId)


        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = buddyViewModel

            genderTv.setAdapter(genderAdapter)
            isNeuteredTv.setAdapter(neuteredAdapter)

            birthDayTv.setOnClickListener {
                DialogForDatePicker.Builder(requireContext())
                    .setInitDate(CalendarUtil.parseStringToDate(binding.birthDayTv.text.toString())!!)
                    .setOnClickPositiveButton { newDate ->
                        binding.birthDayTv.text = CalendarUtil.parseDateToFormatString(newDate)
                    }
                    .build().show()
            }
            adoptDayTv.setOnClickListener {
                DialogForDatePicker.Builder(requireContext())
                    .setInitDate(CalendarUtil.parseStringToDate(binding.adoptDayTv.text.toString())!!)
                    .setOnClickPositiveButton { newDate ->
                        binding.adoptDayTv.text = CalendarUtil.parseDateToFormatString(newDate)
                    }
                    .build().show()
            }
            imgSelect.setOnClickListener {
                TedImagePicker.with(requireContext())
                    .start {
                        buddyViewModel.detailBuddy.value?.profile = it.toString()
                    }
            }
            binding.next.setOnClickListener {
                putData()
            }
        }


    }

    private fun putData() {
        val request = BuddyDetailDataLocal(
            buddyViewModel.getKind(),
            binding.nameTv.text.toString(),
            binding.genderTv.text.toString(),
            null,
            binding.isNeuteredTv.text.toString(),
            binding.birthDayTv.text.toString(),
            binding.adoptDayTv.text.toString(),
        ).replaceForDto()

        buddyViewModel.putBuddy(buddyId, request) {
            findNavController().popBackStack()
        }
    }
}