package com.nocapstone.onboarding.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.snackbar.Snackbar
import com.nocapstone.common.util.PermissionCallback
import com.nocapstone.common.util.PermissionObject
import com.nocapstone.common.util.PermissionType
import com.nocapstone.common_ui.DialogForPermission
import com.nocapstone.onboarding.R
import com.nocapstone.onboarding.databinding.FragmentOnBoardingViewPagerBinding
import dagger.hilt.android.AndroidEntryPoint

//todo companion?
private val messageArray = arrayOf(
    "주위 동물병원 찾기 및 산책 지수 제공을 위한 GPS접근 동의가 필요해요.",
    "진달을 위해서 갤러리 및 카메라 접근 동의가 필요해요." ,
    "버디들의 진단 정보 나 상태 정보 제공을 위한 알림 권한 동의가 필요해요"
)

private val imageArray = arrayOf(R.drawable.img_gps, R.drawable.img_gps, R.drawable.img_gps)



@AndroidEntryPoint
class OnBoardingViewPagerFragment : Fragment(), PermissionCallback {


    companion object{
        var viewpagerNum = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) 3 else 2
    }


    private var _binding: FragmentOnBoardingViewPagerBinding? = null
    private val binding get() = _binding!!
    private val requestPermissionLauncher = PermissionObject.checkPermission(this, { onSuccess() }, { onFail() })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoardingViewPagerBinding.inflate(inflater, container, false)
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
                    requestPermissionLauncher.launch(PermissionType.values()[index].permissionArray)
                }.build().show()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private inner class OnBoardingViewPagerAdapter(fragment: Fragment) :
        FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = viewpagerNum
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> OnBoarding1Fragment.newInstance()
                1 -> OnBoarding2Fragment.newInstance()
                else -> OnBoarding3Fragment.newInstance()
            }
        }


    }

    override fun onSuccess() {
        if (binding.viewpager2.currentItem == viewpagerNum - 1) {
            findNavController().navigate(R.id.next)
        }else{
            binding.viewpager2.currentItem++
        }
    }

    override fun onFail() {
        Snackbar.make(binding.root, "앱을 사용하기 위해 권한 허용을 해주세요!", Snackbar.LENGTH_SHORT).show()
    }
}