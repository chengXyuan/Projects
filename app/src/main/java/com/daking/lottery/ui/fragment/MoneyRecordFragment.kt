package com.daking.lottery.ui.fragment

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.daking.lottery.R
import com.daking.lottery.base.BaseMVPListFragment
import com.daking.lottery.dialog.nice.BaseDialog
import com.daking.lottery.dialog.nice.NiceDialog
import com.daking.lottery.dialog.nice.ViewConvertListener
import com.daking.lottery.model.FundingModel
import com.daking.lottery.ui.adapter.MoneyRecordAdapter
import com.daking.lottery.ui.iview.IMoneyRecordView
import com.daking.lottery.ui.presenter.MoneyRecordPresenter
import com.daking.lottery.util.FormatUtils
import com.daking.lottery.util.format
import com.daking.lottery.util.log
import com.daking.lottery.widget.RecyclerViewDivider
import kotlinx.android.synthetic.main.dialog_money_record_detail.*
import kotlinx.android.synthetic.main.dialog_money_record_detail.view.*
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

        mAdapter.setOnItemClickListener { adapter, _, position ->
            val item = adapter.getItem(position) as FundingModel
            NiceDialog.init()
                    .setLayoutId(R.layout.dialog_money_record_detail)
                    .setConvertListener(object : ViewConvertListener() {
                        override fun convertView(view: View, dialog: BaseDialog) {
                            view.btnConfirm.setOnClickListener { dialog.dismiss() }
                            view.ivClose.setOnClickListener { dialog.dismiss() }
                            tvStyle.text = if (item.type == 0) "存入" else "取出"
                            tvAmount.text = item.money.format()
                            tvTradeNo.text = item.billCode
                            tvTime.text = FormatUtils.instance.convertDateTime(item.createdTime * 1000)
                            when (item.orderStatus) {
                                0 -> {
                                    tvStatus.text = "待审核"
                                    tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.blue700))
                                }
                                1 -> {
                                    tvStatus.text = "成功"
                                    tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.text_normal))
                                }
                                2 -> {
                                    tvStatus.text = "失败"
                                    tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.red700))
                                }
                            }
                            if (!item.remark.isNullOrEmpty()) {
                                tvRemark.visibility = View.VISIBLE
                                tvRemark.text = item.remark
                            }
                        }
                    }).setMargin(40)
                    .setOutCancel(true)
                    .show(activity.supportFragmentManager)
        }
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
