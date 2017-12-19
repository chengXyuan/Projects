package com.daking.lottery.ui.presenter

import com.daking.lottery.base.BasePresenter
import com.daking.lottery.event.OutOfSignEvent
import com.daking.lottery.ui.iview.IFundingView
import com.daking.lottery.util.AccountHelper
import org.greenrobot.eventbus.Subscribe

class FundingPresenter : BasePresenter<IFundingView>() {

    override fun onAttached() {
        super.onAttached()

        val user = AccountHelper.instance.getUser()
        if (user != null) {
            mView.showAccount(user)
        }
        //刷新一下
        refreshAccountInfo()
    }

    fun refreshAccountInfo() {
        AccountHelper.instance.refreshAccount(mView, { user ->
            if (user != null) {
                mView.showAccount(user)
            }
        })
    }

    override fun useEventBus() = true

    @Subscribe
    fun onEvent(event: OutOfSignEvent) {
        refreshAccountInfo()
    }
}