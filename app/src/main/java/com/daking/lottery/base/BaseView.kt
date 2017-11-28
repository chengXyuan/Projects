package com.daking.lottery.base

import com.trello.rxlifecycle2.LifecycleTransformer

interface BaseView {

    fun <T> bindLifecycle(): LifecycleTransformer<T>
}