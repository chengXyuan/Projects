package com.daking.lottery.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.daking.lottery.util.Utils
import com.daking.lottery.util.delegate.NotNullSingleValueVar
import com.daking.lottery.util.log
import java.util.*


class App : Application() {

    private lateinit var mStack: Stack<Activity>

    companion object {
        var instance: App by NotNullSingleValueVar.DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        mStack = Stack()
        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
                mStack.remove(activity)
            }

            override fun onActivitySaveInstanceState(activity: Activity?, bundle: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
                mStack.add(activity)
            }
        })
        //只在主进程里进行初始化,避免重复初始化
        if (Utils.isMainProcess(this)) {

        }
    }

    /**
     * 退出应用
     */
    fun exit() {
        mStack.indices.map { mStack[it] }.forEach { it.finish() }
        log("App exit !")
        android.os.Process.killProcess(android.os.Process.myPid())
    }
}