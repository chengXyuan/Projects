package com.daking.lottery.ui.presenter

import com.daking.lottery.app.App
import com.daking.lottery.app.Constant
import com.daking.lottery.base.BasePresenter
import com.daking.lottery.repository.LocalRepository
import com.daking.lottery.ui.activity.BetActivity
import com.daking.lottery.ui.adapter.HomeGameAdapter
import com.daking.lottery.ui.iview.IHomeView
import com.daking.lottery.util.AccountHelper
import org.jetbrains.anko.startActivity

class HomePresenter : BasePresenter<IHomeView>() {

    override fun onAttached() {
        super.onAttached()
        initGames()
        initPromotions()
        initUser()
        requestData()
    }

    private fun initUser() {
        val user = AccountHelper.instance.getUser()
        mView.refreshUser(user)
        refreshUser()
    }

    private fun refreshUser() {
        AccountHelper.instance.refreshAccount(mView, { user ->
            mView.refreshUser(user)
        })
    }

    private fun initGames() {
        val games = LocalRepository.instance.getHomeGames()
        val gameAdapter = HomeGameAdapter(games)
        gameAdapter.setOnItemClickListener { _, _, position ->
            App.instance.startActivity<BetActivity>(
                    Pair(Constant.GAME_CODE, gameAdapter.data[position].gameCode)
            )
        }
        mView.showGames(gameAdapter)
    }

    private fun initPromotions() {

    }

    fun requestData() {
        getBanner()
    }

    private fun getBanner() {
        mNetRepository.getBanner()
                .dealArray({ _, _, data ->
                    mView.showBanner(data!!)
                }, complete = {
                    mView.onComplete()
                })
    }
}