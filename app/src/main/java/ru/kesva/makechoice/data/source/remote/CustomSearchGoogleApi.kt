package ru.kesva.makechoice.data.source.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.kesva.makechoice.data.model.PhotoResponse

interface CustomSearchGoogleApi {
    companion object {
        private const val apiBaseUrl = "https://www.googleapis.com"
        fun create() : CustomSearchGoogleApi {
            val logging =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
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
            return retrofit.create(CustomSearchGoogleApi::class.java)
        }
    }

    @GET("/customsearch/v1")
    suspend fun getPhotoResponse(
        @Query("key") key: String,
        @Query("cx") cx: String,
        @Query("searchType") searchType: String,
        @Query("num") num: Int,
        @Query("hl") language: String,
        @Query("imgType") imgType: String,
        @Query("q") query: String
    ): PhotoResponse
}