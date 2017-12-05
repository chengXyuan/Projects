package com.daking.lottery.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daking.lottery.R
import com.daking.lottery.model.MineItem
import kotlinx.android.synthetic.main.item_mine.view.*

class MineAdapter(data: List<MineItem>?)
    : BaseQuickAdapter<MineItem, BaseViewHolder>(R.layout.item_mine, data) {

    override fun convert(helper: BaseViewHolder, item: MineItem) {
        helper.itemView.tv_item.text = item.name
        helper.itemView.tv_item.setCompoundDrawablesWithIntrinsicBounds(0, item.resId, 0, 0)
    }
}