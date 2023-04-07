package com.nocapstone.diary

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.databinding.ItemCalendarBinding
import com.nocapstone.diary.dto.CalendarData
import com.nocapstone.diary.ui.DiaryFragmentDirections


class DiaryAdapter(val fragment: Fragment) : ListAdapter<CalendarData, RecyclerView.ViewHolder>(
    DiaryDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DiaryViewHolder(
            ItemCalendarBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DiaryViewHolder) {
            val checkResultData = getItem(position)
            holder.bind(checkResultData)
        }
    }

    inner class DiaryViewHolder(private val binding: ItemCalendarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(calendarData: CalendarData) {

            if (calendarData.day.toIntOrNull() == null) {
                // 날짜가 없다면
                binding.image.visibility = View.GONE
            } else {
                // 날짜 표시 가능한 부분이라면
                binding.date.setTextColor(Color.parseColor("#FFFFFF"))
                if (calendarData.diary != null) {
                    binding.imgUrl = calendarData.diary.thumbnail
                }
            }
            binding.date.text = calendarData.day

            itemView.setOnClickListener {
                if (calendarData.diary != null){
                    fragment.findNavController().navigate(DiaryFragmentDirections.detailDiary(calendarData.diary.diaryId))
                }
            }

        }
    }
}


private class DiaryDiffCallback : DiffUtil.ItemCallback<CalendarData>() {
    override fun areItemsTheSame(oldItem: CalendarData, newItem: CalendarData): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: CalendarData, newItem: CalendarData): Boolean {
        return oldItem == newItem
    }
}