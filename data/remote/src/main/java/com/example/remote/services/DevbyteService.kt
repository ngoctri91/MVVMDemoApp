package com.example.remote.services

import com.example.remote.response.playlist.PlayListResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface DevbyteService {
    @GET("devbytes")
    fun getPlaylistAsync(): Deferred<PlayListResponse>

    companion object {
        operator fun invoke(): DevbyteService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url
                    .newBuilder()
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(READ_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(requestInterceptor)
                .addInterceptor(logging)

            return Retrofit.Builder()
                .client(okHttpClient.build())
                .baseUrl("https://android-kotlin-fun-mars-server.appspot.com/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DevbyteService::class.java)
        }

        private const val CONNECT_TIMEOUT = 30L
        private const val READ_WRITE_TIMEOUT = 60L
    }
}