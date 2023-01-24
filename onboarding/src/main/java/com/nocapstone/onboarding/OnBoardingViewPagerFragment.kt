package com.nocapstone.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nocapstone.common_ui.MainActivityUtil
import com.nocapstone.onboarding.databinding.FragmentOnBoardingViewPagerBinding

private const val NUM_PAGES = 3

class OnBoardingViewPagerFragment : Fragment() {

    private var _binding: FragmentOnBoardingViewPagerBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoardingViewPagerBinding.inflate(inflater, container, false)
        hideAppBar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewpager2.let {
            it.adapter = OnBoardingViewPagerAdapter(this)
            binding.indicator.setViewPager(it)
        }

        binding.nextButton.setOnClickListener {
            if (binding.viewpager2.currentItem == NUM_PAGES - 1)
                (activity as MainActivityUtil).run {
                    navigateToHome(this@OnBoardingViewPagerFragment)
                }
            else
                binding.viewpager2.currentItem++
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun hideAppBar(){
        (activity as MainActivityUtil).run {
            setVisibilityBottomAppbar(View.GONE)
        }
        (activity as MainActivityUtil).run {
            setVisibilityTopAppBar(View.GONE)
        }
    }

    private inner class OnBoardingViewPagerAdapter(fragment: Fragment) :
        FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> OnBoarding1Fragment.newInstance()
                1 -> OnBoarding2Fragment.newInstance()
                else -> OnBoarding3Fragment.newInstance()
            }
        }

    }
}