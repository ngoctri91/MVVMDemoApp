package com.example.repository.repositories

import androidx.lifecycle.LiveData
import com.example.local.dao.VideoDao
import com.example.local.entities.VideoEntity
import com.example.remote.response.playlist.PlayListResponse
import com.example.remote.services.DevbyteService
import com.example.repository.base.NetworkBoundResource
import com.example.repository.base.Resource
import com.example.repository.extension.asDatabaseModel
import kotlinx.coroutines.Deferred

interface PlayListRepository {
    suspend fun getPlayList(isForceRefresh: Boolean = false): LiveData<Resource<List<VideoEntity>>>
}

class PlayListRepositoryImpl(
    val devbyteService: DevbyteService,
    val videoDao: VideoDao
) : PlayListRepository {
    override suspend fun getPlayList(isForceRefresh: Boolean): LiveData<Resource<List<VideoEntity>>> {
        return object : NetworkBoundResource<List<VideoEntity>, PlayListResponse>() {
            override fun processResponse(response: PlayListResponse): List<VideoEntity> {
                return response.asDatabaseModel()
            }

            override suspend fun saveCallResults(items: List<VideoEntity>) {
                videoDao.upsert(items)
            }

            override fun shouldFetch(data: List<VideoEntity>?): Boolean {
               return (data == null) || (data.isEmpty()) || isForceRefresh
            }

            override suspend fun loadFromDb(): List<VideoEntity> {
                return videoDao.getVideos()
            }

            override fun createCallAsync(): Deferred<PlayListResponse> {
                return devbyteService.getPlaylistAsync()
            }
        }.build().asLiveData()
    }
}
