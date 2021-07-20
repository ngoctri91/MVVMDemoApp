package com.example.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.local.dao.VideoDao
import com.example.local.entities.VideoEntity

@Database(
    entities = [
        VideoEntity::class
    ],

    views = [
    ],

    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao
    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "AppDatabase.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}