package com.example.mvvmdemoapp.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.local.entities.VideoEntity

class DevByteAdapter (val callback: VideoClick) : RecyclerView.Adapter<DevByteViewHolder>() {
    var videos: List<VideoEntity> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevByteViewHolder {
        return DevByteViewHolder.from(parent)
    }

    override fun getItemCount() = videos.size

    override fun onBindViewHolder(holder: DevByteViewHolder, position: Int) {
        val video = videos[position]
        holder.bind(video, callback)
    }

    interface VideoClick {
        fun onClick(video: VideoEntity)
    }
}