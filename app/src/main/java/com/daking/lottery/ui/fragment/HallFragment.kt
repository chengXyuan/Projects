package com.daking.lottery.ui.fragment

import android.os.Bundle
import com.daking.lottery.R

import com.daking.lottery.base.BaseMVPFragment
import com.daking.lottery.ui.iview.IHallView
import com.daking.lottery.ui.presenter.HallPresenter

class HallFragment : BaseMVPFragment<HallPresenter>(), IHallView {

    override fun getLayoutResId() = R.layout.fragment_hall

    override fun initData(savedInstanceState: Bundle?) {

    }
}
