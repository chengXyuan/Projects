package com.daking.lottery.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.daking.lottery.R
import com.daking.lottery.base.BaseMVPListFragment
import com.daking.lottery.model.RecordSummary
import com.daking.lottery.ui.activity.RecordOfDateActivity
import com.daking.lottery.ui.adapter.RecordSummaryAdapter
import com.daking.lottery.ui.iview.IRecordSummaryView
import com.daking.lottery.ui.presenter.RecordSummaryPresenter
import com.daking.lottery.util.log
import kotlinx.android.synthetic.main.fragment_record_summary.*
import org.jetbrains.anko.startActivity

class RecordSummaryFragment : BaseMVPListFragment<RecordSummaryPresenter>(), IRecordSummaryView {

    private var isPrepare = false
    private lateinit var mAdapter: RecordSummaryAdapter

    override fun getLayoutResId() = R.layout.fragment_record_summary

    override fun initData(savedInstanceState: Bundle?) {
        log("initData")
        isPrepare = true
        initRecyclerView()
        if (isVisible) {
            requestData(true)
        }
    }

    private fun initRecyclerView() {
        mAdapter = RecordSummaryAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        setupList(refreshLayout, recyclerView, mAdapter)
        //点击事件
        mAdapter.setOnItemClickListener { _, _, position ->
            val item = mAdapter.getItem(position)
            item?.let {
                context.startActivity<RecordOfDateActivity>(
                        Pair(RecordOfDateActivity.EXTRA_STATUS, item.status),
                        Pair(RecordOfDateActivity.EXTRA_TIMESTAMP, item.createdTime))
            }
        }
    }

    override fun requestData(isRefresh: Boolean) {
        super.requestData(isRefresh)
        mPresenter.getRecordSummary(mPageIndex)
    }

    override fun showRecord(data: List<RecordSummary>?) {
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
