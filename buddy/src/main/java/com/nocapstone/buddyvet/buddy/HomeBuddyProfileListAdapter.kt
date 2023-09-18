package com.nocapstone.buddyvet.buddy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nocapstone.buddyvet.buddy.databinding.ItemBuddyProfileBinding
import com.nocapstone.buddyvet.buddy.databinding.ItemBuddyProfileHomeBinding
import com.nocapstone.buddyvet.buddy.domain.entity.BuddyData
import com.nocapstone.buddyvet.buddy.domain.entity.BuddyDataLocal

class HomeBuddyProfileListAdapter() :
    ListAdapter<BuddyDataLocal, RecyclerView.ViewHolder>(HomeBuddyProfileListDiffCallback()) {
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
            val buddyDataLocal = getItem(position)
            holder.bind(buddyDataLocal, position)
        }
    }

    inner class BuddyListViewHolder(private val binding: ItemBuddyProfileHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BuddyDataLocal, position: Int) {
            binding.apply {
                buddyData = data
            }
        }
    }
}

private class HomeBuddyProfileListDiffCallback : DiffUtil.ItemCallback<BuddyDataLocal>() {
    override fun areItemsTheSame(oldItem: BuddyDataLocal, newItem: BuddyDataLocal): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: BuddyDataLocal, newItem: BuddyDataLocal): Boolean {
        return oldItem == newItem
    }
}