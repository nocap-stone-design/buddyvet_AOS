package com.nocapstone.buddyvet.buddy.ui

import android.os.Bundle
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


@AndroidEntryPoint
class InputBuddyInfoFragment : Fragment() {

    private var _binding: FragmentInputBuddyInfoBinding? = null
    private val binding get() = _binding!!
    private val buddyViewModel: BuddyViewModel by viewModels({ requireActivity() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInputBuddyInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val breadAdapter = ArrayAdapter(
            requireContext(),
            list_item,
            listOf("예", "아니오")
        )

        val genderAdapter = ArrayAdapter(
            requireContext(),
            list_item,
            listOf("남자", "여자", "없음")
        )

        with(binding) {
            genderTv.setAdapter(genderAdapter)
            isNeuteredTv.setAdapter(breadAdapter)
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
        }

        binding.next.setOnClickListener {
            setData()
            buddyViewModel
            findNavController().navigate(R.id.next)
        }
    }

    private fun setData(){
        with(binding){
            buddyViewModel.setName(nameEt.editText.toString())
            buddyViewModel.setNeutered(isNeuteredTv.text.toString())
            buddyViewModel.setGender(genderTv.text.toString())
            buddyViewModel.setAdoptDay(adoptDayTv.text.toString())
            buddyViewModel.setBirthDay(birthDayTv.text.toString())
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}