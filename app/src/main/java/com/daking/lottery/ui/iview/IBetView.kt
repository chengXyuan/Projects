package com.daking.lottery.ui.iview

import com.daking.lottery.base.BaseView
import com.daking.lottery.model.LastModel

interface IBetView : BaseView {

    fun showLastRound(lastModel: LastModel)

    fun setNextRoundNum(round: String)

    fun refreshCloseStatus(isClosed: Boolean)

    fun showEndTime(endTime: String)

    fun showCloseTime(minute: String, second: String)

    fun showCloseTime(hour: String, minute: String, second: String)
}
