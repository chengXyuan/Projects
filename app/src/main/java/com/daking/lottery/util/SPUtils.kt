package com.daking.lottery.util

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.daking.lottery.app.App


class SPUtils {

    private val mPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.instance)

    private object Holder {
        val INSTANCE = SPUtils()
    }

    companion object {
        val instance = Holder.INSTANCE
        val CURRENT_USER = "current_user"
    }

    fun putString(key: String, value: String?) = mPreferences.edit().putString(key, value).apply()

    fun getString(key: String, defValue: String?): String? = mPreferences.getString(key, defValue)

    fun putBoolean(key: String, value: Boolean) = mPreferences.edit().putBoolean(key, value).apply()

    fun getBoolean(key: String, defValue: Boolean) = mPreferences.getBoolean(key, defValue)
}