package com.daking.lottery.ui.presenter

import com.daking.lottery.R
import com.daking.lottery.app.Constant
import com.daking.lottery.base.BasePresenter
import com.daking.lottery.ui.iview.IRegisterView
import com.daking.lottery.util.*

class RegisterPresenter : BasePresenter<IRegisterView>() {

    fun commitRegister(username: String, password: String, ensurePassword: String, realName: String, payPassword: String) {
        when {
            !username.isUserName() -> {
                toast(Utils.getString(R.string.input_correct_user_name))
            }
            !password.isPassword() -> {
                toast(Utils.getString(R.string.input_correct_password))
            }
            ensurePassword != password -> {
                toast("两次输入的密码不一致!")
            }
            !realName.isRealName() -> {
                toast("真实姓名只能是汉字")
            }
            payPassword.length != 4 -> {
                toast("请输入4位支付密码")
            }
            else -> {
                doRegister(username, password, realName, payPassword)
            }
        }
    }

    private fun doRegister(username: String, password: String, realName: String, payPassword: String) {
        mNetRepository.register(username, password, realName, payPassword)
                .dealObj({ code, msg, _ ->
                    //注册成功
                    log("code=$code, msg: $msg")
                    requestLogin(true, username, password)
                }, { code: Int, msg: String ->
                    log("code=$code, msg: $msg")
                    toast(msg)
                })
    }

    private fun requestLogin(isRemember: Boolean, username: String, password: String) {
        mNetRepository.login(username, password)
                .dealObj({ _, msg, model ->
                    //登录成功
                    toast(msg)
                    model?.let {
                        model.isVisitor = false
                        AccountHelper.instance.saveUser(model)
                    }
                    dealRememberAccount(isRemember, username, password)
                    mView.registerAndLoginSuccess()
                }, { _, msg ->
                    //登录失败
                    toast(msg)
                    SPUtils.instance.putString(Constant.PASSWORD, "")
                })
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