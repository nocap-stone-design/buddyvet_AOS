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
import com.nocapstone.buddyvet.buddy.util.BuddyType
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

        val breedListItem = if (buddyViewModel.selectType.value == BuddyType.DOG) {
            resources.getStringArray(com.nocapstone.common.R.array.DogBreed)
        } else {
            resources.getStringArray(com.nocapstone.common.R.array.CatBreed)
        }

        val breadAdapter = ArrayAdapter(
            requireContext(),
            com.nocapstone.common_ui.R.layout.list_item,
            breedListItem
        )
        binding.breedTv.setAdapter(breadAdapter)

        val genderAdapter = ArrayAdapter(
            requireContext(),
            com.nocapstone.common_ui.R.layout.list_item,
            listOf("남자", "여자", "없음")
        )
        binding.genderTv.setAdapter(genderAdapter)

        binding.next.setOnClickListener {
            findNavController().navigate(R.id.next)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}