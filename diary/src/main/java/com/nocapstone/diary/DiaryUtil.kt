package com.nocapstone.diary

import com.nocapstone.diary.dto.CalendarData
import com.nocapstone.diary.dto.DiaryData
import java.util.*

class DiaryUtil {
    companion object {
        fun getDatesInMonth(
            year: Int,
            month: Int,
            dataInDate: DiaryData?
        ): MutableList<CalendarData> {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month - 1)
            calendar.set(Calendar.DAY_OF_MONTH, 1)

            val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            val lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

            val calendarDataSize = MAX_CALENDAR_DATA_SIZE
            val calendarArr = Array(calendarDataSize) { CalendarData("", null) }

            arrayOf("일", "월", "화", "수", "목", "금", "토").forEachIndexed { index, dayOfWeek ->
                calendarArr[index] = CalendarData(dayOfWeek, null)
            }

            for (i in 1..lastDayOfMonth) {
                calendarArr[firstDayOfWeek + i + 5] = CalendarData(i.toString(), null)
            }

            dataInDate?.diaries?.forEach {
                calendarArr[firstDayOfWeek + it.day + 5] = CalendarData(it.day.toString(), it)
            }

            return calendarArr.toMutableList()
        }
    }
}