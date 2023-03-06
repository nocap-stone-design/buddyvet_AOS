package com.nocapstone.common_ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nocapstone.common_ui.databinding.ItemImageDetailBinding

class ImageDetailAdapter : ListAdapter<ImageInfo, RecyclerView.ViewHolder>(ImageDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImageViewHolder(
            ItemImageDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ImageViewHolder){
            val image = getItem(position)
            holder.bind(image)
        }
    }

    inner class ImageViewHolder(private val binding: ItemImageDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageInfo: ImageInfo) {
            with(binding) {
                imageUri = imageInfo.url
                executePendingBindings()
            }
        }
    }

    private class ImageDiffCallback : DiffUtil.ItemCallback<ImageInfo>() {
        override fun areItemsTheSame(oldItem: ImageInfo, newItem: ImageInfo): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ImageInfo, newItem: ImageInfo): Boolean {
            return oldItem == newItem
        }
    }

}



