package com.daking.lottery.ui.iview

import com.daking.lottery.base.BaseView
import com.daking.lottery.model.BannerModel
import com.daking.lottery.model.Promotion
import com.daking.lottery.model.UserModel
import com.daking.lottery.ui.adapter.HomeGameAdapter
import com.daking.lottery.ui.adapter.PromotionAdapter

interface IHomeView : BaseView {
    fun showGames(gameAdapter: HomeGameAdapter)
    fun showBanner(data: List<BannerModel>)
    fun onComplete()
    fun refreshUser(user: UserModel?)
    fun showMarquee(text: String)
    fun initPromotion(adapter: PromotionAdapter)
    fun showPromotions(data: List<Promotion>)
}
