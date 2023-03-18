package com.nocapstone.home

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.nocapstone.home.databinding.DialogForSelectBuddyBinding
import com.nocapstone.home.dto.BuddyData

class DialogForSelectBuddy(
    context: Context,
    private val onClickButton: () -> Unit,
    private val buddyListData: List<BuddyData>,
    private val viewModel : HomeViewModel
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
            adapter = BuddyListAdapter(){
                viewModel.setSelectCheckBuddy(it)
            }
            buddyList = buddyListData
        }

        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    class Builder(private val context: Context, private val buddyList : List<BuddyData>,private val viewModel: HomeViewModel) {
        private var onClickButton = {}

        fun setOnClickButton(onClickButton: () -> Unit) = apply {
            this.onClickButton = onClickButton
        }

        fun build() = DialogForSelectBuddy(
            context,
            onClickButton,
            buddyList,
            viewModel
        )
    }


}