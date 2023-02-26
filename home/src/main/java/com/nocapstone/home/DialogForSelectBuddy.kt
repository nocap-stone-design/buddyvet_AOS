package com.nocapstone.home

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.nocapstone.common_ui.DialogForPermission
import com.nocapstone.common_ui.databinding.DialogForPermissionBinding
import com.nocapstone.home.databinding.DialogForSelectBuddyBinding

class DialogForSelectBuddy(
    context: Context,
    private val onClickButton: () -> Unit
    //todo budylist
) : Dialog(context) {
    private lateinit var binding: DialogForSelectBuddyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogForSelectBuddyBinding.inflate(layoutInflater).apply {
            startEyeCheckBtn.setOnClickListener {
                onClickButton.invoke()
                dismiss()
            }
        }
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    class Builder(private val context: Context) {
        private var onClickButton = {}

        fun setOnClickButton(onClickButton: () -> Unit) = apply {
            this.onClickButton = onClickButton
        }

        fun build() = DialogForSelectBuddy(
            context,
            onClickButton
        )
    }


}