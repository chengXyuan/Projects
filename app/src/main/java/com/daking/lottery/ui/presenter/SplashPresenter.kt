package com.daking.lottery.ui.presenter

import com.daking.lottery.base.BasePresenter
import com.daking.lottery.ui.iview.ISplashView
import com.daking.lottery.util.AccountHelper
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit

class SplashPresenter : BasePresenter<ISplashView>() {

    override fun onAttached() {
        super.onAttached()
        jumpDelay(3)
        checkUpdate()
    }

    private fun jumpDelay(seconds: Long) {
        Flowable.timer(seconds, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Consumer<Long> {
                    override fun accept(t: Long?) {
                        toNextPage()
                    }
                })
    }

    private fun toNextPage() {
        val user = AccountHelper.instance.getUser()
        if (user == null) {
            mView.toLoginPage()
        } else {
            mView.toMainPage()
        }
    }

    private fun checkUpdate() {
        mNetRepository.checkUpdate()
                .dealObj({ code, msg, model ->

                })
    }
}