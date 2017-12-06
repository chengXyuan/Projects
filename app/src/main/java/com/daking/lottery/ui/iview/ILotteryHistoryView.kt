package com.daking.lottery.ui.iview

import com.daking.lottery.base.BaseView
import com.daking.lottery.model.LotteryModel

interface ILotteryHistoryView : BaseView {

    fun showHistory(data: List<LotteryModel>?)

    fun showError(msg: String)

    fun onComplete()
}
