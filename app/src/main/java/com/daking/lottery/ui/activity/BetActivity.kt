package com.daking.lottery.ui.activity

import android.os.Bundle
import com.daking.lottery.R

import com.daking.lottery.base.BaseMVPActivity
import com.daking.lottery.ui.iview.IBetView
import com.daking.lottery.ui.presenter.BetPresenter

class BetActivity : BaseMVPActivity<BetPresenter>(), IBetView {

    companion object {
        val GAME_CODE = "game_code"
    }

    override fun getLayoutResId() = R.layout.activity_bet

    override fun initData(savedInstanceState: Bundle?) {

    }
}