package com.daking.lottery.util

import com.google.gson.Gson

object JsonUtil {

    /**
     * json字符串转对象
     */
    inline fun <reified T : Any> fromJson(json: String?): T? = Gson().fromJson(json, T::class.java)

    /**
     * 对象转json
     */
    fun toJson(any: Any?): String = Gson().toJson(any)
}