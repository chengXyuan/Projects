package com.daking.lottery.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.daking.lottery.R
import com.daking.lottery.base.BaseMVPListFragment
import com.daking.lottery.ui.adapter.MoneyRecordAdapter
import com.daking.lottery.ui.iview.IMoneyRecordView
import com.daking.lottery.ui.presenter.MoneyRecordPresenter
import com.daking.lottery.widget.RecyclerViewDivider
import kotlinx.android.synthetic.main.fragment_money_record.*

class MoneyRecordFragment : BaseMVPListFragment<MoneyRecordPresenter>(), IMoneyRecordView {

    override fun getLayoutResId() = R.layout.fragment_money_record

    override fun initData(savedInstanceState: Bundle?) {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.addItemDecoration(RecyclerViewDivider(context,
                RecyclerViewDivider.HORIZONTAL_DIVIDER, R.drawable.shape_divider_line))

        val adapter = MoneyRecordAdapter()
        setupList(refresh_layout, recycler_view, adapter)
    }


}
