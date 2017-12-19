package com.daking.lottery.util

import android.content.Intent
import com.daking.lottery.api.NetSubscriber
import com.daking.lottery.app.App
import com.daking.lottery.base.BaseView
import com.daking.lottery.model.UserModel
import com.daking.lottery.repository.NetRepository
import com.daking.lottery.ui.activity.LoginActivity

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

    fun userSignOut(clear: Boolean) {
        AccountHelper.instance.saveUser(null)
        val activity = App.instance.getCurrentActivity()
        val intent = Intent(activity, LoginActivity::class.java)
        if (clear) {
            //清空Activity栈
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        activity.startActivity(intent)
    }

    fun getUserId() = getUser()?.id

    fun getSessionId() = getUser()?.sessionId

    fun hasAccess(): Boolean {
        val user = getUser()
        return when {
            user == null -> {
                toast("请先登录!")
                false
            }
            user.isVisitor -> {
                toast("请登录正式账号")
                false
            }
            else -> true
        }
    }

    /**
     *  刷新账户 调用获取个人信息接口
     */
    fun <T : BaseView> refreshAccount(view: T, success: (user: UserModel?) -> Unit) {
        NetRepository.instance.refreshAccount()
                .compose(RxUtils.io2Main())
                .compose(view.bindLifecycle())
                .subscribe(object : NetSubscriber<UserModel>() {
                    override fun onSuccess(code: Int, msg: String, data: List<UserModel>?) {
                        if (data != null && data.isNotEmpty()) {
                            val user = data[0]
                            currentUser?.realName = user.realName
                            currentUser?.email = user.email
                            currentUser?.balance = user.balance
                        }
                        success(currentUser)
                    }

                    override fun onFailure(code: Int, msg: String) {
                        log("code=$code, msg: $msg")
                    }
                })
    }
}