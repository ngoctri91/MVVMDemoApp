package com.example.mvvmdemoapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.local.entities.VideoEntity
import com.example.mvvmdemoapp.databinding.DevbyteItemBinding

class DevByteViewHolder(val viewDataBinding: DevbyteItemBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
    fun bind(video: VideoEntity, videoClick: DevByteAdapter.VideoClick) {
        viewDataBinding.video = video
        viewDataBinding.videoCallback = videoClick
        viewDataBinding.videoThumbnail?.let { imageView ->
            Glide.with(imageView.context).load(viewDataBinding.video?.thumbnail.orEmpty()).into(imageView)
        }
        viewDataBinding.executePendingBindings()
    }
    companion object {
        fun from(parent: ViewGroup): DevByteViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = DevbyteItemBinding.inflate(layoutInflater, parent, false)
            return DevByteViewHolder(binding)
        }
    }
}