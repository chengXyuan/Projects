package com.daking.lottery.ui.activity

import android.os.Bundle
import com.daking.lottery.R
import com.daking.lottery.base.BaseActivity
import kotlinx.android.synthetic.main.activity_password.*
import org.jetbrains.anko.startActivity

class PasswordActivity : BaseActivity() {

    override fun getLayoutResId() = R.layout.activity_password

    override fun initData(savedInstanceState: Bundle?) {
        titleBar.setOnLeftTextClickListener { onBackPressed() }
        tvModifyLoginPassword.setOnClickListener {
            startActivity<ModifyPasswordActivity>(Pair(ModifyPasswordActivity.MODIFY_TYPE, 0))
        }
        tvModifyPayPassword.setOnClickListener {
            startActivity<ModifyPasswordActivity>(Pair(ModifyPasswordActivity.MODIFY_TYPE, 1))
        }
    }
}
