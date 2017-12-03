package com.daking.lottery.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.daking.lottery.R
import com.daking.lottery.base.BaseMVPFragment
import com.daking.lottery.ui.adapter.PayWaysAdapter
import com.daking.lottery.ui.iview.IRechargeView
import com.daking.lottery.ui.presenter.RechargePresenter
import com.daking.lottery.widget.RecyclerViewDivider
import kotlinx.android.synthetic.main.fragment_recharge.*

class RechargeFragment : BaseMVPFragment<RechargePresenter>(), IRechargeView {

    lateinit var mErrorView: View
    lateinit var mTvError: TextView
    lateinit var mAdapter: PayWaysAdapter

    override fun getLayoutResId() = R.layout.fragment_recharge

    override fun initData(savedInstanceState: Bundle?) {
        initRecyclerView()

        mPresenter.requestPayInWays()
    }

    private fun initRecyclerView() {
        refresh_layout.setColorSchemeResources(R.color.colorAccent)
        refresh_layout.setOnRefreshListener { mPresenter.requestPayInWays() }
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.addItemDecoration(RecyclerViewDivider(context,
                RecyclerViewDivider.HORIZONTAL_DIVIDER, R.drawable.shape_divider_line))
        mAdapter = PayWaysAdapter(null)
        recycler_view.adapter = mAdapter

        /*error view*/
        mErrorView = layoutInflater.inflate(R.layout.layout_error_view,
                recycler_view.parent as ViewGroup, false)
        mTvError = mErrorView.findViewById(R.id.tv_error_view_msg)
        mErrorView.setOnClickListener { mPresenter.requestPayInWays() }
    }

    override fun showPayWays(data: List<MultiItemEntity>) {
        if (data.isEmpty()) {
            val emptyView = layoutInflater.inflate(R.layout.layout_empty_view,
                    recycler_view.parent as ViewGroup, false)
            mAdapter.emptyView = emptyView
        }
        mAdapter.setNewData(data)
    }

    override fun onFailure(msg: String) {
        mTvError.text = msg
        mAdapter.emptyView = mErrorView
    }

    override fun onComplete() {
        if (refresh_layout != null && refresh_layout.isRefreshing) {
            refresh_layout.isRefreshing = false
        }
    }
}