package com.daking.lottery.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.daking.lottery.R
import com.daking.lottery.app.App
import com.daking.lottery.base.BaseMVPActivity
import com.daking.lottery.ui.fragment.FundingFragment
import com.daking.lottery.ui.fragment.HallFragment
import com.daking.lottery.ui.fragment.HomeFragment
import com.daking.lottery.ui.fragment.MineFragment
import com.daking.lottery.ui.iview.IMainView
import com.daking.lottery.ui.presenter.MainPresenter
import com.daking.lottery.util.toast
import com.daking.lottery.widget.TabEntity
import com.flyco.tablayout.listener.CustomTabEntity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseMVPActivity<MainPresenter>(), IMainView {

    private var mExitTime = 0L
    private var mFragments = ArrayList<Fragment>()

    override fun getLayoutResId() = R.layout.activity_main

    override fun initData(savedInstanceState: Bundle?) {
        val tabEntities = ArrayList<CustomTabEntity>()

        val titleIds = intArrayOf(
                R.string.home_page,
                R.string.lottery,
                R.string.funding,
                R.string.mine
        )
        val iconUnselectedIds = intArrayOf(
                R.drawable.ic_home_normal,
                R.drawable.ic_game_normal,
                R.drawable.ic_money_normal,
                R.drawable.ic_mine_normal
        )
        val iconSelectedIds = intArrayOf(
                R.drawable.ic_home_selected,
                R.drawable.ic_game_selected,
                R.drawable.ic_money_selected,
                R.drawable.ic_mine_selected
        )
        val fragClazz = arrayListOf<Class<*>>(
                HomeFragment::class.java,
                HallFragment::class.java,
                FundingFragment::class.java,
                MineFragment::class.java
        )
        titleIds.forEachIndexed { index, it ->
            tabEntities.add(TabEntity(getString(it), iconSelectedIds[index], iconUnselectedIds[index]))
            mFragments.add(fragClazz[index].newInstance() as Fragment)
        }
        bottom_tab.setTabData(tabEntities, this, R.id.fl_container, mFragments)
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            toast(R.string.click_again_to_exit)
            mExitTime = System.currentTimeMillis()
        } else {
            App.instance.exit()
        }
    }
}
