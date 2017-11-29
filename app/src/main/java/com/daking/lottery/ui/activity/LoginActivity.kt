package com.daking.lottery.ui.activity

import android.os.Bundle

import com.daking.lottery.base.BaseMVPActivity
import com.daking.lottery.ui.iview.ILoginView
import com.daking.lottery.ui.presenter.LoginPresenter

class LoginActivity : BaseMVPActivity<LoginPresenter>(), ILoginView {

    override fun getLayoutResId() = 0

    override fun initData(savedInstanceState: Bundle?) {

    }
}