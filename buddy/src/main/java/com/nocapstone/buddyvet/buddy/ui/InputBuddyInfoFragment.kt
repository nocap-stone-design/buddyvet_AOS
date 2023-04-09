package com.nocapstone.buddyvet.buddy.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
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

        initMenu()

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

    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    android.R.id.home -> {
                        findNavController().popBackStack()
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


}