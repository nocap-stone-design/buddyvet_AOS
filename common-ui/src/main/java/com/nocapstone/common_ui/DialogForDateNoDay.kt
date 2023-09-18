package com.nocapstone.common_ui


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.nocapstone.common_ui.databinding.DialogForDatepickerNoDayBinding
import java.util.*

class DialogForDateNoDay(context: Context,
                         private val initDate: Date,
                         private val onClickPositiveButton: (String) -> Unit
) : Dialog(context) {
    private lateinit var binding: DialogForDatepickerNoDayBinding
    private var calendar: Calendar = Calendar.getInstance()

    init {
        calendar.time = initDate
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogForDatepickerNoDayBinding.inflate(layoutInflater).apply {

            pickerYear.maxValue = 2050
            pickerYear.minValue = 1980

            pickerYear.value = calendar.get(Calendar.YEAR)

            pickerMonth.maxValue = 12
            pickerMonth.minValue = 1

            pickerMonth.value = calendar.get(Calendar.MONTH)+1

            positiveButton.setOnClickListener {
                onClickPositiveButton.invoke("${pickerYear.value}-${pickerMonth.value}")
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
        private var onClickPositiveButton: (String) -> Unit = {}


        fun setInitDate(initDate: Date) = apply {
            this.initDate = initDate
        }

        fun setOnClickPositiveButton(onClickPositiveButton: (String) -> Unit) = apply {
            this.onClickPositiveButton = onClickPositiveButton
        }


        fun build() = DialogForDateNoDay(
            context,
            initDate,
            onClickPositiveButton
        )
    }

}