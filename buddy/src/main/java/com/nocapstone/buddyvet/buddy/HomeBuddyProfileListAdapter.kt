package com.nocapstone.buddyvet.buddy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nocapstone.buddyvet.buddy.databinding.ItemBuddyProfileBinding
import com.nocapstone.buddyvet.buddy.databinding.ItemBuddyProfileHomeBinding
import com.nocapstone.buddyvet.buddy.domain.entity.BuddyData

class HomeBuddyProfileListAdapter() :
    ListAdapter<BuddyData, RecyclerView.ViewHolder>(HomeBuddyProfileListDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BuddyListViewHolder(
            ItemBuddyProfileHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BuddyListViewHolder) {
            val buddyData = getItem(position)
            holder.bind(buddyData, position)
        }
    }

    inner class BuddyListViewHolder(private val binding: ItemBuddyProfileHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BuddyData, position: Int) {

            binding.apply {
                buddyData = data
            }
        }
    }
}

private class HomeBuddyProfileListDiffCallback : DiffUtil.ItemCallback<BuddyData>() {
    override fun areItemsTheSame(oldItem: BuddyData, newItem: BuddyData): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: BuddyData, newItem: BuddyData): Boolean {
        return oldItem == newItem
    }
}