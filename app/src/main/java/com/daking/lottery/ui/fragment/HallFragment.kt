package com.daking.lottery.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.daking.lottery.R
import com.daking.lottery.app.Constant
import com.daking.lottery.base.BaseMVPFragment
import com.daking.lottery.dialog.MainMenuPopupWindow
import com.daking.lottery.dialog.easy.HorizontalGravity
import com.daking.lottery.dialog.easy.VerticalGravity
import com.daking.lottery.model.OpenModel
import com.daking.lottery.ui.activity.LotteryHistoryActivity
import com.daking.lottery.ui.activity.WebViewActivity
import com.daking.lottery.ui.adapter.HallAdapter
import com.daking.lottery.ui.iview.IHallView
import com.daking.lottery.ui.presenter.HallPresenter
import com.daking.lottery.widget.RecyclerViewDivider
import kotlinx.android.synthetic.main.fragment_hall.*
import kotlinx.android.synthetic.main.layout_error_view.view.*
import org.jetbrains.anko.startActivity

class HallFragment : BaseMVPFragment<HallPresenter>(), IHallView {

    private var menuPopup: MainMenuPopupWindow? = null
    private lateinit var mAdapter: HallAdapter

    override fun getLayoutResId() = R.layout.fragment_hall

    @SuppressLint("SetTextI18n")
    override fun initData(savedInstanceState: Bundle?) {
        title_bar.setOnRightTextClickListener { showMenuPopup() }
        val website = Constant.LOTTERY_WEBSITE
                .replace("https://", "")
                .replace("http://", "")
                .replace("www.", "")
                .replace("/", "")
        tv_website.text = website
        rl_website.setOnClickListener {
            context.startActivity<WebViewActivity>(Pair(WebViewActivity.EXTRA_WEB_TITLE, "开奖网"),
                    Pair(WebViewActivity.EXTRA_WEB_URL, Constant.LOTTERY_WEBSITE))
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        mAdapter = HallAdapter()
        mAdapter.setOnItemClickListener { adapter, _, position ->
            val item = adapter.getItem(position) as OpenModel
            context.startActivity<LotteryHistoryActivity>(
                    Pair(Constant.GAME_CODE, item.gameCode)
            )
        }

        refresh_layout.isRefreshing = true
        refresh_layout.setColorSchemeResources(R.color.colorAccent)
        refresh_layout.setOnRefreshListener { mPresenter.startPollingServer() }

        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.addItemDecoration(RecyclerViewDivider(context,
                RecyclerViewDivider.HORIZONTAL_DIVIDER, R.drawable.shape_divider_line))

        mAdapter.setHasStableIds(true)
        recycler_view.adapter = mAdapter
    }

    private fun showMenuPopup() {
        if (menuPopup == null) {
            menuPopup = MainMenuPopupWindow(activity).createPopup()
        }
        menuPopup!!.refreshBalance()
        menuPopup!!.showAtAnchorView(title_bar, VerticalGravity.BELOW, HorizontalGravity.ALIGN_RIGHT)
    }

    override fun showLotteries(data: List<OpenModel>?) {
        mAdapter.setNewData(data)
    }

    override fun showError(msg: String) {
        /*error view*/
        val errorView = layoutInflater.inflate(R.layout.layout_error_view,
                recycler_view.parent as ViewGroup, false)
        errorView.setOnClickListener {
            refresh_layout.isRefreshing = true
            mPresenter.startPollingServer()
        }
        errorView.tv_error_view_msg.text = msg
        mAdapter.emptyView = errorView
    }

    override fun onComplete() {
        if (refresh_layout != null && refresh_layout.isRefreshing) {
            refresh_layout.isRefreshing = false
        }
    }

    override fun notifyItemChanged(index: Int, timeCD: Long) {
        mAdapter.notifyItemChanged(index, timeCD)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        //每次进入页面开始轮询服务器, 离开页面则停止轮询
        if (!hidden) {
            refresh_layout.isRefreshing = true
            mPresenter.startPollingServer()
        } else {
            mPresenter.stopPollingServer()
        }
    }
}
