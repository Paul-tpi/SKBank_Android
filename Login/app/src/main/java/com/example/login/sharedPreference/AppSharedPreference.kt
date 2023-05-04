package com.example.login

import android.content.Context

class AppSharedPreference(context: Context) :
    BaseSharedPreference(context, PREFS_NAME),
    SharedPreferenceInterface {

    companion object {
        private const val PREFS_NAME = "SK_BANK_LOGIN"

        @Volatile
        private var instance: AppSharedPreference? = null

        fun create(context: Context) {
            synchronized(this) {
                if (instance == null) {
                    instance = AppSharedPreference(context)
                }
            }
        }

        fun getInstance() = instance!!
    }

    override var userName: String? by StringPreference(
        getPreferences(),
        SharedPreferenceConstant.USER_NAME,
        ""
    )

    override var pwd: String? by StringPreference(
        getPreferences(),
        SharedPreferenceConstant.PWD,
        ""
    )
}