package com.daking.lottery.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.daking.lottery.R
import com.daking.lottery.base.BaseMVPListFragment
import com.daking.lottery.model.RecordDetail
import com.daking.lottery.ui.adapter.RecordDetailAdapter
import com.daking.lottery.ui.iview.IRecordDetailView
import com.daking.lottery.ui.presenter.RecordDetailPresenter
import com.daking.lottery.util.log
import kotlinx.android.synthetic.main.fragment_record_summary.*

class RecordDetailFragment : BaseMVPListFragment<RecordDetailPresenter>(), IRecordDetailView {

    private var isPrepare = false
    private var type = 0
    private var timestamp: Long? = null
    private lateinit var mAdapter: RecordDetailAdapter

    companion object {
        val TYPE_UNSETTLED = 0//未结
        val TYPE_SETTLED = 1//已结
        private val RECORD_TYPE = "record_type"
        private val TIMESTAMP = "timestamp"

        fun newInstance(type: Int, timestamp: Long? = null): RecordDetailFragment {
            val fragment = RecordDetailFragment()
            val args = Bundle()
            args.putInt(RECORD_TYPE, type)
            if (timestamp != null) args.putLong(TIMESTAMP, timestamp)
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutResId() = R.layout.fragment_record_summary

    override fun initData(savedInstanceState: Bundle?) {
        log("initData")
        isPrepare = true
        type = arguments.getInt(RECORD_TYPE)
        timestamp = arguments.getLong(TIMESTAMP)
        initRecyclerView()
        requestData(true)
    }

    private fun initRecyclerView() {
        mAdapter = RecordDetailAdapter(type)
        recyclerView.layoutManager = LinearLayoutManager(context)
        setupList(refreshLayout, recyclerView, mAdapter)
    }

    override fun requestData(isRefresh: Boolean) {
        super.requestData(isRefresh)
        mPresenter.getRecordDetail(type, mPageIndex, timestamp)
    }

    override fun showRecord(data: List<RecordDetail>?) {
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
