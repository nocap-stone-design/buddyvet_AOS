package com.nocapstone.onboarding.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nocapstone.onboarding.R
import com.nocapstone.onboarding.databinding.FragmentInputBuddyViewPagerBinding
import com.nocapstone.onboarding.databinding.FragmentOnBoardingViewPagerBinding


class InputBuddyViewPagerFragment : Fragment() {

    private var _binding: FragmentInputBuddyViewPagerBinding? = null
    private val binding
    get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInputBuddyViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewpager2.let {
            it.adapter = InputBuddyViewPagerAdapter(this)
            binding.indicator.setViewPager(it)
        }

        binding.nextButton.setOnClickListener {
            binding.viewpager2.currentItem++
        }
    }

    private inner class InputBuddyViewPagerAdapter(fragment: Fragment) :
        FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = OnBoardingViewPagerFragment.viewpagerNum
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> InputBuddyTypeFragment.newInstance()
                1 -> InputBuddyInfoFragment.newInstance()
                else -> InputBuddyInfo2Fragment.newInstance()
            }
        }


    }

}