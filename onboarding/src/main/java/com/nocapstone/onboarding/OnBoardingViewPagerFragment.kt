package com.nocapstone.onboarding

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.snackbar.Snackbar
import com.nocapstone.common_ui.DialogForPermission
import com.nocapstone.common_ui.MainActivityUtil
import com.nocapstone.onboarding.databinding.FragmentOnBoardingViewPagerBinding
import kotlin.math.log


class OnBoardingViewPagerFragment : Fragment() {


    private var _binding: FragmentOnBoardingViewPagerBinding? = null
    private val binding get() = _binding!!
    var viewpagerNum: Int = 0

    private val messageArray = arrayOf("gps 권한", "알림 권한", "카메라 권한")
    private val imageArray = arrayOf(R.drawable.img_gps, R.drawable.img_gps, R.drawable.img_gps)
    private val permissionArray = arrayOf(
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,  // 도시 블록 단위
            Manifest.permission.ACCESS_FINE_LOCATION,
        ),
        arrayOf(Manifest.permission.CAMERA),
        arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    )

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.all { it.value }) {
            if (binding.viewpager2.currentItem == viewpagerNum - 1) {
               /*
                (activity as MainActivityUtil).run {
                    navigateToHome(this@OnBoardingViewPagerFragment)
                }*/
                findNavController().navigate(R.id.next)
            }
            binding.viewpager2.currentItem++
        } else {
            Snackbar.make(binding.root, "앱을 사용하기 위해 권한 허용을 해주세요!", Snackbar.LENGTH_SHORT).show()
        }
    }

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

            val index = binding.viewpager2.currentItem

            DialogForPermission.Builder(requireContext())
                .setMessage(messageArray[index])
                .setImg(imageArray[index])
                .setOnClickButton {
                    requestPermission(permissionArray[index])
                }.build().show()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestPermission(permissionArray: Array<String>) =
        requestPermissionLauncher.launch(permissionArray)

    private fun hideAppBar() {
        (activity as MainActivityUtil).run {
            setVisibilityBottomAppbar(View.GONE)
        }
        (activity as MainActivityUtil).run {
            setVisibilityTopAppBar(View.GONE)
        }
    }

    private inner class OnBoardingViewPagerAdapter(fragment: Fragment) :
        FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = setViewpagerNum()

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> OnBoarding1Fragment.newInstance()
                1 -> OnBoarding2Fragment.newInstance()
                else -> OnBoarding3Fragment.newInstance()
            }
        }

        fun setViewpagerNum(): Int{
            viewpagerNum = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) 3 else 2
            return viewpagerNum
        }
    }
}