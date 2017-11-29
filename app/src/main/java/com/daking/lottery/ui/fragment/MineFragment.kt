package com.daking.lottery.ui.fragment

import android.os.Bundle

import com.daking.lottery.base.BaseMVPFragment
import com.daking.lottery.ui.iview.IMineView
import com.daking.lottery.ui.presenter.MinePresenter

class MineFragment : BaseMVPFragment<MinePresenter>(), IMineView {

    override fun getLayoutResId() = 0

    override fun initData(savedInstanceState: Bundle?) {

    }
}
