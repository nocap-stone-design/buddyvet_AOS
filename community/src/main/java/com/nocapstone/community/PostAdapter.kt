package com.nocapstone.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nocapstone.community.databinding.ItemPostBinding
import com.nocapstone.community.dto.Post
import com.nocapstone.community.ui.CommunityFragmentDirections

class PostAdapter(val fragment: Fragment) : ListAdapter<Post, RecyclerView.ViewHolder>(
    DiaryDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CheckResultViewHolder(
            ItemPostBinding.inflate(
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

    inner class CheckResultViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.post = post
            itemView.setOnClickListener {
                fragment.findNavController().navigate(CommunityFragmentDirections.detailPost(post.postId))
            }
        }
    }
}


private class DiaryDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}