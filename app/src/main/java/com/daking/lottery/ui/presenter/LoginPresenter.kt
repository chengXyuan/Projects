package com.daking.lottery.ui.presenter

import com.daking.lottery.R
import com.daking.lottery.app.Constant
import com.daking.lottery.base.BasePresenter
import com.daking.lottery.ui.iview.ILoginView
import com.daking.lottery.util.*

class LoginPresenter : BasePresenter<ILoginView>() {

    override fun onAttached() {
        super.onAttached()
        val isRemember = SPUtils.instance.getBoolean(Constant.REMEMBER_ACCOUNT, false)
        var username = AccountHelper.instance.getUser()?.username ?: ""
        if (username.isEmpty()) {
            username = SPUtils.instance.getString(Constant.USERNAME, "") ?: ""
        }
        val password = SPUtils.instance.getString(Constant.PASSWORD, "") ?: ""
        mView.initAccount(isRemember, username, password)
    }

    fun loginDemo() {
        mNetRepository.visitorLogin()
                .dealObj({ _, msg, model ->
                    //登录成功
                    toast(msg)
                    model!!.isVisitor = true
                    AccountHelper.instance.saveUser(model)
                    mView.loginSuccess()
                }, { _, msg ->
                    //登录失败
                    toast(msg)
                    SPUtils.instance.putString(Constant.PASSWORD, "")
                }, showLoading = true, loadingMsg = "正在登陆...")
    }

    fun requestLogin(isRemember: Boolean, username: String, password: String) {
        when {
            !username.isUserName() -> toast(Utils.getString(R.string.input_correct_user_name))
            !password.isPassword() -> toast(Utils.getString(R.string.input_correct_password))
            else -> {
                mNetRepository.login(username, password)
                        .dealObj({ _, msg, model ->
                            //登录成功
                            toast(msg)
                            model?.let {
                                model.isVisitor = false
                                AccountHelper.instance.saveUser(model)
                            }
                            dealRememberAccount(isRemember, username, password)
                            mView.loginSuccess()
                        }, { _, msg ->
                            //登录失败
                            toast(msg)
                            SPUtils.instance.putString(Constant.PASSWORD, "")
                        }, showLoading = true, loadingMsg = "正在登陆...")
            }
        }
    }

    private fun dealRememberAccount(isRemember: Boolean, username: String, password: String) {
        SPUtils.instance.putString(Constant.USERNAME, username)
        SPUtils.instance.putBoolean(Constant.REMEMBER_ACCOUNT, isRemember)
        if (isRemember) {
            SPUtils.instance.putString(Constant.PASSWORD, password)
        } else {
            SPUtils.instance.putString(Constant.PASSWORD, "")
        }
    }
}