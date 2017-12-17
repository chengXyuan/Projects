package com.daking.lottery.ui.iview

import com.daking.lottery.base.BaseView
import com.daking.lottery.model.RecordSummary

interface IRecordSummaryView : BaseView {
    fun showRecord(data: List<RecordSummary>?)

    fun showError(msg: String)

    fun onComplete()
}
