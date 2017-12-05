package com.daking.lottery.ui.presenter

import com.daking.lottery.app.Constant
import com.daking.lottery.base.BasePresenter
import com.daking.lottery.ui.iview.IMoneyRecordView

class MoneyRecordPresenter : BasePresenter<IMoneyRecordView>() {

    fun getMoneyRecord(pageIndex: Int, pageSize: Int = Constant.PAGE_SIZE) {
        mNetRepository.getMoneyRecord(pageIndex, pageSize)
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