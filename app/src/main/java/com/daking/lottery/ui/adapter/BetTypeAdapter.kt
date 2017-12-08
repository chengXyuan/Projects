package com.daking.lottery.ui.adapter

import android.widget.FrameLayout
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daking.lottery.R
import com.daking.lottery.model.BetTypeItem

class BetTypeAdapter(data: List<BetTypeItem>?)
    : BaseMultiItemQuickAdapter<BetTypeItem, BaseViewHolder>(data) {

    companion object {
        val TYPE_NORMAL = 1
        val TYPE_COMBO = 2
    }

    private var isClosed = true

    init {
        addItemType(TYPE_NORMAL, R.layout.item_type_title)
        addItemType(TYPE_COMBO, R.layout.item_lm_type)
    }

    fun setClosed(isClosed: Boolean) {
        this.isClosed = isClosed
        notifyDataSetChanged()
    }

    override fun convert(helper: BaseViewHolder, item: BetTypeItem) {
        helper.getView<FrameLayout>(R.id.fy_type).isSelected = item.selected
        helper.setVisible(R.id.img_mark, item.selected)
        helper.setText(R.id.tv_type_name, item.typeName)
        if (item.itemType == TYPE_COMBO) {
            val odds = if (isClosed || item.odds.isNullOrEmpty()) "封盘" else item.odds
            helper.setText(R.id.tv_type_code, odds)
        }
    }
}