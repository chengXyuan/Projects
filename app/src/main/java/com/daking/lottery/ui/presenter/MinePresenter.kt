package com.daking.lottery.ui.presenter

import com.daking.lottery.app.Constant
import com.daking.lottery.base.BasePresenter
import com.daking.lottery.repository.LocalRepository
import com.daking.lottery.ui.activity.*
import com.daking.lottery.ui.adapter.MineAdapter
import com.daking.lottery.ui.iview.IMineView
import com.daking.lottery.util.AccountHelper
import com.daking.lottery.util.SPUtils
import com.daking.lottery.util.log
import com.daking.lottery.util.toast
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
                    mView.getActivity().startActivity<PersonalInfoActivity>()
                }
                1 -> {
                    //修改密码
                }
                2 -> {
                    //我的消息
                }
                3 -> {
                    //资金管理
                    mView.getActivity().startActivity<MainActivity>(
                            Pair(MainActivity.MAIN_TAB_POSITION, 2))
                }
                4 -> {
                    //银行卡
                    getBankcard()
                }
                5 -> {
                    //下注记录
                    mView.getActivity().startActivity<BetRecordActivity>()
                }
                6 -> {
                    //新闻中心
                }
                7 -> {
                    //关于我们
                    mView.getActivity().startActivity<AboutUsActivity>()
                }
                8 -> {
                    //客服中心
                    val url = SPUtils.instance.getString(Constant.SERVICE_URL, "")
                    mView.getActivity().startActivity<WebViewActivity>(
                            Pair(WebViewActivity.EXTRA_WEB_TITLE, "在线客服"),
                            Pair(WebViewActivity.EXTRA_WEB_URL, url!!))
                }
            }
        }
    }

    private fun getBankcard() {
        mNetRepository.getBankcard()
                .dealObj({ code, msg, model ->
                    log("code=$code, msg: $msg")
                    if (model == null) {
                        mView.getActivity().startActivity<BankcardActivity>(Pair(BankcardActivity.EXTRA_IS_MODIFY, false))
                    } else {
                        mView.getActivity().startActivity<BankcardActivity>(Pair(BankcardActivity.EXTRA_IS_MODIFY, true),
                                Pair(BankcardActivity.EXTRA_ODD_BANK, model))
                    }
                }, { code, msg ->
                    log("code=$code, msg: $msg")
                    toast(msg)
                })
    }
}