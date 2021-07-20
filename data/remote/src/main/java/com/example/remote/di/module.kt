package com.example.remote.di

import com.example.remote.services.DevbyteService
import org.koin.dsl.module

val remoteModule = module() {
    single<DevbyteService> { DevbyteService() }
}