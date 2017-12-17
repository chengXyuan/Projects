package com.daking.lottery.ui.presenter

import com.daking.lottery.base.BasePresenter
import com.daking.lottery.ui.iview.IAboutUsView
import com.daking.lottery.util.log

class AboutUsPresenter : BasePresenter<IAboutUsView>() {

    override fun onAttached() {
        super.onAttached()
        checkUpdate()
    }

    private fun checkUpdate() {
        mNetRepository.checkUpdate()
                .dealObj({ code, msg, model ->
                    log("code=$code, msg: $msg")
                    model?.let { mView.getVersionSuccess(model) }
                })
    }
}