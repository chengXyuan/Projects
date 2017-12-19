package com.daking.lottery.ui.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daking.lottery.R
import com.daking.lottery.model.MsgModel
import com.daking.lottery.util.FormatUtils
import kotlinx.android.synthetic.main.item_message.view.*

class MessageAdapter : BaseQuickAdapter<MsgModel, BaseViewHolder>(R.layout.item_message) {

    override fun convert(helper: BaseViewHolder, item: MsgModel) {
        helper.itemView.tvDate.text = FormatUtils.instance.convertDate(item.updateTime * 1000)
        helper.itemView.tvMsg.text = item.content
        if (helper.adapterPosition == 0) {
            helper.itemView.tvNew.visibility = View.VISIBLE
        } else {
            helper.itemView.tvNew.visibility = View.GONE
        }
    }
}
