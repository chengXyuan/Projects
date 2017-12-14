package com.daking.lottery.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import com.daking.lottery.R
import com.daking.lottery.app.Constant
import com.daking.lottery.base.BaseMVPActivity
import com.daking.lottery.dialog.BetDetailDialog
import com.daking.lottery.dialog.MainMenuPopupWindow
import com.daking.lottery.dialog.MoreGameTypePopup
import com.daking.lottery.dialog.easy.HorizontalGravity
import com.daking.lottery.dialog.easy.VerticalGravity
import com.daking.lottery.dialog.nice.BaseDialog
import com.daking.lottery.event.BetClosedEvent
import com.daking.lottery.event.ClearSelectionEvent
import com.daking.lottery.event.SelectStateChangeEvent
import com.daking.lottery.model.LastModel
import com.daking.lottery.model.MultiBetItem
import com.daking.lottery.model.TypeTitle
import com.daking.lottery.ui.iview.IBetView
import com.daking.lottery.ui.presenter.BetPresenter
import com.daking.lottery.util.LotteryUtils
import com.daking.lottery.util.toast
import com.daking.lottery.widget.OnMultiClickListener
import kotlinx.android.synthetic.main.activity_bet.*
import kotlinx.android.synthetic.main.fragment_lottery_info.*
import kotlinx.android.synthetic.main.layout_bet_bottom.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.collections.forEachWithIndex
import java.util.*

class BetActivity : BaseMVPActivity<BetPresenter>(), IBetView {

    private var gameCode = Constant.GAME_CODE_PJ_FUNNY_8
    private var menuPopup: MainMenuPopupWindow? = null
    private var mTypePopup: MoreGameTypePopup? = null
    private var isClosed = true
    private var fragPosition: Int = 0
    //下注选项(多选)
    private var selectBeans = ArrayList<MultiBetItem>()
    //下注详情弹窗
    private var mBetDetailDialog: BaseDialog? = null

    override fun getLayoutResId() = R.layout.activity_bet

    override fun initData(savedInstanceState: Bundle?) {
        gameCode = intent.getIntExtra(Constant.GAME_CODE, Constant.GAME_CODE_PJ_FUNNY_8)
        tvGameName.text = LotteryUtils.instance.getGameName(gameCode)

        ivBack.setOnClickListener { onBackPressed() }
        ivMenu.setOnClickListener { showMenuPopup() }
        tvReset.setOnClickListener { clearSelection() }
        btn_bet.setOnClickListener(object : OnMultiClickListener() {
            override fun noMultiClick(view: View) {
                commitBetting()
            }
        })
        et_bet_amount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    val text = s.toString()
                    if (s.length == 1 && text == "0") {
                        s.clear()
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        initBetType()
        setCurrentFragment(0)

        mPresenter.getLotteryInfo(gameCode)
    }

    private fun setCurrentFragment(pos: Int) {
        clearSelection()
        mPresenter.setFragment(gameCode, supportFragmentManager, R.id.flOddsContainer, pos, isClosed)
    }

    private fun initBetType() {
        val typeTitles = LotteryUtils.instance.getTabTitle(gameCode)
        addRadioButtons(0, typeTitles.take(4))
        if (typeTitles.size > 4) {
            ivShowMore.visibility = View.VISIBLE
            val moreTitle = typeTitles.drop(4)
            ivShowMore.setOnClickListener {
                showMoreTypePopup(moreTitle)
            }
        }
        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            val radioBtn = radioGroup.findViewById<RadioButton>(i)
            fragPosition = radioBtn.tag as Int
            setCurrentFragment(fragPosition)
        }
    }

    private fun addRadioButtons(selectPosition: Int, titles: List<TypeTitle>) {
        radioGroup.removeAllViews()
        titles.forEachWithIndex { i, item ->
            val radioBtn = RadioButton(this)
            radioBtn.tag = item.position
            radioBtn.text = item.title
            radioBtn.setBackgroundResource(R.drawable.selector_rb_bg)
            radioBtn.buttonDrawable = null
            radioBtn.textSize = 16f
            radioBtn.setTextColor(Color.WHITE)
            radioBtn.gravity = Gravity.CENTER
            val layoutParams = RadioGroup.LayoutParams(0, -1, 1f)
            radioGroup.addView(radioBtn, layoutParams)
            if (titles.size == 1) {
                radioBtn.isEnabled = false
            } else if (selectPosition == i) {
                /*此处使用RadioGroup.check(resId)会造成RadioGroup的onCheckedChanged()多次调用;
                若使用radioButton.setChecked(true)则会造成该radioButton的状态在RadioGroup中不可改变*/
                radioBtn.toggle()
            }
        }
    }

