package com.example.local.di

import com.example.local.AppDatabase
import com.example.local.dao.VideoDao
import org.koin.dsl.module

val localModule = module() {
    single<AppDatabase> { AppDatabase(get())  }
    single<VideoDao> { AppDatabase(get()).videoDao()  }
}