package com.daking.lottery.ui.adapter

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daking.lottery.R
import com.daking.lottery.app.Constant
import com.daking.lottery.model.MultiBetItem
import com.daking.lottery.model.OddsItem
import com.daking.lottery.util.LotteryUtils

class BetDataAdapter(val gameCode: Int, data: List<MultiBetItem>?)
    : BaseMultiItemQuickAdapter<MultiBetItem, BaseViewHolder>(data) {

    companion object {
        val TITLE = 1
        val CONTENT_TEXT = 2
        val CONTENT_NUM = 3
        val CONTENT_LIAN = 4
    }

    private var isClosed = true
    private var selectPositions: HashSet<Int> = HashSet()

    init {
        addItemType(TITLE, R.layout.item_bet_data_title)
        addItemType(CONTENT_TEXT, R.layout.item_bet_content_text)
        addItemType(CONTENT_NUM, R.layout.item_bet_content_num)
        addItemType(CONTENT_LIAN, R.layout.item_bet_content_num)
    }

    fun setClosed(isClosed: Boolean) {
        this.isClosed = isClosed
        notifyDataSetChanged()
    }

    override fun convert(helper: BaseViewHolder, item: MultiBetItem) {
        when (item.itemType) {
            TITLE -> {//标题
                helper.setText(R.id.tv_bet_title, item.typeName)
            }
            CONTENT_TEXT -> {//文字
                val betItem = item.betItem
                setData(helper, item, betItem!!)
                helper.setText(R.id.tv_item_name, betItem.name)
            }
            CONTENT_NUM -> {//号码
                val betItem = item.betItem
                setData(helper, item, betItem!!)
                when (gameCode) {
                    Constant.GAME_CODE_PJ_PK_10,
                    Constant.GAME_CODE_CJ_LOTTERY,
                    Constant.GAME_CODE_LUCKY_AIRSHIP -> {
                    }
                    else -> helper.setText(R.id.tv_item_name, betItem.name)
                }
                helper.setBackgroundRes(R.id.tv_item_name, LotteryUtils.instance
                        .getLotteryNumBg(gameCode, betItem.name))
            }
            CONTENT_LIAN -> {//连码
                helper.getView<TextView>(R.id.tv_item_odds).visibility = View.GONE
                if (gameCode != Constant.GAME_CODE_CJ_LUCKY_FARM) {
                    helper.setText(R.id.tv_item_name, item.number.toString())
                }
                if (item.isSelected) {
                    selectPositions.add(helper.adapterPosition)
                    helper.getView<LinearLayout>(R.id.ll_bet_item).isSelected = true
                } else {
                    if (selectPositions.contains(helper.adapterPosition)) {
                        selectPositions.remove(helper.adapterPosition)
                    }
                    helper.getView<LinearLayout>(R.id.ll_bet_item).isSelected = false
                }
                helper.setBackgroundRes(R.id.tv_item_name, LotteryUtils.instance
                        .getLotteryNumBg(gameCode, item.number.toString()))
            }
        }
    }

    private fun setData(helper: BaseViewHolder, item: MultiBetItem, betItem: OddsItem) {
        if (isClosed) {
            helper.setText(R.id.tv_item_odds, "封盘")
            if (item.isSelected) {
                item.isSelected = false
            }
        } else {
            helper.setText(R.id.tv_item_odds, betItem.odds)
        }
        if (item.isSelected) {
            selectPositions.add(helper.adapterPosition)
            helper.getView<LinearLayout>(R.id.ll_bet_item).isSelected = true
        } else {
            if (selectPositions.contains(helper.adapterPosition)) {
                selectPositions.remove(helper.adapterPosition)
            }
            helper.getView<LinearLayout>(R.id.ll_bet_item).isSelected = false
        }
    }

    fun clearSelect() {
        selectPositions.forEach {
            val item = getItem(it)
            if (item != null) {
                item.isSelected = false
                notifyItemChanged(it, 0)
            }
        }
    }
}