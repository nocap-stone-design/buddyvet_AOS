package com.nocapstone.common_ui

import android.widget.DatePicker
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class CalendarUtil {
    companion object {

        private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        private val dateFormatNoDay = SimpleDateFormat("yyyy-MM", Locale.KOREA)

        private fun Calendar.getYear() = get(Calendar.YEAR)
        private fun Calendar.getMonth() = get(Calendar.MONTH)+1
        private fun Calendar.getDay() = get(Calendar.DAY_OF_MONTH)


        fun getTodayDate(): String = Calendar.getInstance().let {
            return parseDateToFormatString(it.time)!!
        }

        fun getTodayDateNoDay() : String = Calendar.getInstance().let {
            return "${it.getYear()}-${it.getMonth()}"
        }

        // private fun getDateFormat(year : String, month: String, day: String) : String = "${year} ${getMonthFormat(month)}"

        fun DatePicker.getDate() = "$year-${month+1}-$dayOfMonth"

        fun parseDateToFormatString(date: Date): String? =
            dateFormat.format(date)

        fun parseStringToDateNoDay(textView: TextView):Date? =
            dateFormatNoDay.parse(textView.text.toString())

        fun parseStringToDate(string: String): Date? =
            dateFormat.parse(string)

        fun getMonthFormat(month: String): String {
            return when (month.toInt()) {
                Calendar.FEBRUARY -> "Feb"
                Calendar.MARCH -> "Mar"
                Calendar.APRIL -> "Apr"
                Calendar.MAY -> "May"
                Calendar.JUNE -> "Jun"
                Calendar.JULY -> "Jul"
                Calendar.AUGUST -> "Aug"
                Calendar.SEPTEMBER -> "Sep"
                Calendar.OCTOBER -> "Oct"
                Calendar.NOVEMBER -> "Nov"
                Calendar.DECEMBER -> "Dec"
                else -> throw IllegalArgumentException("Invalid month: $month")
            }
        }


    }


}