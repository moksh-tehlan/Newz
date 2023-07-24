package com.moksh.data.apiservice

import com.moksh.data.Constants
import com.moksh.data.model.NewsResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewzApiService {

    @GET("top-headlines?sortBy=publishedAt&pageSize=50")
    suspend fun getArticlesByCateGory(
        @Query("category") category:String,
        @Query("country") country:String = Constants.COUNTRY
    ):Response<NewsResponse>

    companion object {
        operator fun invoke(): NewzApiService{

            val interceptor = Interceptor{chain ->
                val url = chain.request()
                    .url
                    .newBuilder()
                    .addQueryParameter("apiKey",Constants.API_KEY)
                    .build()

                val request = chain.request().newBuilder().url(url).build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(NewzApiService::class.java)
        }
    }
}