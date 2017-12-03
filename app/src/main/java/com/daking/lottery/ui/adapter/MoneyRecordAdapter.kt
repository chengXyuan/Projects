package com.daking.lottery.ui.adapter

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daking.lottery.R
import com.daking.lottery.model.FundingModel
import com.daking.lottery.util.Utils

class MoneyRecordAdapter : BaseQuickAdapter<FundingModel, BaseViewHolder>(R.layout.item_record) {

    override fun convert(helper: BaseViewHolder, item: FundingModel) {
        helper.setText(R.id.tv_time, Utils.convertTime(item.createdTime * 1000))
        val type = if (item.type == 0) Utils.getString(R.string.deposit) else Utils.getString(R.string.take_out)
        helper.setText(R.id.tv_amount, "$type ${item.money}")
        when (item.orderStatus) {
            0/*审核中*/ -> {
                helper.setText(R.id.tv_record_status, Utils.getString(R.string.untreated))
                helper.setTextColor(R.id.tv_record_status, ContextCompat.getColor(mContext, R.color.green400))
            }
            1/*成功*/ -> {
                helper.setText(R.id.tv_record_status, Utils.getString(R.string.success))
                helper.setTextColor(R.id.tv_record_status, ContextCompat.getColor(mContext, R.color.disabled))
            }
            2/*失败*/ -> {
                helper.setText(R.id.tv_record_status, Utils.getString(R.string.fail))
                helper.setTextColor(R.id.tv_record_status, ContextCompat.getColor(mContext, R.color.red400))
            }
        }
    }
}