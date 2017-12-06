package com.daking.lottery.ui.presenter

import com.daking.lottery.base.BasePresenter
import com.daking.lottery.model.OpenModel
import com.daking.lottery.ui.iview.IHallView
import com.daking.lottery.util.RxUtils
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class HallPresenter : BasePresenter<IHallView>() {

    private var mPollingDis: Disposable? = null
    private var mCountDownDis: Disposable? = null

    /**
     * 开始轮询服务器
     */
    fun startPollingServer() {
        if (mPollingDis != null && !mPollingDis!!.isDisposed) {
            mPollingDis!!.dispose()
        }
        mPollingDis = Flowable.interval(0, 15, TimeUnit.SECONDS)
                .compose(RxUtils.io2Main())
                .compose(mView.bindLifecycle())
                .subscribe { getLotteryHistory() }
    }

    /**
     * 停止轮询服务器
     */
    fun stopPollingServer() {
        if (mPollingDis != null && !mPollingDis!!.isDisposed) {
            mPollingDis!!.dispose()
        }
    }

    /**
     * 获取开奖信息
     */
    private fun getLotteryHistory() {
        mNetRepository.getLotteryHistory(null, 1)
                .dealArray({ _, _, data ->
                    mView.showLotteries(data)
                    if (data != null) startCountDown(data)
                }, { _, msg ->
                    mView.showError(msg)
                    mView.onComplete()
                }, {
                    mView.onComplete()
                })
    }

    private fun startCountDown(data: List<OpenModel>) {
        if (mCountDownDis != null && !mCountDownDis!!.isDisposed) {
            mCountDownDis!!.dispose()
        }
        mCountDownDis = Flowable.interval(0, 1, TimeUnit.SECONDS).flatMap { aLong ->
            Flowable.just(data.map {
                if (it.isClose == 0 && it.openTime != null && it.sysTime != null) {
                    it.openTime - it.sysTime - aLong
                } else -1
            })
        }
                .compose(RxUtils.io2Main())
                .compose(mView.bindLifecycle())
                .subscribe({ longs ->
                    //是否马上刷新
                    longs.forEachIndexed { index, l ->
                        mView.notifyItemChanged(index, l)
                    }

                    if (longs.any { it == 0L }) {
                        getLotteryHistory()
                    }
                })
    }

    override fun onDetached() {
        super.onDetached()
        stopPollingServer()
    }
}