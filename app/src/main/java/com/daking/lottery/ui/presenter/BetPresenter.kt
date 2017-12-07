package com.daking.lottery.ui.presenter

import com.daking.lottery.base.BasePresenter
import com.daking.lottery.model.LotteryInfo
import com.daking.lottery.ui.iview.IBetView
import com.daking.lottery.util.AccountHelper
import com.daking.lottery.util.Utils
import com.daking.lottery.util.retryWithDelay
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class BetPresenter : BasePresenter<IBetView>() {

    private var roundNum = ""
    private var closeTimeDisposable: Disposable? = null
    private var endTimeDisposable: Disposable? = null

    override fun onAttached() {
        super.onAttached()
        initBalance()
    }

    private fun initBalance() {
        val user = AccountHelper.instance.getUser()
        if (user != null) {
            mView.refreshBalance(user.balance)
        }
        refreshBalance()
    }

    private fun refreshBalance() {
        AccountHelper.instance.refreshAccount(mView, { user ->
            user?.let { mView.refreshBalance(user.balance) }
        })
    }

    fun getLotteryInfo(gameCode: Int) {
        mNetRepository.getLotteryInfo(gameCode)
                .retryWithDelay(3, 5000L)
                .dealObj({ _, _, model ->
                    //获取期数信息成功
                    dealLotteryInfo(gameCode, model)
                })
    }

    private fun getLotteryInfoDelay(gameCode: Int, delay: Long) {
        Flowable.timer(delay, TimeUnit.SECONDS)
                .compose(mView.bindLifecycle())
                .subscribe { getLotteryInfo(gameCode) }
    }

    private fun dealLotteryInfo(gameCode: Int, model: LotteryInfo) {
        val nextModel = model.next
        val lastModel = model.last

        //下期信息 主要是开奖倒计时, 封盘倒计时, 刷新开封盘状态
        nextModel?.let {
            with(nextModel) {
                //新刷出数据里下期 轮数变化 说明有新的数据 展示新数据
                if (roundNum != round) {
                    roundNum = round
                    //显示当前期数
                    mView.setNextRoundNum(roundNum)
                    //判断是否封盘
                    val endTimeCD = endTime - timestamp
                    val closeTimeCD = closeTime - timestamp
                    val isClosed = isClose == 1 || closeTimeCD <= 0
                    //刷新开封盘状态
                    mView.refreshCloseStatus(isClosed)
                    //如果已封盘, 封盘时间显示"--", 否则封盘时间开始倒计时
                    if (isClosed) mView.showCloseTime("--", "--")
                    else closeTimeCountDown(closeTimeCD)
                    //显示开奖时间并倒计时
                    endTimeCountDown(gameCode, endTimeCD)
                }

            }
        }


        lastModel?.let {
            mView.showLastRound(lastModel)
        }

        //判断上一期的号码是否开出来了, 如果开出来了, 则停止轮询, 否则每15秒轮询服务器查询上期开奖结果
        //判断的依据是 开奖期数差值为1时 表示已经出来了
        if (nextModel != null && lastModel != null) {
            val next = nextModel.round.substringAfterLast("-")
            val last = lastModel.round.substringAfterLast("-")
            if (next.toLong() - last.toLong() != 1L) {
                getLotteryInfoDelay(gameCode, 15L)
            }
        }
    }

    /**
     * 封盘时间倒计时
     */
    private fun closeTimeCountDown(closeTimeCD: Long) {
        if (closeTimeDisposable != null) {
            closeTimeDisposable!!.dispose()
        }
        closeTimeDisposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(closeTimeCD + 1)
                .map { aLong -> closeTimeCD - aLong }
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.bindLifecycle())
                .subscribe { aLong ->
                    val hour = Utils.getHour(aLong)
                    val minute = Utils.getMinute(aLong)
                    val second = Utils.getSecond(aLong)
                    if ("00" == hour) {
                        mView.showCloseTime(minute, second)
                    } else {
                        mView.showCloseTime(hour, minute, second)
                    }
                    //倒计时结束后刷新开封盘状态
                    if (aLong == 0L) {
                        mView.showCloseTime("--", "--")
                        mView.refreshCloseStatus(true)
                    }
                }
    }

    /**
     * 下期开奖时间倒计时
     */
    private fun endTimeCountDown(gameCode: Int, endTimeCD: Long) {
        if (endTimeDisposable != null) {
            endTimeDisposable!!.dispose()
        }
        endTimeDisposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(endTimeCD + 1)
                .map { aLong -> endTimeCD - aLong }
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.bindLifecycle())
                .subscribe { aLong ->
                    val endTime = Utils.seconds2Time(aLong)
                    mView.showEndTime(endTime)
                    //倒计时结束后从新请求数据
                    if (aLong == 0L) {
                        getLotteryInfo(gameCode)
                    }
                }
    }
}