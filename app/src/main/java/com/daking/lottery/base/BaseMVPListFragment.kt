package com.daking.lottery.base

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daking.lottery.R
import com.daking.lottery.widget.CustomLoadMoreView

abstract class BaseMVPListFragment<P : BasePresenter<*>> : BaseMVPFragment<P>(), BaseQuickAdapter.RequestLoadMoreListener {

    private lateinit var mAdapter: BaseQuickAdapter<*, *>
    private lateinit var mRefreshLayout: SwipeRefreshLayout
    private lateinit var mEmptyView: View
    private lateinit var mErrorView: View
    private lateinit var mTvError: TextView

    private var mIsRefresh = false
    open var mPageIndex = 1

    companion object {
        val PAGE_SIZE = 20
    }

    fun <T, K : BaseViewHolder> setupList(refreshLayout: SwipeRefreshLayout,
                                          recyclerView: RecyclerView, adapter: BaseQuickAdapter<T, K>) {
        mAdapter = adapter
        mRefreshLayout = refreshLayout

        /*emptyView*/
        mEmptyView = layoutInflater.inflate(R.layout.layout_empty_view,
                recyclerView.parent as ViewGroup, false)

        /*error view*/
        mErrorView = layoutInflater.inflate(R.layout.layout_error_view,
                recyclerView.parent as ViewGroup, false)
        mErrorView.setOnClickListener { requestData(true) }
        mTvError = mErrorView.findViewById(R.id.tv_error_view_msg)

        /*refresh*/
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent)
        mRefreshLayout.setOnRefreshListener { requestData(true) }

        /*load more*/
        mAdapter.setLoadMoreView(CustomLoadMoreView())
        mAdapter.setOnLoadMoreListener(this, recyclerView)

        recyclerView.adapter = mAdapter
    }

    override fun onLoadMoreRequested() {
        requestData(false)
    }

    open fun requestData(isRefresh: Boolean) {
        mIsRefresh = isRefresh
        if (mIsRefresh) {
            mPageIndex = 1
            mRefreshLayout.isRefreshing = true
        } else mPageIndex++
    }

    fun <T, K : BaseViewHolder> setData(adapter: BaseQuickAdapter<T, K>, data: List<T>?) {
        with(adapter) {
            if (mIsRefresh) {
                setNewData(data)
                disableLoadMoreIfNotFullPage()
                if (data == null || data.isEmpty()) emptyView = mEmptyView
            } else if (data != null) {
                addData(data)
            }
            if (data == null || data.size < PAGE_SIZE) loadMoreEnd()
            else loadMoreComplete()
        }
    }

    fun showError(msg: String) {
        with(mAdapter) {
            if (mIsRefresh) {
                emptyView = mErrorView
                mTvError.text = msg
            } else loadMoreFail()
        }
    }

    fun onComplete() {
        mRefreshLayout.isRefreshing = false
    }
}