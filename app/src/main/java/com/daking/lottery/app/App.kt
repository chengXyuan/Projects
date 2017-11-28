package com.daking.lottery.app

import android.app.Application
import com.daking.lottery.util.Utils
import com.daking.lottery.util.delegate.NotNullSingleValueVar


class App : Application() {

    companion object {
        var instance: App by NotNullSingleValueVar.DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        //只在主进程里进行初始化,避免重复初始化
        if (Utils.isMainProcess(this)) {

        }
    }
}