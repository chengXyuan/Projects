package com.daking.lottery.ui.presenter

import com.daking.lottery.base.BasePresenter
import com.daking.lottery.ui.iview.IPersonalInfoView
import com.daking.lottery.util.AccountHelper

class PersonalInfoPresenter : BasePresenter<IPersonalInfoView>() {

    override fun onAttached() {
        super.onAttached()
        initUser()
    }


    private fun initUser() {
        val user = AccountHelper.instance.getUser()
        mView.refreshUser(user)
        refreshUser()
    }

    private fun refreshUser() {
        AccountHelper.instance.refreshAccount(mView, { user ->
            mView.refreshUser(user)
        })
    }
}