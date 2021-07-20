package com.example.local.dao.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(data: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(data: T)
}