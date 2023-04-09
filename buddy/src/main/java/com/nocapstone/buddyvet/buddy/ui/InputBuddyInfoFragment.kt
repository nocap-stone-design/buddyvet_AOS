package com.nocapstone.buddyvet.buddy.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nocapstone.buddyvet.buddy.R
import com.nocapstone.buddyvet.buddy.databinding.FragmentInputBuddyInfoBinding
import com.nocapstone.common_ui.CalendarUtil
import com.nocapstone.common_ui.DialogForDatePicker
import com.nocapstone.common_ui.R.layout.list_item
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import java.lang.reflect.GenericArrayType


@AndroidEntryPoint
class InputBuddyInfoFragment : Fragment() {

    private var _binding: FragmentInputBuddyInfoBinding? = null
    private val binding get() = _binding!!
    private val buddyViewModel: BuddyViewModel by viewModels({ requireActivity() })
    private val neuteredItem = listOf("예", "아니오")
    private val genderListItem = listOf("남자", "여자")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInputBuddyInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val neuteredAdapter = ArrayAdapter(
            requireContext(),
            list_item,
            neuteredItem
        )


        val genderAdapter = ArrayAdapter(
            requireContext(),
            list_item,
            genderListItem
        )

        with(binding) {
            viewModel = buddyViewModel
            lifecycleOwner = viewLifecycleOwner
            genderTv.setAdapter(genderAdapter)
            isNeuteredTv.setAdapter(neuteredAdapter)
            birthDayTv.text = CalendarUtil.getTodayDate()
            adoptDayTv.text = CalendarUtil.getTodayDate()
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
                        buddyViewModel.setSelectImgUri(it)
                    }
            }
        }

        binding.next.setOnClickListener {
            setData()
            findNavController().navigate(R.id.next)
        }
    }

    private fun setData() {
        with(binding) {
            buddyViewModel.setName(nameTv.text.toString())
            buddyViewModel.setNeutered(
                when (isNeuteredTv.text.toString()) {
                    neuteredItem[0] -> true
                    else -> false
                }
            )
            buddyViewModel.setGender(
                when (genderTv.text.toString()) {
                    genderListItem[0] -> "M"
                    else -> "F"
                }
            )
            buddyViewModel.setAdoptDay(adoptDayTv.text.toString())
            buddyViewModel.setBirthDay(birthDayTv.text.toString())
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}