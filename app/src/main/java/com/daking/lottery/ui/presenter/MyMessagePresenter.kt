package com.daking.lottery.ui.presenter

import com.daking.lottery.app.Constant
import com.daking.lottery.base.BasePresenter
import com.daking.lottery.ui.iview.IMyMessageView
import com.daking.lottery.util.log

class MyMessagePresenter : BasePresenter<IMyMessageView>() {

    fun getAllMessage(pageIndex: Int) {
        mNetRepository.getAllMessage(pageIndex, Constant.PAGE_SIZE)
                .dealArray({ code, msg, data ->
                    log("code=$code, msg: $msg")
                    mView.showMessages(data)
                }, { code, msg ->
                    log("code=$code, msg: $msg")
                    mView.showError(msg)
                    mView.onComplete()
                }, {
                    mView.onComplete()
                })
    }
}