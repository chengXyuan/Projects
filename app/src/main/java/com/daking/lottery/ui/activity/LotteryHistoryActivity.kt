package com.daking.lottery.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.daking.lottery.R
import com.daking.lottery.app.Constant
import com.daking.lottery.base.BaseMVPListActivity
import com.daking.lottery.dialog.SwitchGamePopup
import com.daking.lottery.dialog.easy.HorizontalGravity
import com.daking.lottery.dialog.easy.VerticalGravity
import com.daking.lottery.model.OpenModel
import com.daking.lottery.ui.adapter.LotteryHistoryAdapter
import com.daking.lottery.ui.iview.ILotteryHistoryView
import com.daking.lottery.ui.presenter.LotteryHistoryPresenter
import com.daking.lottery.util.LotteryUtils
import com.daking.lottery.widget.RecyclerViewDivider
import kotlinx.android.synthetic.main.activity_lottery_history.*

class LotteryHistoryActivity : BaseMVPListActivity<LotteryHistoryPresenter>(), ILotteryHistoryView {

    private lateinit var mAdapter: LotteryHistoryAdapter
    private var gameCode = 1

    override fun getLayoutResId() = R.layout.activity_lottery_history

    override fun initData(savedInstanceState: Bundle?) {
        gameCode = intent.getIntExtra(Constant.GAME_CODE, 1)

        tvGameName.text = LotteryUtils.instance.getGameName(gameCode)
        tvGameName.setOnClickListener {
            showSwitchGamePopup()
        }
        ivBack.setOnClickListener { onBackPressed() }

        initRecyclerView()
        requestData(true)
    }

    private fun initRecyclerView() {
        mAdapter = LotteryHistoryAdapter()
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addItemDecoration(RecyclerViewDivider(this,
                RecyclerViewDivider.HORIZONTAL_DIVIDER, R.drawable.shape_divider_line))
        setupList(refreshLayout, recycler_view, mAdapter)
    }

    override fun showHistory(data: List<OpenModel>?) {
        setData(mAdapter, data)
    }

    override fun requestData(isRefresh: Boolean) {
        super.requestData(isRefresh)
        mPresenter.getLotteryHistory(gameCode, mPageIndex)
    }

    private fun showSwitchGamePopup() {
        SwitchGamePopup(this, gameCode, object : SwitchGamePopup.OnItemClickListener {
            override fun onItemClick(gameCode: Int) {
                intent.putExtra(Constant.GAME_CODE, gameCode)
                recreate()
            }
        }).createPopup<SwitchGamePopup>()
                .showAtAnchorView(titleBar, VerticalGravity.BELOW, HorizontalGravity.CENTER)
    }
}