package com.nocapstone.common_ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.nocapstone.common_ui.CalendarUtil.Companion.getDate
import com.nocapstone.common_ui.databinding.DialogForDatepickerBinding
import java.util.*

class DialogForDatePicker(
    context: Context,
    private val initDate: Date,
    private val onClickPositiveButton: (Date) -> Unit
) : Dialog(context) {
    private lateinit var binding: DialogForDatepickerBinding
    private var calendar: Calendar = Calendar.getInstance()

    init {
        calendar.time = initDate
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogForDatepickerBinding.inflate(layoutInflater).apply {
            datePicker.init(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),null)
            positiveButton.setOnClickListener {
                calendar.set(datePicker.year,datePicker.month,datePicker.dayOfMonth)
                onClickPositiveButton.invoke(calendar.time)
                dismiss()
            }
            negativeButton.setOnClickListener {
                dismiss()
            }
        }
        setContentView(binding.root)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    class Builder(private val context: Context) {
        private lateinit var initDate: Date
        private var onClickPositiveButton : (Date) -> Unit = {}


        fun setInitDate(initDate: Date) = apply {
            this.initDate = initDate
        }

        fun setOnClickPositiveButton(onClickPositiveButton: (Date) -> Unit) = apply {
            this.onClickPositiveButton = onClickPositiveButton
        }


        fun build() = DialogForDatePicker(
            context,
            initDate,
            onClickPositiveButton
        )
    }
}