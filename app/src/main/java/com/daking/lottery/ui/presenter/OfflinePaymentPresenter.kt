package com.daking.lottery.ui.presenter

import com.daking.lottery.R
import com.daking.lottery.base.BasePresenter
import com.daking.lottery.ui.iview.IOfflinePaymentView
import com.daking.lottery.util.Utils
import com.daking.lottery.util.isRealName
import com.daking.lottery.util.log
import com.daking.lottery.util.toast

class OfflinePaymentPresenter : BasePresenter<IOfflinePaymentView>() {

    fun commitOfflinePay(realName: String, amount: String, min: Int, dateTime: String, orderNo: String,
                         payTypeId: Int, address: String, bankNum: String, rechargeOffer: String) {
        when {
            !realName.isRealName() -> {
                toast(Utils.getString(R.string.input_real_name))
            }
            amount.isEmpty() -> {
                toast(Utils.getString(R.string.input_recharge_amount))
            }
            amount.toFloat() < min -> {
                toast("入款金额不能低于${min}元")
            }
            orderNo.length < 4 -> {
                toast(Utils.getString(R.string.input_order_num))
            }
            else -> {
                offlinePayment(orderNo, payTypeId, amount, realName, address, bankNum, rechargeOffer, dateTime)
            }
        }
    }

    private fun offlinePayment(orderNo: String, payTypeId: Int, amount: String,
                               realName: String, address: String, bankNum: String, rechargeOffer: String,
                               createdTime: String) {
        mNetRepository.offlinePayment(orderNo, payTypeId, amount, realName, address, bankNum,
                rechargeOffer, createdTime)
                .dealObj({ code, msg, _ ->
                    log("code=$code, msg: $msg")
                    toast(msg)
                    mView.paySuccess()
                }, { code, msg ->
                    log("code=$code, msg: $msg")
                    toast(msg)
                }, showLoading = true)
    }
}