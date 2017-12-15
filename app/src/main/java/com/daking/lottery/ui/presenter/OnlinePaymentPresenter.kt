package com.daking.lottery.ui.presenter

import com.daking.lottery.base.BasePresenter
import com.daking.lottery.ui.iview.IOnlinePaymentView
import com.daking.lottery.util.log
import com.daking.lottery.util.toast

class OnlinePaymentPresenter : BasePresenter<IOnlinePaymentView>() {

    fun commitRecharge(amount: String, min: Int, max: Int, businessCode: String, domain: String,
                       notifyUrl: String, remark: String, paymentClass: Int,
                       rechargeOffer: String, payTypeId: String, name: String) {
        when {
            amount.isEmpty() -> {
                toast("请输入充值金额")
            }
            amount.toInt() < min -> {
                toast("单次充值金额不能低于$min")
            }
            amount.toInt() > max -> {
                toast("单次充值金额不能超过$max")
            }
            amount.toInt() % 100 == 0 -> {
                toast("充值金额最后两位不能同时为0")
            }
            else -> {
                onlinePayment(amount, businessCode, domain, notifyUrl, remark, paymentClass,
                        rechargeOffer, payTypeId, name)
            }
        }
    }

    private fun onlinePayment(amount: String, businessCode: String, domain: String,
                              notifyUrl: String, remark: String, paymentClass: Int,
                              rechargeOffer: String, payTypeId: String, name: String) {
        mNetRepository.onlinePayment(amount, businessCode, domain, notifyUrl, remark,
                paymentClass, rechargeOffer, payTypeId, name).dealObj({ code, msg, model ->
            log("code=$code, msg: $msg")
            model?.let { mView.getOnlinePayUrl(model.url) }
        }, { code, msg ->
            log("code=$code, msg: $msg")
            toast(msg)
        }, showLoading = true)
    }


}