package com.example.login

interface SharedPreferenceInterface {
    var userName: String?
    var pwd: String?
}

class SharedPreferenceConstant {
    companion object {
        const val USER_NAME = "USER_NAME"

        const val PWD = "PWD"
    }
}