    private fun showMoreTypePopup(moreTitle: List<TypeTitle>) {
        if (mTypePopup == null) {
            mTypePopup = MoreGameTypePopup(this, gameCode, moreTitle,
                    object : MoreGameTypePopup.OnItemSelectListener {
                        override fun onItemSelect(position: Int, rgTitles: List<TypeTitle>) {
                            addRadioButtons(position, rgTitles)
                        }

                    }).createPopup()
        }
        mTypePopup?.showAtAnchorView(rlTypeContainer, VerticalGravity.BELOW, HorizontalGravity.CENTER)
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
        EventBus.getDefault().post(BetClosedEvent(gameCode, isClosed))
        //如果在显示下注详情弹窗的时候封盘了, 关闭详情弹窗
        if (isClosed && mBetDetailDialog != null && mBetDetailDialog!!.isVisible) {
            mBetDetailDialog!!.dismiss()
        }
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

    override fun onDestroy() {
        clearSelection()
        mPresenter.clearFragments(gameCode)
        super.onDestroy()
    }

    /**
     * 重置用户选择: 清除selectBeans, 下注数量重置为0,
     * EventBus发送事件到BetFragment去清除已经选择的选项
     */
    fun clearSelection() {
        if (selectBeans.isNotEmpty()) {
            selectBeans.clear()
            tv_select_num.text = "0"
            tv_select_num.isSelected = false
        }
        et_bet_amount.setText("")
        EventBus.getDefault().post(ClearSelectionEvent())
    }

    override fun useEventBus() = true

    @Subscribe
    fun onEvent(event: SelectStateChangeEvent) {
        val item = event.item
        if (item.isSelected) {
            selectBeans.add(item)
        } else if (selectBeans.contains(item)) {
            selectBeans.remove(item)
        }
        /*
        * 据传过来的item状态, 刷新selectBeans, mTvSelectNum, mBtnBet
        */
        btn_bet.isEnabled = selectBeans.isNotEmpty()
        tv_select_num.text = selectBeans.size.toString()
        tv_select_num.isSelected = selectBeans.size > 0
    }

    private fun commitBetting() {
        val frag = mPresenter.getCurrentFragment(gameCode, fragPosition)
        val selectedId = frag.getSelectedId()
        //检查用户的下注选项是否符合规则(是否为空, 是否小于最小选择数或大于最大选中数)
        val checkStr = LotteryUtils.instance.checkBetBeans(gameCode, fragPosition, selectedId, selectBeans)
        if (!checkStr.isNullOrEmpty()) {
            toast(checkStr)
            return
        }

        val money = et_bet_amount.text.toString().trim()
        if (money.isEmpty()) {
            toast(R.string.input_bet_money)
            return
        }
        //对下注选项排序
        Collections.sort(selectBeans)
        //当前下注期数
        val roundNum = mPresenter.roundNum
        if (roundNum.isEmpty()) {
            toast("下注期数为空!")
            return
        }
        //打开下注弹窗, 显示下注详情, 点击确认后请求下注接口(下注接口代码在BetDetailDialog中实现)
        //显示下注结果和提示信息
        //刷新余额
        mBetDetailDialog = BetDetailDialog.init(gameCode, fragPosition, selectedId, money.toInt(),
                roundNum, mPresenter.mEndTime, frag.getSelectItem(), selectBeans,
                object : BetDetailDialog.OnBetResultListener {
                    override fun onCommit() {
                        showLoadingDialog()
                    }

                    override fun onBetResult(isSuccess: Boolean, msg: String) {
                        dismissLoadingDialog()
                        toast(msg)
                        //刷新余额
                        mPresenter.refreshBalance()
                    }
                }).setMargin(40).show(supportFragmentManager)
    }
}