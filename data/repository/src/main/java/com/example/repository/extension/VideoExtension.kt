package com.example.repository.extension

import com.example.local.entities.VideoEntity
import com.example.remote.response.playlist.PlayListResponse
import com.example.remote.response.playlist.Video

/**
 * Convert Network results to database objects
 */
fun PlayListResponse.asDatabaseModel(): List<VideoEntity> {
    return videos.map {
        VideoEntity(
            title = it.title,
            description = it.description,
            url = it.url,
            updated = it.updated,
            thumbnail = it.thumbnail)
    }
}