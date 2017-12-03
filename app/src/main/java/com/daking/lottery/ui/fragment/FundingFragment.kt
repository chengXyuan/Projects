package com.daking.lottery.ui.fragment

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.LinearLayout
import com.daking.lottery.R

import com.daking.lottery.base.BaseMVPFragment
import com.daking.lottery.model.UserModel
import com.daking.lottery.ui.adapter.FundingAdapter
import com.daking.lottery.ui.iview.IFundingView
import com.daking.lottery.ui.presenter.FundingPresenter
import kotlinx.android.synthetic.main.fragment_funding.*

class FundingFragment : BaseMVPFragment<FundingPresenter>(), IFundingView {

    override fun getLayoutResId() = R.layout.fragment_funding

    override fun initData(savedInstanceState: Bundle?) {
        tab_layout.setupWithViewPager(view_pager)
        //为tabLayout添加分割线
        val linearLayout = tab_layout.getChildAt(0) as LinearLayout
        linearLayout.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        linearLayout.dividerDrawable = ContextCompat.getDrawable(context, R.drawable.shape_vertical_divider)
        //设置ViewPager的Adapter
        view_pager.adapter = FundingAdapter(childFragmentManager, arrayOf("充值", "提现", "交易记录"))
    }

    override fun showAccount(user: UserModel) {
        tv_user_name.text = user.username
        tv_balance.text = user.balance.toString()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        //每次可见是刷新一下账户信息
        if (!hidden) {
            mPresenter.refreshAccountInfo()
        }
    }
}
