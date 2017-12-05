package com.daking.lottery.repository

import com.daking.lottery.api.ApiClient
import com.daking.lottery.api.ApiStore
import com.daking.lottery.model.*
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

    /**
     * 检查更新
     */
    fun checkUpdate() = mApiStore.checkUpdate()

    /**
     * 游客登录
     */
    fun visitorLogin() = mApiStore.visitorLogin()

    /**
     * 登录
     */
    fun login(username: String, password: String): Flowable<Root<UserModel>> {
        val params = HashMap<String, Any?>()
        params["username"] = username
        params["password"] = password
        return mApiStore.login(params)
    }

    /**
     * 刷新账户(个人信息)
     */
    fun refreshAccount(): Flowable<Root<UserModel>> {
        val params = HashMap<String, Any?>()
        params["usersId"] = getUserId()
        params["sessionId"] = getSessionId()
        return mApiStore.refreshAccount(params)
    }

    /**
     * 获取轮播图和客服链接
     */
    fun getBanner() = mApiStore.getBanner()

    /**
     * 获取支持的支付方式
     */
    fun requestPayInWays(): Flowable<Root<PayWaysModel>> {
        val params = HashMap<String, Any?>()
        params["usersId"] = getUserId()
        params["sessionId"] = getSessionId()
        return mApiStore.getPayWays(params)
    }

    fun getMoneyRecord(pageIndex: Int, pageSize: Int)
            : Flowable<Root<FundingModel>> {
        val params = HashMap<String, Any?>()
        params["usersId"] = getUserId()
        params["sessionId"] = getSessionId()
        params["pageSize"] = pageSize
        params["pageNo"] = pageIndex
        return mApiStore.getMoneyRecord(params)
    }

    fun getLotteryHistory(gameCode: Int?, pageIndex: Int, pageSize: Int)
            : Flowable<Root<LotteryModel>> {
        val params = HashMap<String, Any?>()
        params["usersId"] = getUserId()
        params["sessionId"] = getSessionId()
        params["lotterygamesId"] = gameCode
        params["pageSize"] = pageSize
        params["pageNo"] = pageIndex
        return mApiStore.getLotteryHistory(params)
    }
}