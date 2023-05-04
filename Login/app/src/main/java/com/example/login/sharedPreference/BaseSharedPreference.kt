package com.example.login

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class BaseSharedPreference(
    context: Context,
    fileName: String
) {
    companion object {
        private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        private val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
    }

    private val preferences: Lazy<SharedPreferences> = lazy {
        EncryptedSharedPreferences.create(
            fileName,
            mainKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun getPreferences() = preferences

    class BooleanPreference(
        private val preferences: Lazy<SharedPreferences>,
        private val name: String,
        private val defaultValue: Boolean
    ) : ReadWriteProperty<Any, Boolean> {

        @WorkerThread
        override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
            return preferences.value.getBoolean(name, defaultValue)
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
            preferences.value.edit { putBoolean(name, value).apply() }
        }
    }

    class NullableBooleanPreference(
        private val preferences: Lazy<SharedPreferences>,
        private val name: String
    ) : ReadWriteProperty<Any, Boolean?> {

        @WorkerThread
        override fun getValue(thisRef: Any, property: KProperty<*>): Boolean? {
            val value = preferences.value.getBoolean(name, false)
            return if (value != null) value
            else
                null
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean?) {
            preferences.value.edit {
                if (value != null)
                    putBoolean(name, value).apply()
                else {
                    if (preferences.value.contains(name))
                        remove(name)
                }
            }
        }
    }

    class StringPreference(
        private val preferences: Lazy<SharedPreferences>,
        private val name: String,
        private val defaultValue: String
    ) : ReadWriteProperty<Any, String?> {

        @WorkerThread
        override fun getValue(thisRef: Any, property: KProperty<*>): String {
            return preferences.value.getString(name, defaultValue) ?: defaultValue
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
            preferences.value.edit { putString(name, value).apply() }
        }
    }

    class IntegerPreference(
        private val preferences: Lazy<SharedPreferences>,
        private val name: String,
        private val defaultValue: Int
    ) : ReadWriteProperty<Any, Int?> {

        @WorkerThread
        override fun getValue(thisRef: Any, property: KProperty<*>): Int {
            return preferences.value.getInt(name, defaultValue)
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Int?) {
            preferences.value.edit { putInt(name, value!!).apply() }
        }
    }
}