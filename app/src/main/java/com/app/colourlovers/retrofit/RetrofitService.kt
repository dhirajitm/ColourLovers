package com.app.colourlovers.retrofit

import com.app.colourlovers.data.ColourResponseItem
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitService {

//    https://www.colourlovers.com/api/colors?keywords=KEYWORDS&format=json&numResults=20

    @GET("colors")
    fun getAllColours(@Query("keywords") keywords: String, @Query("format") format: String, @Query("numResult") numResult: Int, @Query("resultOffset") offset: Int): Call<List<ColourResponseItem>>

    companion object {

        var httpClient = OkHttpClient.Builder()

        var retrofitService: RetrofitService? = null

        fun getInstance(): RetrofitService {

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            httpClient.addInterceptor(httpLoggingInterceptor)

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://www.colourlovers.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}