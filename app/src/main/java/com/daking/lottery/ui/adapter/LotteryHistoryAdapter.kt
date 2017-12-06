package com.daking.lottery.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daking.lottery.R
import com.daking.lottery.model.LotteryModel
import com.daking.lottery.util.Utils
import kotlinx.android.synthetic.main.item_lottery_history.view.*

class LotteryHistoryAdapter
    : BaseQuickAdapter<LotteryModel, BaseViewHolder>(R.layout.item_lottery_history) {

    override fun convert(helper: BaseViewHolder, item: LotteryModel) {
        with(item) {
            helper.itemView.lotteryRound.text = round
            helper.itemView.lotteryTime.text = mContext.getString(R.string.time,
                    Utils.convertTime(1000 * (openTime ?: 0)))
            val numbers = number.split(",")
            helper.itemView.lotteryNumberView.setNumbers(gameCode, numbers)
            helper.itemView.lotteryPropertyView.setNumbers(gameCode, numbers)
        }
    }
}