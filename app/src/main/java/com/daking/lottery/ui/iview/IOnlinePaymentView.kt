package com.daking.lottery.ui.iview

import com.daking.lottery.base.BaseView

interface IOnlinePaymentView : BaseView {

    fun getOnlinePayUrl(url: String)
}
