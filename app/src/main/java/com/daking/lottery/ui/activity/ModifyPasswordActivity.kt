package com.daking.lottery.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.daking.lottery.R

import com.daking.lottery.base.BaseMVPActivity
import com.daking.lottery.ui.iview.IModifyPasswordView
import com.daking.lottery.ui.presenter.ModifyPasswordPresenter
import com.daking.lottery.widget.TitleBar

class ModifyPasswordActivity : BaseMVPActivity<ModifyPasswordPresenter>(), IModifyPasswordView {

    private val type by lazy { intent.getIntExtra(MODIFY_TYPE, 0) }

    companion object {
        val MODIFY_TYPE = "modify_type"
    }

    override fun getLayoutResId(): Int {
        return if (type == 0) {//登录密码
            R.layout.activity_modify_password
        } else {//支付密码
            R.layout.activity_modify_password2
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        val titleBar = findViewById<TitleBar>(R.id.titleBar)
        val etOldPassword = findViewById<EditText>(R.id.etOldPassword)
        val etNewPassword = findViewById<EditText>(R.id.etNewPassword)
        val etEnsurePassword = findViewById<EditText>(R.id.etEnsurePassword)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)
        titleBar.setOnLeftTextClickListener { onBackPressed() }
        btnConfirm.setOnClickListener {
            val oldPassword = etOldPassword.text.toString().trim()
            val newPassword = etNewPassword.text.toString().trim()
            val ensurePassword = etEnsurePassword.text.toString().trim()
            mPresenter.commitUpdate(type, oldPassword, newPassword, ensurePassword)
        }
    }
}