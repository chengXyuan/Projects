package com.daking.lottery.ui.presenter

import com.daking.lottery.app.App
import com.daking.lottery.base.BasePresenter
import com.daking.lottery.model.VersionModel
import com.daking.lottery.ui.iview.ISplashView
import com.daking.lottery.util.AccountHelper
import com.daking.lottery.util.Utils
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class SplashPresenter : BasePresenter<ISplashView>() {

    private lateinit var timeDisposable: Disposable

    override fun onAttached() {
        super.onAttached()
        jumpDelay(3)
        checkUpdate()
    }

    private fun jumpDelay(seconds: Long) {
        timeDisposable = Flowable.timer(seconds, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { toNextPage() }
    }

    fun toNextPage() {
        val user = AccountHelper.instance.getUser()
        if (user == null) {
            mView.toLoginPage()
        } else {
            mView.toMainPage()
        }
    }

    private fun checkUpdate() {
        mNetRepository.checkUpdate()
                .dealObj({ _, _, model ->
                    model?.let {
                        dealUpdate(model)
                    }
                })
    }

    private fun dealUpdate(model: VersionModel) {
        //已安装版本
        val currVersion = Utils.getVersionName(App.instance)
        //线上版本
        val lastVersion = model.versionName
        if (lastVersion.toFloat() > currVersion.toFloat() && model.versionUrl.isNotEmpty()) {
            //如果线上版本高于已安装的版本.
            //先取消跳转
            timeDisposable.dispose()
            //显示升级弹窗
            mView.showVersionDialog(model)
        }
    }
}