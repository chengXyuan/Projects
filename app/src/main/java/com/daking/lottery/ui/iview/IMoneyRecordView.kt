package com.daking.lottery.ui.iview

import com.daking.lottery.base.BaseView
import com.daking.lottery.model.FundingModel

interface IMoneyRecordView : BaseView {

    fun showRecord(data: List<FundingModel>?)

    fun showError(msg: String)

    fun onComplete()
}
