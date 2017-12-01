package com.daking.lottery.util

import com.daking.lottery.repository.NetRepository
import com.daking.lottery.model.UserModel
import io.reactivex.Flowable

class AccountHelper {

    private object Holder {
        val INSTANCE = AccountHelper()
    }

    companion object {
        val instance = Holder.INSTANCE
        private val CURRENT_USER = "current_user"
        private var currentUser: UserModel? = null
    }

    fun saveUser(user: UserModel?) {
        currentUser = user
        SPUtils.instance.putString(CURRENT_USER, JsonUtil.toJson(user))
    }

    fun getUser(): UserModel? {
        if (currentUser == null) {
            val jsonStr = SPUtils.instance.getString(CURRENT_USER, null)
            currentUser = JsonUtil.fromJson(jsonStr)
        }
        return currentUser
    }

    fun getUserId() = getUser()?.id

    fun getSessionId() = getUser()?.sessionId

    /**
     *  刷新账户 调用获取个人信息接口
     */
    fun refreshAccount() =
            NetRepository.instance.refreshAccount()
                    .flatMap { (_, models) ->
                        val user = models!![0]
                        currentUser?.realName = user.realName
                        currentUser?.email = user.email
                        currentUser?.balance = user.balance
                        Flowable.just(currentUser)
                    }
}