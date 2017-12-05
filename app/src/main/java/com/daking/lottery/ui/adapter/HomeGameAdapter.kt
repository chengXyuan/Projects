package com.daking.lottery.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daking.lottery.R
import com.daking.lottery.model.GameModel
import kotlinx.android.synthetic.main.item_home_game.view.*
import java.util.*


class HomeGameAdapter(games: ArrayList<GameModel>)
    : BaseQuickAdapter<GameModel, BaseViewHolder>(R.layout.item_home_game, games) {

    override fun convert(helper: BaseViewHolder, item: GameModel) {
        helper.itemView.tv_game.text = item.gameName
        helper.itemView.tv_game.setCompoundDrawablesWithIntrinsicBounds(0, item.logoResId, 0, 0)
    }
}