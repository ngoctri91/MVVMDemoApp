package com.example.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.remote.response.playlist.PlayListResponse
import com.example.remote.response.playlist.Video
import com.example.remote.services.DevbyteService.Companion.videoApi
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FetchVideoTest {
    private lateinit var videos: MutableLiveData<List<Video>>

    @Before
    fun setUp() {
        videos = fetchVideos()
    }

    private fun fetchVideos(): MutableLiveData<List<Video>> {
        val responseLiveData: MutableLiveData<List<Video>> = MutableLiveData()

        val dataRequest: Call<PlayListResponse> = videoApi.getPlaylistAsyncTest()

        dataRequest.enqueue(object : Callback<PlayListResponse> {
            override fun onResponse(
                call: Call<PlayListResponse>,
                response: Response<PlayListResponse>
            ) {
                val listVideo: PlayListResponse? = response.body()
                val video: List<Video> = (listVideo?.videos ?: mutableListOf())

                responseLiveData.value = video
            }

            override fun onFailure(call: Call<PlayListResponse>, t: Throwable) {
                Log.d("TAG", "Error" + t.message)
            }
        })
        return responseLiveData
    }

    @Test
    fun isConnectedServer_haveDataReturn() {
        assertThat(videos).isNotEqualTo(null)
    }
}