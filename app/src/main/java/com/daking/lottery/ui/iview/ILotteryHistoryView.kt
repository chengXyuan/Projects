package com.daking.lottery.ui.iview

import com.daking.lottery.base.BaseView
import com.daking.lottery.model.OpenModel

interface ILotteryHistoryView : BaseView {

    fun showHistory(data: List<OpenModel>?)

    fun showError(msg: String)

    fun onComplete()
}
