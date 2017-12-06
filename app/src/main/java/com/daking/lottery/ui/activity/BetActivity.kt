package com.daking.lottery.ui.activity

import android.os.Bundle
import com.daking.lottery.R
import com.daking.lottery.app.Constant

import com.daking.lottery.base.BaseMVPActivity
import com.daking.lottery.dialog.MainMenuPopupWindow
import com.daking.lottery.dialog.easy.HorizontalGravity
import com.daking.lottery.dialog.easy.VerticalGravity
import com.daking.lottery.model.LastModel
import com.daking.lottery.ui.iview.IBetView
import com.daking.lottery.ui.presenter.BetPresenter
import com.daking.lottery.util.LotteryUtils
import kotlinx.android.synthetic.main.activity_bet.*
import kotlinx.android.synthetic.main.fragment_home.*

class BetActivity : BaseMVPActivity<BetPresenter>(), IBetView {

    private var gameCode = Constant.GAME_CODE_PJ_FUNNY_8
    private var menuPopup: MainMenuPopupWindow? = null

    override fun getLayoutResId() = R.layout.activity_bet

    override fun initData(savedInstanceState: Bundle?) {
        gameCode = intent.getIntExtra(Constant.GAME_CODE, Constant.GAME_CODE_PJ_FUNNY_8)
        tvGameName.text = LotteryUtils.instance.getGameName(gameCode)
        ivBack.setOnClickListener { onBackPressed() }
        ivMenu.setOnClickListener { showMenuPopup() }

        mPresenter.getLotteryInfo(gameCode)
    }

    private fun showMenuPopup() {
        if (menuPopup == null) {
            menuPopup = MainMenuPopupWindow(this).createPopup()
        }
        menuPopup!!.refreshBalance()
        menuPopup!!.showAtAnchorView(fl_title_bar, VerticalGravity.BELOW, HorizontalGravity.ALIGN_RIGHT)
    }

    override fun showLastRound(lastModel: LastModel) {

    }

    override fun setNextRoundNum(round: String) {

    }

    override fun refreshCloseStatus(isClosed: Boolean) {

    }

    override fun showEndTime(endTime: String) {

    }

    override fun showCloseTime(minute: String, second: String) {

    }

    override fun showCloseTime(hour: String, minute: String, second: String) {

    }
}