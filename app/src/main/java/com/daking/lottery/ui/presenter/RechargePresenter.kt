package com.daking.lottery.ui.presenter

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.daking.lottery.base.BasePresenter
import com.daking.lottery.model.PayInTitle
import com.daking.lottery.model.PayWaysModel
import com.daking.lottery.ui.iview.IRechargeView
import java.util.*

class RechargePresenter : BasePresenter<IRechargeView>() {

    fun requestPayInWays() {
        mNetRepository.requestPayInWays()
                .dealObj({ _, _, model ->
                    dealPayInData(model)
                }, { _, msg ->
                    mView.onFailure(msg)
                }, {
                    mView.onComplete()
                })
    }

    private fun dealPayInData(data: PayWaysModel) {
        val payWays = ArrayList<MultiItemEntity>()

        //线上支付
        val onlinePay = data.onlinePay
        if (onlinePay != null && onlinePay.isNotEmpty()) {
            payWays.add(PayInTitle("在线支付", "Online Payment"))
            payWays.addAll(onlinePay)
        }

        //线下支付
        val offlinePay = data.offlinePay
        if (offlinePay != null && offlinePay.isNotEmpty()) {
            payWays.add(PayInTitle("线下支付", "Offline Payment"))
            payWays.addAll(offlinePay)
        }

        //显示支付方式
        mView.showPayWays(payWays)
    }
}