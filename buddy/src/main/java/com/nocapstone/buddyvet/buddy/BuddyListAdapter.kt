package com.nocapstone.buddyvet.buddy

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nocapstone.buddyvet.buddy.databinding.ItemBuddyDetailBinding
import com.nocapstone.buddyvet.buddy.domain.entity.BuddyData
import com.nocapstone.buddyvet.buddy.ui.MyBuddyFragment
import com.nocapstone.common_ui.R.menu.common_menu

class BuddyListAdapter(
    val fragment: Fragment
) : ListAdapter<BuddyData, RecyclerView.ViewHolder>(BuddyListDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BuddyListViewHolder(
            ItemBuddyDetailBinding.inflate(
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

    inner class BuddyListViewHolder(private val binding: ItemBuddyDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BuddyData, position: Int) {
            binding.apply {
                buddyData = data
                more.setOnClickListener {
                    val id = getItem(position).id
                    showMenu(it, common_menu,id)
                }
            }
        }

        private fun showMenu(v: View, @MenuRes menuRes: Int, id: Long) {
            val popup = PopupMenu(fragment.requireContext(), v)
            popup.menuInflater.inflate(menuRes, popup.menu)

            popup.setOnMenuItemClickListener { menuItem: MenuItem ->
                when(menuItem.itemId) {
                    com.nocapstone.common_ui.R.id.delete -> {
                        (fragment as MyBuddyFragment).buddyViewModel.deleteBuddy(id)
                    }
                    com.nocapstone.common_ui.R.id.put -> {
                    }
                }
                true
            }
            // Show the popup menu.
            popup.show()
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