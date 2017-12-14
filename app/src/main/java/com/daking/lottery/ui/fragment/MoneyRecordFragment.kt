package com.daking.lottery.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.daking.lottery.R
import com.daking.lottery.base.BaseMVPListFragment
import com.daking.lottery.model.FundingModel
import com.daking.lottery.ui.adapter.MoneyRecordAdapter
import com.daking.lottery.ui.iview.IMoneyRecordView
import com.daking.lottery.ui.presenter.MoneyRecordPresenter
import com.daking.lottery.util.log
import com.daking.lottery.widget.RecyclerViewDivider
import kotlinx.android.synthetic.main.fragment_money_record.*

class MoneyRecordFragment : BaseMVPListFragment<MoneyRecordPresenter>(), IMoneyRecordView {

    private var isPrepare = false
    private lateinit var mAdapter: MoneyRecordAdapter

    override fun getLayoutResId() = R.layout.fragment_money_record

    override fun initData(savedInstanceState: Bundle?) {
        log("initData")
        isPrepare = true
        initRecyclerView()
        if (isVisible) {
            requestData(true)
        }
    }

    private fun initRecyclerView() {
        mAdapter = MoneyRecordAdapter()
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.addItemDecoration(RecyclerViewDivider(context,
                RecyclerViewDivider.HORIZONTAL_DIVIDER, R.drawable.shape_divider_line))
        setupList(refresh_layout, recycler_view, mAdapter)
    }

    override fun requestData(isRefresh: Boolean) {
        super.requestData(isRefresh)
        mPresenter.getMoneyRecord(mPageIndex)
    }

    override fun showRecord(data: List<FundingModel>?) {
        setData(mAdapter, data)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        log("setUserVisibleHint: $isVisibleToUser")
        if (isVisibleToUser && isPrepare) {
            requestData(true)
        }
    }

}
