package com.daking.lottery.ui.iview

import com.daking.lottery.base.BaseView
import com.daking.lottery.model.LotteryModel

interface IHallView : BaseView {

    fun showLotteries(data: List<LotteryModel>?)

    fun showError(msg: String)

    fun onComplete()

    fun notifyItemChanged(index: Int, timeCD: Long)
}
