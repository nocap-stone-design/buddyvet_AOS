package com.nocapstone.diary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.databinding.ItemDiaryBinding
import com.nocapstone.diary.dto.Diary
import com.nocapstone.diary.ui.DiaryFragmentDirections


class DiaryAdapter(val fragment: Fragment) : ListAdapter<Diary, RecyclerView.ViewHolder>(
    DiaryDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CheckResultViewHolder(
            ItemDiaryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CheckResultViewHolder) {
            val checkResultData = getItem(position)
            holder.bind(checkResultData)
        }
    }

    inner class CheckResultViewHolder(private val binding: ItemDiaryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(diary: Diary) {
            binding.diary = diary
            itemView.setOnClickListener {
                fragment.findNavController().navigate(DiaryFragmentDirections.detailDiary(diary.diaryId))
            }
        }
    }
}


private class DiaryDiffCallback : DiffUtil.ItemCallback<Diary>() {
    override fun areItemsTheSame(oldItem: Diary, newItem: Diary): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Diary, newItem: Diary): Boolean {
        return oldItem == newItem
    }
}