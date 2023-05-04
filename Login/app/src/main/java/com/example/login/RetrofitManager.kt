package com.example.login

import okhttp3.OkHttpClient
import retrofit2.Retrofit.*
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager private constructor() {
    private val apiService: APIService
    private val okHttpClient = OkHttpClient()

    init {
        val retrofit = Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        apiService = retrofit.create(APIService::class.java)
    }

    val api: APIService
        get() = apiService

    companion object {
        val instance = RetrofitManager()
    }
}