package com.nocapstone.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nocapstone.home.databinding.ItemBuddyProfileBinding
import com.nocapstone.home.dto.BuddyData
import okhttp3.internal.notify
import okhttp3.internal.notifyAll

class BuddyListAdapter(private val onClickBuddyListener: (Int) -> Unit) :
    ListAdapter<BuddyData, RecyclerView.ViewHolder>(BuddyListDiffCallback()) {

    var selectPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BuddyListViewHolder(
            ItemBuddyProfileBinding.inflate(
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

    inner class BuddyListViewHolder(private val binding: ItemBuddyProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BuddyData, position: Int) {

            binding.apply {
                buddyData = data
            }

            itemView.setOnClickListener {
                var color = false
                if (selectPosition == position) {
                    color = false
                    selectPosition = -1
                } else {
                    // 두개가 다르고
                    if (selectPosition != -1) {
                        // 이전에 선택되어있는게 있다면 white로 바꾸고
                        getItem(selectPosition).isSelect = false
                    }
                    selectPosition = position
                    color = true
                }
                getItem(position).isSelect = color
                onClickBuddyListener(position)
                notifyDataSetChanged()
            }
        }
    }
}

private class BuddyListDiffCallback : DiffUtil.ItemCallback<BuddyData>() {
    override fun areItemsTheSame(oldItem: BuddyData, newItem: BuddyData): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: BuddyData, newItem: BuddyData): Boolean {
        return oldItem == newItem
    }
}