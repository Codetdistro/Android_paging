package com.example.android_paging.network

import com.example.android_paging.data.news
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {


    @FormUrlEncoded
    @POST("LGLNews")
    suspend fun getResult(
        @Field("page")page:Int
    ):news

    companion object{
        private const val BASE_URL = "https://news.soolegal.com/api/index.php/"
        operator fun invoke():Api=Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().also { client->
                val logger = HttpLoggingInterceptor()
                logger.level = HttpLoggingInterceptor.Level.BASIC
                client.addInterceptor(logger)
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}