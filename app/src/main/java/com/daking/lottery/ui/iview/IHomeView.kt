package com.daking.lottery.ui.iview

import com.daking.lottery.base.BaseView
import com.daking.lottery.ui.adapter.HomeGameAdapter

interface IHomeView : BaseView {
    fun showGames(gameAdapter: HomeGameAdapter)
}
