package com.daking.lottery.util

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.daking.lottery.app.App


class SPUtils private constructor() {

    private val mPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.instance)

    private object Holder {
        val INSTANCE = SPUtils()
    }

    companion object {
        val instance = Holder.INSTANCE
    }

    fun putString(key: String, value: String?) = mPreferences.edit().putString(key, value).apply()

    fun getString(key: String, defValue: String?): String? = mPreferences.getString(key, defValue)
}