package com.daking.lottery.ui.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daking.lottery.R
import com.daking.lottery.model.RecordDetail
import com.daking.lottery.ui.fragment.RecordDetailFragment
import com.daking.lottery.util.FormatUtils
import com.daking.lottery.util.format
import kotlinx.android.synthetic.main.item_record_detail.view.*

class RecordDetailAdapter(val type: Int)
    : BaseQuickAdapter<RecordDetail, BaseViewHolder>(R.layout.item_record_detail) {

    override fun convert(helper: BaseViewHolder, item: RecordDetail) {
        with(item) {
            helper.itemView.tvOrderId.text = "订单号:$id"
            helper.itemView.tvOrderStatus.text = if (type == RecordDetailFragment.TYPE_SETTLED) "已结" else "未结"
            helper.itemView.tvLotteryName.text = games
            helper.itemView.tvLotteryRound.text = "第${round}期"
            helper.itemView.tvPlayDesc.text = "$gamesPlay1 $gamesPlay2 $gamesPlay3 $gamesPlay4 $smallBall @$rate"
            helper.itemView.tvBetTime.text = FormatUtils.instance.convertDateTime(createdTime * 1000)
            if (countPay == 1) {
                helper.itemView.tvBetMoney.text = totalMonty.format()
            } else {
                helper.itemView.tvBetMoney.text = "$money * $countPay = $totalMonty"
            }
            if (type == RecordDetailFragment.TYPE_SETTLED) {
                //已结
                helper.itemView.isSelected = false
                helper.itemView.flCanWin.visibility = View.GONE
                helper.itemView.cLResult.visibility = View.VISIBLE
                helper.itemView.tvWinOrLose.text = trueWin.format()
                helper.itemView.tvBackWater.text = userCut.format()
                helper.itemView.tvMoneyAfter.text = moneyAfter.format()
            } else {
                //未结
                helper.itemView.isSelected = true
                helper.itemView.flCanWin.visibility = View.VISIBLE
                helper.itemView.cLResult.visibility = View.GONE
                helper.itemView.tvCanWin.text = win.format()
            }
        }
    }
}