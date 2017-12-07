package com.daking.lottery.ui.activity

import android.os.Bundle
import android.view.View
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
import kotlinx.android.synthetic.main.fragment_lottery_info.*

class BetActivity : BaseMVPActivity<BetPresenter>(), IBetView {

    private var gameCode = Constant.GAME_CODE_PJ_FUNNY_8
    private var menuPopup: MainMenuPopupWindow? = null
    private var isClosed = true

    override fun getLayoutResId() = R.layout.activity_bet

    override fun initData(savedInstanceState: Bundle?) {
        gameCode = intent.getIntExtra(Constant.GAME_CODE, Constant.GAME_CODE_PJ_FUNNY_8)
        tvGameName.text = LotteryUtils.instance.getGameName(gameCode)
        ivBack.setOnClickListener { onBackPressed() }
        ivMenu.setOnClickListener { showMenuPopup() }

        mPresenter.getLotteryInfo(gameCode)
    }

    /**
     * 弹出menuPopupWindow
     */
    private fun showMenuPopup() {
        if (menuPopup == null) {
            menuPopup = MainMenuPopupWindow(this).createPopup()
        }
        menuPopup!!.refreshBalance()
        menuPopup!!.showAtAnchorView(fl_title, VerticalGravity.BELOW, HorizontalGravity.ALIGN_RIGHT)
    }

    /**
     * 刷新余额
     */
    override fun refreshBalance(balance: Double) {
        tvBalance.text = balance.toString()
    }

    /**
     * 上期开奖数据
     */
    override fun showLastRound(lastModel: LastModel) {
        tvLastRound.text = getString(R.string.lottery_round, lastModel.round)
        lotteryNumberView.setNumbers(gameCode, lastModel.number)
        lotteryPropertyView.setNumbers(gameCode, lastModel.number)
    }

    /**
     * 下期期数
     */
    override fun setNextRoundNum(round: String) {
        tvNextRound.text = getString(R.string.lottery_round, round)
    }

    /**
     * 刷新封盘状态
     */
    override fun refreshCloseStatus(isClosed: Boolean) {
        this.isClosed = isClosed
    }

    /**
     * 开奖时间
     */
    override fun showEndTime(endTime: String) {
        tvOpenTime.text = endTime
    }

    /**
     * 封盘时间 只显示分和秒
     */
    override fun showCloseTime(minute: String, second: String) {
        if (llCloseTime.visibility == View.GONE || llCloseTime2.visibility == View.VISIBLE) {
            llCloseTime.visibility = View.VISIBLE
            llCloseTime2.visibility = View.GONE
        }
        tvCloseTimeMinute.text = minute
        tvCloseTimeSecond.text = second
    }

    /**
     * 封盘时间 显示时、分、秒
     */
    override fun showCloseTime(hour: String, minute: String, second: String) {
        if (llCloseTime.visibility == View.VISIBLE || llCloseTime2.visibility == View.GONE) {
            llCloseTime.visibility = View.GONE
            llCloseTime2.visibility = View.VISIBLE
        }
        tvCloseTime2Hour.text = hour
        tvCloseTime2Minute.text = minute
        tvCloseTime2Second.text = second
    }
}