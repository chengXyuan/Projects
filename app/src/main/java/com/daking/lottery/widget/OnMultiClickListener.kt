package com.daking.lottery.widget

import android.view.View

abstract class OnMultiClickListener : View.OnClickListener {

    companion object {
        // 两次点击按钮之间的点击间隔不能少于1000毫秒
        private val MIN_CLICK_DELAY_TIME = 1000
        private var lastClickTime = 0L
    }

    override fun onClick(v: View) {
        val curTime = System.currentTimeMillis()
        if (curTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            //超过点击间隔后再将lastClickTime重置为当前点击时间
            lastClickTime = curTime
            noMultiClick(v)
        }
    }

    abstract fun noMultiClick(view: View)
}