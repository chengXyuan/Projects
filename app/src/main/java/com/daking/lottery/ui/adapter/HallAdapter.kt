package com.daking.lottery.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daking.lottery.R
import com.daking.lottery.model.LotteryModel
import com.daking.lottery.util.Utils
import kotlinx.android.synthetic.main.item_lottery_hall.view.*

class HallAdapter : BaseQuickAdapter<LotteryModel, BaseViewHolder>(R.layout.item_lottery_hall) {

    override fun convert(helper: BaseViewHolder, item: LotteryModel) {
        with(item) {
            helper.itemView.tv_game_name.text = getGameName()
            helper.itemView.tv_round_number.text = mContext.getString(R.string.lottery_round, round)
            if (isClose == 0 && openTime != null && sysTime != null) {
                val timeCD = (openTime - sysTime)
                helper.itemView.tv_lottery_time.text = Utils.seconds2Time(timeCD)
            } else {
                helper.itemView.tv_lottery_time.setText(R.string.lottery_close)
            }
            val numbers = number.split(",")
            helper.itemView.lotteryNumberView.setNumbers(gameCode, numbers)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int, payloads: MutableList<Any>?) {
        if (payloads != null && payloads.isNotEmpty()) {
            val timeCD = payloads[0] as Long
            holder.itemView.tv_lottery_time.text = Utils.seconds2Time(timeCD)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }
}
