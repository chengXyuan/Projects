package com.daking.lottery.ui.iview

import com.daking.lottery.base.BaseView
import com.daking.lottery.model.RecordDetail

interface IRecordDetailView : BaseView{

    fun showRecord(data: List<RecordDetail>?)

    fun showError(msg: String)

    fun onComplete()
}
