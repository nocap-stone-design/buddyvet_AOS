package com.nocapstone.common_ui

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide


@BindingAdapter("photoUrl")
fun bindImageView(imageView: ImageView, photoUrl: String?) {
    Glide.with(imageView.context)
        .load(photoUrl)
        .placeholder(R.drawable.img_person)
        .into(imageView)
}

@BindingAdapter("uri")
fun bindImageView(imageView: ImageView, uri: Uri?) {
    Glide.with(imageView.context)
        .load(uri)
        .into(imageView)
}


@BindingAdapter("adapter", "submitList", requireAll = true)
fun bindViewPager(view: ViewPager2, adapter: RecyclerView.Adapter<*>, submitList: List<Any>?) {
    if (submitList == null) return
    view.adapter = adapter.apply {
        (this as ListAdapter<Any, *>).submitList(submitList.toMutableList())
    }
}

@BindingAdapter("visible")
fun setVisibility(view: View, flag: Boolean) {
    view.visibility = if (flag) View.VISIBLE else View.GONE
}

@BindingAdapter("adapter", "submitList", requireAll = true)
fun bindRecyclerView(view: RecyclerView, adapter: RecyclerView.Adapter<*>, submitList: List<Any>?) {
    view.adapter = adapter.apply {
        (this as ListAdapter<Any, *>).submitList(submitList?.toMutableList())
    }
}


