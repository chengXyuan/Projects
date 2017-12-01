package com.daking.lottery.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daking.lottery.R
import com.daking.lottery.model.GameModel
import java.util.*


class HomeGameAdapter(games: ArrayList<GameModel>)
    : BaseQuickAdapter<GameModel, BaseViewHolder>(R.layout.item_home_game, games) {

    override fun convert(helper: BaseViewHolder, item: GameModel) {
        val tvGame = helper.getView<TextView>(R.id.tv_game)
        tvGame.text = item.gameName
        tvGame.setCompoundDrawablesWithIntrinsicBounds(0, item.logoResId, 0, 0)
    }
}