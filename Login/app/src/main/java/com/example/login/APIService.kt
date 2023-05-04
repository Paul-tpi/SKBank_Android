package com.example.login

import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("/users")
    fun getData(): Call<List<UsersItem>>
}