package com.daking.lottery.ui.presenter

import com.daking.lottery.app.Constant
import com.daking.lottery.base.BasePresenter
import com.daking.lottery.ui.iview.IRecordSummaryView

class RecordSummaryPresenter : BasePresenter<IRecordSummaryView>() {

    fun getRecordSummary(pageIndex: Int, pageSize: Int = Constant.PAGE_SIZE) {
        mNetRepository.getRecordSummary(pageIndex, pageSize)
                .dealArray({ _, _, data ->
                    mView.showRecord(data)
                }, { _, msg ->
                    mView.showError(msg)
                    mView.onComplete()
                }, {
                    mView.onComplete()
                })
    }
}