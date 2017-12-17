package com.daking.lottery.base

import android.app.Activity
import com.trello.rxlifecycle2.LifecycleTransformer

interface BaseView {

    fun showLoadingDialog(msg: String?)

    fun dismissLoadingDialog()

    fun getActivity(): Activity

    fun <T> bindLifecycle(): LifecycleTransformer<T>
}