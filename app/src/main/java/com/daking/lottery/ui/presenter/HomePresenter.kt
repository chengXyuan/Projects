package com.daking.lottery.ui.presenter

import com.daking.lottery.base.BasePresenter
import com.daking.lottery.repository.LocalRepository
import com.daking.lottery.ui.adapter.HomeGameAdapter
import com.daking.lottery.ui.iview.IHomeView

class HomePresenter : BasePresenter<IHomeView>() {

    override fun onAttached() {
        super.onAttached()
        initGames()
        initPromotions()
    }

    private fun initGames() {
        val games = LocalRepository.instance.getHomeGames()
        val gameAdapter = HomeGameAdapter(games)
        mView.showGames(gameAdapter)
    }

    private fun initPromotions() {

    }

    fun requestData() {

    }
}