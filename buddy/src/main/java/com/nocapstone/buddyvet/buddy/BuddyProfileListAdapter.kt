package com.nocapstone.buddyvet.buddy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nocapstone.buddyvet.buddy.databinding.ItemBuddyProfileBinding
import com.nocapstone.buddyvet.buddy.domain.entity.BuddyData

class BuddyProfileListAdapter(
    private val ColorChange: Boolean,
    private val onClickBuddyListener: (Int) -> Unit
) :
    ListAdapter<BuddyData, RecyclerView.ViewHolder>(BuddyProfileListDiffCallback()) {

    var prePosition = -1

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
                onClickBuddyListener(position)
                if (ColorChange) {
                    if (prePosition >= 0) {
                        getItem(prePosition).isSelect = false
                    }
                    getItem(position).isSelect = true
                    prePosition = position
                    notifyDataSetChanged()
                }
            }
        }
    }
}

private class BuddyProfileListDiffCallback : DiffUtil.ItemCallback<BuddyData>() {
    override fun areItemsTheSame(oldItem: BuddyData, newItem: BuddyData): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: BuddyData, newItem: BuddyData): Boolean {
        return oldItem == newItem
    }
}