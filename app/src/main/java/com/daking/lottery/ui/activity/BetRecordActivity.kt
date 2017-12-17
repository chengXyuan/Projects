package com.daking.lottery.ui.activity

import android.os.Bundle
import com.daking.lottery.R

import com.daking.lottery.base.BaseMVPActivity
import com.daking.lottery.event.UnsettledCountEvent
import com.daking.lottery.ui.adapter.BetRecordAdapter
import com.daking.lottery.ui.iview.IBetRecordView
import com.daking.lottery.ui.presenter.BetRecordPresenter
import com.daking.lottery.util.Utils
import kotlinx.android.synthetic.main.activity_bet_record.*
import org.greenrobot.eventbus.Subscribe

class BetRecordActivity : BaseMVPActivity<BetRecordPresenter>(), IBetRecordView {

    override fun getLayoutResId() = R.layout.activity_bet_record

    override fun initData(savedInstanceState: Bundle?) {
        val adapter = BetRecordAdapter(supportFragmentManager, arrayOf("汇总", "未结注单", "已结注单"))
        viewPager.adapter = adapter
        tabLayout.setViewPager(viewPager)
    }

    override fun useEventBus() = true

    @Subscribe
    fun onEvent(event: UnsettledCountEvent) {
        if (event.total == 0) {
            tabLayout.hideMsg(1)
        } else {
            tabLayout.showMsg(1, event.total)
            tabLayout.setMsgMargin(1, Utils.dp2px(-2f), Utils.dp2px(4f))
        }
    }
}