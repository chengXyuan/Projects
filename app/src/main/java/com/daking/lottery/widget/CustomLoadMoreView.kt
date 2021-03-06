package com.daking.lottery.widget

import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.daking.lottery.R


class CustomLoadMoreView : LoadMoreView() {

    override fun getLayoutId() = R.layout.layout_load_more

    override fun getLoadingViewId() = R.id.lay_load_more_loading_view

    override fun getLoadEndViewId() = R.id.tv_load_more_no_more

    override fun getLoadFailViewId() = R.id.lay_load_more_load_fail_view
}