package ru.kesva.makechoice.data.source.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import ru.kesva.makechoice.data.model.PexelsResponse

interface PexelsSearchPhotoApi {
    companion object {
        private const val apiBaseUrl = "https://api.pexels.com/"
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

    @GET("v1/search")
    fun getPhotoResponse(
        @Header("Authorization") key: String,
        @Query("locale") locale: String,
        @Query("query") query: String
    ): PexelsResponse

}