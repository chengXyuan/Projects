package com.daking.lottery.ui.iview

import com.daking.lottery.base.BaseView
import com.daking.lottery.model.UserModel

interface IFundingView : BaseView {

    fun showAccount(user: UserModel)
}
