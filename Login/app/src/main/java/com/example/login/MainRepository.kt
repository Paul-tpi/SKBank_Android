package com.example.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository {
    private var apiService: APIService? = null

    init {
        apiService = RetrofitManager.instance.api
    }

    fun getUsersItem(): LiveData<List<UsersItem>> {
        val data = MutableLiveData<List<UsersItem>>()

        apiService?.getData()?.enqueue(object : Callback<List<UsersItem>> {

            override fun onFailure(call: Call<List<UsersItem>>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<List<UsersItem>>,
                response: Response<List<UsersItem>>
            ) {
                val res = response.body()
                if (response.code() == 200 && res != null) {
                    data.value = res
                } else {
                    data.value = null
                }
            }
        })
        return data
    }
}