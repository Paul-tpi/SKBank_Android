package com.example.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.regex.Pattern

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var homeRepository: MainRepository? = null
    var postModelListLiveData: LiveData<List<UsersItem>>? = null
    var loginResultLiveData: LiveData<Boolean>? = null

    init {
        homeRepository = MainRepository()
        postModelListLiveData = MutableLiveData()
        loginResultLiveData = MutableLiveData()
    }

    fun getUsers() {
        postModelListLiveData = homeRepository?.getUsersItem()
    }

    fun loginCheck(account: String, pwd: String) {
        val loginResult = MutableLiveData<Boolean>()
        val enRule = ".*[a-zA-z].*"
        val numberRule = ".*[0-9].*"

        loginResult.value = (account != pwd &&
                pwd.length >= 6 &&
                Pattern.matches(enRule, pwd) &&
                Pattern.matches(numberRule, pwd))

        loginResultLiveData = loginResult
    }
}