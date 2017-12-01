package com.daking.lottery.ui.iview

import com.daking.lottery.base.BaseView
import com.daking.lottery.model.BannerModel
import com.daking.lottery.model.UserModel
import com.daking.lottery.ui.adapter.HomeGameAdapter

interface IHomeView : BaseView {
    fun showGames(gameAdapter: HomeGameAdapter)
    fun showBanner(data: List<BannerModel>)
    fun onComplete()
    fun refreshUser(user: UserModel?)
}
