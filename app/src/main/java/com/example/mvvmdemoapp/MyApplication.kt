package com.example.mvvmdemoapp

//import com.example.local.di.localModule
//import com.example.remote.di.remoteModule
//import com.example.repository.di.repositoryModule
import android.app.Application
import com.example.local.di.localModule
import com.example.mvvmdemoapp.di.appModule
import com.example.remote.di.remoteModule
import com.example.repository.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@MyApplication)
            modules(localModule)
            modules(remoteModule)
            modules(repositoryModule)
            modules(appModule)
        }
    }
}