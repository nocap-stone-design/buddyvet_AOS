package com.nocapstone.common_ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.nocapstone.common_ui.databinding.DialogForPermissionBinding

class DialogForPermission(
    context: Context,
    private val message: String,
    private val imgId: Int,
    private val onClickButton: () -> Unit
) : Dialog(context){

    private lateinit var binding: DialogForPermissionBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogForPermissionBinding.inflate(layoutInflater).apply {
            dialogTv.text = message
            dialogIv.setImageResource(imgId)
            dialogButton.setOnClickListener {
                onClickButton.invoke()
                dismiss()
            }
        }
        setContentView(binding.root)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    class Builder(private val context: Context) {
        private var message = ""
        private var imgId = 0
        private var onClickButton = {}

        fun setMessage(message: String) = apply {
            this.message = message
        }

        fun setImg(imgId: Int) = apply {
            this.imgId = imgId
        }

        fun setOnClickButton(onClickButton: () -> Unit) = apply {
            this.onClickButton = onClickButton
        }

        fun build() = DialogForPermission(
            context,
            message,
            imgId,
            onClickButton
        )
    }
}