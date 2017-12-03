package com.daking.lottery.ui.fragment

import android.os.Bundle
import com.daking.lottery.R

import com.daking.lottery.base.BaseMVPFragment
import com.daking.lottery.ui.iview.IWithdrawView
import com.daking.lottery.ui.presenter.WithdrawPresenter

class WithdrawFragment : BaseMVPFragment<WithdrawPresenter>(), IWithdrawView {

    override fun getLayoutResId() = R.layout.fragment_withdraw

    override fun initData(savedInstanceState: Bundle?) {

    }
}
