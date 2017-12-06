package com.daking.lottery.ui.presenter

import com.daking.lottery.base.BasePresenter
import com.daking.lottery.ui.iview.ILotteryHistoryView

class LotteryHistoryPresenter : BasePresenter<ILotteryHistoryView>() {

    fun getLotteryHistory(gameCode: Int, pageIndex: Int) {
        mNetRepository.getLotteryHistory(gameCode, pageIndex)
                .dealArray({ _, _, data ->
                    mView.showHistory(data)
                }, { _, msg ->
                    mView.showError(msg)
                    mView.onComplete()
                }, {
                    mView.onComplete()
                })
    }
}