package com.daking.lottery.util

import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object RxUtils {

    fun <T> io2Main(subscribeThread: Scheduler = Schedulers.io(),
                    unSubscribeThread: Scheduler = Schedulers.io(),
                    observeThread: Scheduler = AndroidSchedulers.mainThread())
            : FlowableTransformer<T, T> {
        return FlowableTransformer { upstream ->
            upstream.subscribeOn(subscribeThread)
                    .unsubscribeOn(unSubscribeThread)
                    .observeOn(observeThread)
        }
    }

    fun <T> async(subscribeThread: Scheduler = Schedulers.io(),
                  unSubscribeThread: Scheduler = Schedulers.io(),
                  observeThread: Scheduler = AndroidSchedulers.mainThread())
            : ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(subscribeThread)
                    .unsubscribeOn(unSubscribeThread)
                    .observeOn(observeThread)
        }
    }
}