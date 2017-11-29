package com.daking.lottery.ui.activity

import android.os.Bundle

import com.daking.lottery.base.BaseMVPActivity
import com.daking.lottery.ui.iview.IRegisterView
import com.daking.lottery.ui.presenter.RegisterPresenter

class RegisterActivity : BaseMVPActivity<RegisterPresenter>(), IRegisterView {

    override fun getLayoutResId() = 0

    override fun initData(savedInstanceState: Bundle?) {

    }
}