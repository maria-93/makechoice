package ru.kesva.makechoice.data.source.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface PexelsSearchPhotoApi {
    companion object {
        private const val apiBaseUrl = "https://api.pexels.com/v1/search"
        fun create(): PexelsSearchPhotoApi {
            val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
            val httpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(apiBaseUrl)
                .client(httpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
            return retrofit.create(PexelsSearchPhotoApi::class.java)
        }
    }


}