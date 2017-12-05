package com.daking.lottery.ui.presenter

import com.daking.lottery.app.App
import com.daking.lottery.app.Constant
import com.daking.lottery.base.BasePresenter
import com.daking.lottery.repository.LocalRepository
import com.daking.lottery.ui.activity.MainActivity
import com.daking.lottery.ui.activity.WebViewActivity
import com.daking.lottery.ui.adapter.MineAdapter
import com.daking.lottery.ui.iview.IMineView
import com.daking.lottery.util.AccountHelper
import com.daking.lottery.util.SPUtils
import org.jetbrains.anko.startActivity

class MinePresenter : BasePresenter<IMineView>() {

    override fun onAttached() {
        super.onAttached()
        initUser()
        initMineItems()
    }

    private fun initUser() {
        val user = AccountHelper.instance.getUser()
        mView.refreshUser(user)
        refreshUser()
    }

    fun refreshUser() {
        AccountHelper.instance.refreshAccount(mView, { user ->
            mView.refreshUser(user)
        })
    }

    private fun initMineItems() {
        val mineItems = LocalRepository.instance.getMineItems()
        val mineAdapter = MineAdapter(mineItems)
        mView.showMineItems(mineAdapter)

        mineAdapter.setOnItemClickListener { _, _, position ->
            when (position) {
                0 -> {
                    //个人资料
                }
                1 -> {
                    //修改密码
                }
                2 -> {
                    //我的消息
                }
                3 -> {
                    //资金管理
                    App.instance.startActivity<MainActivity>(
                            Pair(MainActivity.MAIN_TAB_POSITION, 2))
                }
                4 -> {
                    //银行卡
                }
                5 -> {
                    //下注记录
                }
                6 -> {
                    //新闻中心
                }
                7 -> {
                    //关于我们
                }
                8 -> {
                    //客服中心
                    val url = SPUtils.instance.getString(Constant.SERVICE_URL, "")
                    App.instance.startActivity<WebViewActivity>(
                            Pair(WebViewActivity.EXTRA_WEB_TITLE, "在线客服"),
                            Pair(WebViewActivity.EXTRA_WEB_URL, url!!))
                }
            }
        }
    }
}