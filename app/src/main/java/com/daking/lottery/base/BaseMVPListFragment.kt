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

    lateinit var mRefresshLayout: SwipeRefreshLayout
    lateinit var mEmptyView: View
    lateinit var mErrorView: View
    lateinit var mTvError: TextView

    private var mIsRefresh = false
    private var mPageIndex = 1

    companion object {
        val PAGE_SIZE = 20
    }

    fun <T, K : BaseViewHolder> setupList(refreshLayout: SwipeRefreshLayout, recyclerView: RecyclerView,
                                          adapter: BaseQuickAdapter<T, K>) {
        mRefresshLayout = refreshLayout

        /*emptyView*/
        mEmptyView = layoutInflater.inflate(R.layout.layout_empty_view,
                recyclerView.parent as ViewGroup, false)

        /*error view*/
        mErrorView = layoutInflater.inflate(R.layout.layout_error_view,
                recyclerView.parent as ViewGroup, false)
        mTvError = mErrorView.findViewById(R.id.tv_error_view_msg)

        /*refresh*/
        mRefresshLayout.setColorSchemeResources(R.color.colorAccent)
        mRefresshLayout.setOnRefreshListener { requestData(true) }

        /*loadmore*/
        adapter.setLoadMoreView(CustomLoadMoreView())
        adapter.setOnLoadMoreListener(this, recyclerView)

        recyclerView.adapter = adapter
    }

    override fun onLoadMoreRequested() {
        requestData(false)
    }

    open fun requestData(isRefresh: Boolean) {
        mIsRefresh = isRefresh
        if (mIsRefresh) mPageIndex = 1
        else mPageIndex++
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

    fun <T, K : BaseViewHolder> showError(adapter: BaseQuickAdapter<T, K>, msg: String) {
        with(adapter) {
            if (mIsRefresh) {
                emptyView = mErrorView
                mTvError.text = msg
            } else loadMoreFail()
        }
    }

    fun onComplete() {
        mRefresshLayout.isRefreshing = false
    }
}