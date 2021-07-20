package com.example.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.local.dao.base.BaseDao
import com.example.local.entities.VideoEntity

@Dao
interface VideoDao : BaseDao<VideoEntity> {
    @Query("select * from videos")
    suspend fun getVideos(): List<VideoEntity>
}