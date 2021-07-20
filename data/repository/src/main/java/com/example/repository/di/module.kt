package com.example.repository.di

import com.example.repository.repositories.PlayListRepository
import com.example.repository.repositories.PlayListRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<PlayListRepository> { PlayListRepositoryImpl(get(), get()) }
}