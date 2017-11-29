package com.daking.lottery.ui.fragment

import android.os.Bundle

import com.daking.lottery.base.BaseMVPFragment
import com.daking.lottery.ui.iview.IHomeView
import com.daking.lottery.ui.presenter.HomePresenter

class HomeFragment : BaseMVPFragment<HomePresenter>(), IHomeView {

    override fun getLayoutResId() = 0

    override fun initData(savedInstanceState: Bundle?) {

    }
}
