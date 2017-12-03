package com.daking.lottery.ui.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.daking.lottery.R
import com.daking.lottery.model.OfflinePayModel
import com.daking.lottery.model.OnlinePayModel
import com.daking.lottery.model.PayInTitle

class PayWaysAdapter(data: List<MultiItemEntity>?)
    : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    companion object {
        val TYPE_TITLE = 0
        val TYPE_ONLINE_PAY = 1
        val TYPE_OFFLINE_PAY = 2
    }

    init {
        addItemType(TYPE_TITLE, R.layout.item_pay_title)
        addItemType(TYPE_ONLINE_PAY, R.layout.item_pay_item)
        addItemType(TYPE_OFFLINE_PAY, R.layout.item_pay_item)
    }

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity) {
        when (helper.itemViewType) {
            TYPE_TITLE -> {
                val item0 = item as PayInTitle
                helper.setText(R.id.tv_title, item0.title)
                helper.setText(R.id.tv_sub_title, item0.subTitle)
            }
            TYPE_ONLINE_PAY -> {
                val item0 = item as OnlinePayModel
                helper.setBackgroundRes(R.id.iv_logo, item0.getSmallLogoRes())
                helper.setText(R.id.tv_pay_name, item0.getPayName())
            }
            TYPE_OFFLINE_PAY -> {
                val item0 = item as OfflinePayModel
                helper.setBackgroundRes(R.id.iv_logo, item0.getSmallLogoRes())
                helper.setText(R.id.tv_pay_name, item0.getPayName())
            }
        }
    }
}
