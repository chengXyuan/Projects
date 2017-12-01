package com.daking.lottery.api

import org.junit.Test

class ApiStoreTest {

    private val mService = ApiClient.instance.getApiStore()

    @Test
    fun testRegister() {
        val params = HashMap<String, Any?>()
        params["username"] = "hah233"
        params["password"] = "123456789"
        params["realName"] = "艾琳娜"
        params["payPassword"] = "1111"

        val call = mService.register(params)
        call.subscribe()
    }
}
