package com.daking.lottery.ui.iview

import com.daking.lottery.base.BaseView

interface ILoginView : BaseView {
    fun initAccount(isRemember: Boolean, username: String, password: String)

    fun loginSuccess()
}
