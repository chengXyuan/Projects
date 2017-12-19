package com.daking.lottery.ui.presenter

import com.daking.lottery.app.Constant
import com.daking.lottery.base.BasePresenter
import com.daking.lottery.event.OutOfSignEvent
import com.daking.lottery.model.Promotion
import com.daking.lottery.repository.LocalRepository
import com.daking.lottery.ui.activity.BetActivity
import com.daking.lottery.ui.activity.WebViewActivity
import com.daking.lottery.ui.adapter.HomeGameAdapter
import com.daking.lottery.ui.adapter.PromotionAdapter
import com.daking.lottery.ui.iview.IHomeView
import com.daking.lottery.util.AccountHelper
import org.greenrobot.eventbus.Subscribe
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
            mView.getActivity().startActivity<BetActivity>(
                    Pair(Constant.GAME_CODE, gameAdapter.data[position].gameCode)
            )
        }
        mView.showGames(gameAdapter)
    }

    private fun initPromotions() {
        val promotionAdapter = PromotionAdapter()
        promotionAdapter.setOnItemClickListener { adapter, _, position ->
            val item = adapter.getItem(position) as Promotion
            mView.getActivity().startActivity<WebViewActivity>(
                    Pair(WebViewActivity.EXTRA_WEB_TITLE, item.title),
                    Pair(WebViewActivity.EXTRA_WEB_URL, item.webUrl))
        }
        mView.initPromotion(promotionAdapter)
    }

    fun requestData() {
        getBanner()
        getAllMessage()
        getPromotions()
    }

    private fun getBanner() {
        mNetRepository.getBanner()
                .dealArray({ _, _, data ->
                    mView.showBanner(data!!)
                }, complete = {
                    mView.onComplete()
                })
    }

    private fun getAllMessage() {
        mNetRepository.getAllMessage(1, -1)
                .dealArray({ _, _, data ->
                    data?.let {
                        val text = data.fold("") { acc, msgModel ->
                            acc + msgModel.content + "        "
                        }
                        mView.showMarquee(text)
                    }
                })
    }

    private fun getPromotions() {
        mNetRepository.getPromotions(1, 10)
                .dealArray({ _, _, data ->
                    data?.let {
                        mView.showPromotions(data)
                    }
                })

    }

    override fun useEventBus() = true

    @Subscribe
    fun onEvent(event: OutOfSignEvent) {
        refreshUser()
    }
}