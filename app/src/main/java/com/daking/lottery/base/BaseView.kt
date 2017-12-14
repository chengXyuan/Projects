package com.daking.lottery.base

import com.trello.rxlifecycle2.LifecycleTransformer

interface BaseView {

    fun showLoadingDialog(msg: String?)

    fun dismissLoadingDialog()

    fun <T> bindLifecycle(): LifecycleTransformer<T>
}