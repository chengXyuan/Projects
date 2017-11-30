package com.daking.lottery.api

import com.daking.lottery.model.Root
import com.daking.lottery.model.UserModel
import com.daking.lottery.util.AccountHelper
import io.reactivex.Flowable


class NetRepository private constructor() {

    private val mApiStore: ApiStore = ApiClient.instance.getApiStore()

    private object Holder {
        val Instance = NetRepository()
    }

    companion object {
        val instance = Holder.Instance
    }

    private fun getUserId() = AccountHelper.instance.getUserId()

    private fun getSessionId() = AccountHelper.instance.getSessionId()

    fun checkUpdate() = mApiStore.checkUpdate()

    fun visitorLogin() = mApiStore.visitorLogin()

    fun login(username: String, password: String): Flowable<Root<UserModel>> {
        val params = HashMap<String, Any?>()
        params["username"] = username
        params["password"] = password
        return mApiStore.login(params)
    }

    fun refreshAccount(): Flowable<Root<UserModel>> {
        val params = HashMap<String, Any?>()
        params["usersId"] = getUserId()
        params["sessionId"] = getSessionId()
        return mApiStore.login(params)
    }
}