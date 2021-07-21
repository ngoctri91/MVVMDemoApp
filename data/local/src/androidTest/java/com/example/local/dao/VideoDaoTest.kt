package com.example.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.runner.RunWith
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.local.AppDatabase
import com.example.local.entities.VideoEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class VideoDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: VideoDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.videoDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertVideoItem() = runBlockingTest {
        val videoItem = VideoEntity("url", "2021-01-02", "Film", "Description of film", "url")
        dao.upsert(videoItem)

        val allShoppingItems = dao.getVideos()

        assertThat(allShoppingItems).contains(videoItem)
    }

    @Test
    fun checkUrl() = runBlockingTest {
        val allShoppingItems = dao.getVideos()

        for (i in allShoppingItems){
            assertThat(i.url).isNotEqualTo("")
        }
    }
}
