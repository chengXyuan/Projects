package com.daking.lottery.ui.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.daking.lottery.R
import com.daking.lottery.base.BaseMVPFragment
import com.daking.lottery.dialog.nice.BaseDialog
import com.daking.lottery.dialog.nice.NiceDialog
import com.daking.lottery.dialog.nice.ViewConvertListener
import com.daking.lottery.model.UserModel
import com.daking.lottery.ui.adapter.MineAdapter
import com.daking.lottery.ui.iview.IMineView
import com.daking.lottery.ui.presenter.MinePresenter
import com.daking.lottery.util.AccountHelper
import com.daking.lottery.util.format
import com.daking.lottery.widget.RecyclerViewDivider
import kotlinx.android.synthetic.main.dialog_common.view.*
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseMVPFragment<MinePresenter>(), IMineView {

    override fun getLayoutResId() = R.layout.fragment_mine

    override fun initData(savedInstanceState: Bundle?) {
        title_bar.setOnRightTextClickListener {
            NiceDialog.init()
                    .setLayoutId(R.layout.dialog_common)
                    .setConvertListener(object : ViewConvertListener() {
                        override fun convertView(view: View, dialog: BaseDialog) {
                            view.tv_msg.text = "确定要退出登录?"
                            view.tv_confirm.setOnClickListener { AccountHelper.instance.userSignOut(true) }
                            view.tv_cancel.setOnClickListener { dialog.dismiss() }
                            view.iv_close.setOnClickListener { dialog.dismiss() }
                        }
                    }).setMargin(40)
                    .setOutCancel(true)
                    .show(activity.supportFragmentManager)
        }
        iv_refresh.setOnClickListener { mPresenter.refreshUser() }
    }

    override fun refreshUser(user: UserModel?) {
        if (user != null) {
            tv_greeting.text = "晚上好，${user.username}！"
            tv_balance.text = "￥${user.balance.format()}"
        } else {
            tv_greeting.text = "请登录"
            tv_balance.text = "￥0.00"
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        //每次可见是刷新一下账户信息
        if (!hidden) {
            mPresenter.refreshUser()
        }
    }

    override fun showMineItems(mineAdapter: MineAdapter) {
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = GridLayoutManager(context, 3)
        recycler_view.addItemDecoration(RecyclerViewDivider(context,
                RecyclerViewDivider.BOTH_SET, R.drawable.shape_divider_line))
        recycler_view.adapter = mineAdapter
    }
}
