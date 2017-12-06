package com.daking.lottery.ui.fragment

import android.os.Bundle
import com.daking.lottery.R

import com.daking.lottery.base.BaseMVPFragment
import com.daking.lottery.ui.iview.ILotteryInfoView
import com.daking.lottery.ui.presenter.LotteryInfoPresenter

class LotteryInfoFragment : BaseMVPFragment<LotteryInfoPresenter>(), ILotteryInfoView {

    override fun getLayoutResId() = R.layout.fragment_lottery_info

    override fun initData(savedInstanceState: Bundle?) {

    }
}
