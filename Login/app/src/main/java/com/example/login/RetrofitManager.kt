package com.example.login

import retrofit2.Retrofit.*
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager private constructor() {
    private val apiService: APIService

    init {
        val retrofit = Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(APIService::class.java)
    }

    val api: APIService
        get() = apiService

    companion object {
        val instance = RetrofitManager()
    }
}