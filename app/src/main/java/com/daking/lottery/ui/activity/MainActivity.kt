package com.daking.lottery.ui.activity

import android.os.Bundle
import com.daking.lottery.R
import com.daking.lottery.base.BaseMVPActivity
import com.daking.lottery.ui.iview.IMainView
import com.daking.lottery.ui.presenter.MainPresenter

class MainActivity : BaseMVPActivity<MainPresenter>(), IMainView {

    override fun getLayoutResId() = R.layout.activity_main

    override fun initData(savedInstanceState: Bundle?) {

    }
}
