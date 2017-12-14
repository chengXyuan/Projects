package com.daking.lottery.ui.activity

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.daking.lottery.R
import com.daking.lottery.base.BaseMVPActivity
import com.daking.lottery.ui.iview.IRegisterView
import com.daking.lottery.ui.presenter.RegisterPresenter
import com.daking.lottery.widget.OnMultiClickListener
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.startActivity

class RegisterActivity : BaseMVPActivity<RegisterPresenter>(), IRegisterView {

    override fun getLayoutResId() = R.layout.activity_register

    override fun initData(savedInstanceState: Bundle?) {
        titleBar.setOnLeftTextClickListener {
            //返回 登录
            startActivity<LoginActivity>()
            finish()
        }
        tvToLogin.setOnClickListener {
            //去登录
            startActivity<LoginActivity>()
            finish()
        }
        ivPwdVisibility.setOnClickListener {
            //隐藏显示密码
            showPassword(ivPwdVisibility, etPassword)
        }
        ivEnsurePwdVisibility.setOnClickListener {
            //隐藏显示 确认密码
            showPassword(ivEnsurePwdVisibility, etEnsurePassword)
        }
        btnRegister.setOnClickListener(object : OnMultiClickListener() {
            override fun noMultiClick(view: View) {
                //点击注册
                val username = etUsername.text.trim().toString()
                val password = etPassword.text.trim().toString()
                val ensurePassword = etEnsurePassword.text.trim().toString()
                val realName = etRealName.text.trim().toString()
                val payPassword = passwordView.getPassword()
                mPresenter.commitRegister(username, password, ensurePassword, realName, payPassword)
            }
        })
    }


    private fun showPassword(ivVisible: ImageView, etPassword: EditText) {
        ivVisible.isSelected = !ivVisible.isSelected
        if (ivVisible.isSelected) {
            etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }

    override fun registerAndLoginSuccess() {
        //注册并登录成功
        startActivity<MainActivity>()
        finish()
    }
}