package com.daking.lottery.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daking.lottery.R
import com.daking.lottery.model.RecordSummary
import com.daking.lottery.util.FormatUtils
import com.daking.lottery.util.format
import kotlinx.android.synthetic.main.item_record_summary.view.*

class RecordSummaryAdapter : BaseQuickAdapter<RecordSummary, BaseViewHolder>(R.layout.item_record_summary) {

    override fun convert(helper: BaseViewHolder, item: RecordSummary) {
        with(item) {
            helper.itemView.isSelected = status == 0
            helper.itemView.tvLotteryDate.text = FormatUtils.instance.convertDate(createdTime * 1000)
            helper.itemView.tvLotteryCount.text = id
            helper.itemView.tvLotteryAmount.text = money.format()
            helper.itemView.tvBackWater.text = backwater.format()
            helper.itemView.tvMoneyCanWin.text = win.format()
            if (status == 0) {
                helper.itemView.tvMoneyCanWinT.text = "可赢金额"
                helper.itemView.tvBackWaterT.text = "预计退水"
                helper.itemView.tvTotalT.text = "预计"
                helper.itemView.tvTotal.text = "未结"
            } else {
                helper.itemView.tvMoneyCanWinT.text = "输赢金额"
                helper.itemView.tvBackWaterT.text = "退水金额"
                helper.itemView.tvTotalT.text = "总计"
                helper.itemView.tvTotal.text = "已结"
            }
        }
    }
}