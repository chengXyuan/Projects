package com.daking.lottery.model

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.daking.lottery.R
import com.daking.lottery.ui.adapter.PayWaysAdapter
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PayWaysModel(@SerializedName("onlinepay") val onlinePay: List<OnlinePayModel>?,
                        @SerializedName("offlinepay") val offlinePay: List<OfflinePayModel>?)

data class OnlinePayModel(val paymentClass: Int,
                          val channel: List<OnlinePayChannel>)
    : MultiItemEntity, Serializable {

    fun getPayName() = when (paymentClass) {
        1 -> "网银在线充值"
        2 -> "微信在线充值"
        3 -> "支付宝在线充值"
        4 -> "财付通在线充值"
        else -> "在线充值"
    }

    fun getSmallLogoRes() = when (paymentClass) {
        1 /*在线*/ -> R.drawable.img_logo_union_small
        2/*微信*/ -> R.drawable.img_logo_wecaht_small
        3/*支付宝*/ -> R.drawable.img_logo_alipay_small
        4/*财付通*/ -> R.drawable.img_logo_cft_small
        else -> R.drawable.img_logo_union_small
    }

    fun getLargeLogoRes() = when (paymentClass) {
        1/*在线*/ -> R.drawable.img_logo_union_large
        2/*微信*/ -> R.drawable.img_logo_wecaht_large
        3/*支付宝*/ -> R.drawable.img_logo_alipay_large
        4/*财付通*/ -> R.drawable.img_logo_cft_large
        else -> R.drawable.img_logo_union_large
    }

    override fun getItemType() = PayWaysAdapter.TYPE_ONLINE_PAY
}

data class OnlinePayChannel(val payTypeId: String,
                            val businessCode: String,
                            val domain: String,
                            @SerializedName("notifyurl") val notifyUrl: String,
                            val rechargeOffer: String,
                            val paymentClass: Int,
                            val maximumAmount: Int,
                            val minimumAmount: Int,
                            val name: String) : Serializable

data class OfflinePayModel(val paymentClass: Int,
                           val channel: List<OfflinePayChannel>)
    : MultiItemEntity, Serializable {

    fun getPayName() = when (paymentClass) {
        4 -> "微信转账"
        5 -> "银行转账"
        6 -> "支付宝转账"
        else -> "银行转账"
    }

    fun getSmallLogoRes() = when (paymentClass) {
        4/*微信*/ -> R.drawable.img_logo_wecaht_small
        5/*在线*/ -> R.drawable.img_logo_union_small
        6/*支付宝*/ -> R.drawable.img_logo_alipay_small
        else -> R.drawable.img_logo_union_small
    }

    override fun getItemType() = PayWaysAdapter.TYPE_OFFLINE_PAY
}

data class OfflinePayChannel(val payTypeId: String,
                             val user: String,
                             val address: String,
                             val code: String,
                             val imageUrl: String?,
                             val rechargeOffer: String,
                             val maximumAmount: Int,
                             val minimumAmount: Int
) : Serializable

data class PayInTitle(val title: String,
                      val subTitle: String) : MultiItemEntity {

    override fun getItemType() = PayWaysAdapter.TYPE_TITLE
}