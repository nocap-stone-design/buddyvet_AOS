package com.nocapstone.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nocapstone.home.databinding.ItemCheckResultBinding
import com.nocapstone.home.dto.CheckResultData

class CheckResultAdapter() : ListAdapter<CheckResultData, RecyclerView.ViewHolder>(CheckResultDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CheckResultViewHolder(
            ItemCheckResultBinding.inflate(
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

    inner class CheckResultViewHolder(private val binding: ItemCheckResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(checkResultData: CheckResultData) {
            binding.checkResultData = checkResultData
        }
    }
}

private class CheckResultDiffCallback : DiffUtil.ItemCallback<CheckResultData>() {
    override fun areItemsTheSame(oldItem: CheckResultData, newItem: CheckResultData): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: CheckResultData, newItem: CheckResultData): Boolean {
        return oldItem == newItem
    }
}