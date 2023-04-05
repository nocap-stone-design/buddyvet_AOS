package com.nocapstone.common_ui


import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


object CustomToast {
    fun createToast(fragment: Fragment, message: String, toastType: ToastType) {
        MotionToast.createColorToast(
            fragment.requireActivity(),
            toastType.title,
            message,
            toastType.style,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.SHORT_DURATION,
            ResourcesCompat.getFont(
                fragment.requireContext(),
                www.sanju.motiontoast.R.font.helvetica_regular
            )
        )
    }
}


enum class ToastType(val style: MotionToastStyle, val title: String) {
    SUCCESS(MotionToastStyle.SUCCESS, "성공 \uD83D\uDE0D"),
    ERROR(MotionToastStyle.ERROR, "에러 ☹ "),
    INFO(MotionToastStyle.INFO, "알림")
}

data class ToastSet(
    val message: String,
    val type: ToastType
)

