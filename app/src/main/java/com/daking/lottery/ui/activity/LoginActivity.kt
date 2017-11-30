package com.daking.lottery.ui.activity

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.daking.lottery.R
import com.daking.lottery.base.BaseMVPActivity
import com.daking.lottery.ui.iview.ILoginView
import com.daking.lottery.ui.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : BaseMVPActivity<LoginPresenter>(), ILoginView {

    override fun getLayoutResId() = R.layout.activity_login

    override fun initData(savedInstanceState: Bundle?) {
        iv_pwd_visibility.setOnClickListener { showPassword(iv_pwd_visibility.isSelected) }
        tv_try_play.setOnClickListener { mPresenter.loginDemo() }
        btn_login.setOnClickListener {
            val isRemember = cb_remember.isChecked
            val username = et_username.text.toString().trim()
            val password = et_password.text.toString().trim()
            mPresenter.requestLogin(isRemember, username, password)
        }
        btn_register.setOnClickListener {
            startActivity<RegisterActivity>()
            finish()
        }
    }

    override fun initAccount(isRemember: Boolean, username: String, password: String) {
        cb_remember.isChecked = isRemember
        if (isRemember) {
            et_username.setText(username)
            et_password.setText(password)
        }
    }

    private fun showPassword(selected: Boolean) {
        iv_pwd_visibility.isSelected = !selected
        if (iv_pwd_visibility.isSelected) {
            et_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            et_password.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }

    override fun loginSuccess() {
        startActivity<MainActivity>()
        finish()
    }
}