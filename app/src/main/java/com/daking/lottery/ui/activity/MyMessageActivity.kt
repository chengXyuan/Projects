package com.daking.lottery.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.daking.lottery.R
import com.daking.lottery.base.BaseMVPListActivity
import com.daking.lottery.model.MsgModel
import com.daking.lottery.ui.adapter.MessageAdapter
import com.daking.lottery.ui.iview.IMyMessageView
import com.daking.lottery.ui.presenter.MyMessagePresenter
import com.daking.lottery.widget.RecyclerViewDivider
import kotlinx.android.synthetic.main.activity_my_message.*
import org.jetbrains.anko.startActivity

class MyMessageActivity : BaseMVPListActivity<MyMessagePresenter>(), IMyMessageView {

    private lateinit var mAdapter: MessageAdapter

    override fun getLayoutResId() = R.layout.activity_my_message

    override fun initData(savedInstanceState: Bundle?) {
        titleBar.setOnLeftTextClickListener { onBackPressed() }
        initRecyclerView()
        requestData(true)
    }

    private fun initRecyclerView() {
        mAdapter = MessageAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(RecyclerViewDivider(this,
                RecyclerViewDivider.HORIZONTAL_DIVIDER, R.drawable.shape_divider_line))
        setupList(refreshLayout, recyclerView, mAdapter)

        mAdapter.setOnItemClickListener { _, _, position ->
            val item = mAdapter.getItem(position)
            item?.let {
                startActivity<MessageDetailActivity>(Pair(MessageDetailActivity.MSG_MODEL, item))
            }
        }
    }

    override fun requestData(isRefresh: Boolean) {
        super.requestData(isRefresh)
        mPresenter.getAllMessage(mPageIndex)
    }

    override fun showMessages(data: List<MsgModel>?) {
        setData(mAdapter, data)
    }
}