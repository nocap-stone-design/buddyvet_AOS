package com.nocapstone.home.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.nocapstone.buddyvet.buddy.BuddyProfileListAdapter
import com.nocapstone.buddyvet.buddy.ui.BuddyViewModel
import com.nocapstone.home.databinding.DialogForSelectBuddyBinding


class DialogForSelectBuddy constructor(
    context: Context,
    private val onClickButton: () -> Unit,
    private val buddyViewModel: BuddyViewModel
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

        binding.apply {
            viewModel = buddyViewModel
            adapter = BuddyProfileListAdapter(true) { nowSelectPosition ->
                buddyViewModel.setSelectCheckBuddy(nowSelectPosition)
            }
        }

        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    class Builder(
        private val context: Context,
        private val buddyViewModel: BuddyViewModel
    ) {
        private var onClickButton = {}

        fun setOnClickButton(onClickButton: () -> Unit) = apply {
            this.onClickButton = onClickButton
        }

        fun build() = DialogForSelectBuddy(
            context,
            onClickButton,
            buddyViewModel
        )
    }


}