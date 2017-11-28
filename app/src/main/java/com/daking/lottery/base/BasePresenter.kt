package com.daking.lottery.base

import org.greenrobot.eventbus.EventBus


open class BasePresenter<V : BaseView> {

    open lateinit var mView: V

    fun setView(view: V) {
        mView = view
        onAttached()
    }

    open fun onAttached() {
        if (useEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    open fun onDetached() {
        if (useEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    open fun useEventBus() = false
}