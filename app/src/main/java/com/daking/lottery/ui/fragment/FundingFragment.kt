package com.daking.lottery.ui.fragment

import android.os.Bundle

import com.daking.lottery.base.BaseMVPFragment
import com.daking.lottery.ui.iview.IFundingView
import com.daking.lottery.ui.presenter.FundingPresenter

class FundingFragment : BaseMVPFragment<FundingPresenter>(), IFundingView {

    override fun getLayoutResId() = 0

    override fun initData(savedInstanceState: Bundle?) {

    }
}
