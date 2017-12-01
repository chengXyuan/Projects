package com.daking.lottery.ui.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.daking.lottery.R

import com.daking.lottery.base.BaseMVPFragment
import com.daking.lottery.ui.adapter.HomeGameAdapter
import com.daking.lottery.ui.iview.IHomeView
import com.daking.lottery.ui.presenter.HomePresenter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseMVPFragment<HomePresenter>(), IHomeView {

    override fun getLayoutResId() = R.layout.fragment_home

    override fun initData(savedInstanceState: Bundle?) {
        refresh_layout.setOnRefreshListener {
            refresh_layout.isRefreshing = true
            mPresenter.requestData()
        }
    }

    override fun showGames(gameAdapter: HomeGameAdapter) {
        rv_lottery.setHasFixedSize(true)
        rv_lottery.isNestedScrollingEnabled = false
        rv_lottery.layoutManager = GridLayoutManager(context, 3)
        rv_lottery.adapter = gameAdapter
    }
}
