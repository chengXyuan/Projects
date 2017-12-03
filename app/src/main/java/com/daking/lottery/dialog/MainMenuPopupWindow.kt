package com.daking.lottery.dialog

import android.support.v4.app.FragmentActivity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.daking.lottery.R
import com.daking.lottery.dialog.easy.BasePopupWindow
import com.daking.lottery.dialog.nice.BaseDialog
import com.daking.lottery.dialog.nice.NiceDialog
import com.daking.lottery.dialog.nice.ViewConvertListener
import com.daking.lottery.util.AccountHelper
import com.daking.lottery.util.Utils
import kotlinx.android.synthetic.main.dialog_common.view.*
import kotlinx.android.synthetic.main.popup_main_menu.view.*

class MainMenuPopupWindow(private val activity: FragmentActivity) : BasePopupWindow(activity) {

    private lateinit var mtvBalance: TextView

    override fun initAttributes() {
        setContentView(R.layout.popup_main_menu,
                Utils.dp2px(210), ViewGroup.LayoutParams.WRAP_CONTENT)
        setFocusAndOutsideEnable(true)
                .setBackgroundDimEnable(true)
                .setAnimationStyle(R.style.popup_drop_down)

    }

    override fun initViews(view: View) {
        mtvBalance = view.tv_balance
        view.tv_balance.setOnClickListener { }
        view.tv_recharge.setOnClickListener { }
        view.tv_withdraw.setOnClickListener { }
        view.tv_bet_record.setOnClickListener { }
        view.tv_personal_center.setOnClickListener { }
        view.tv_game_rule.setOnClickListener { }
        view.tv_service.setOnClickListener { }
        view.tv_log_out.setOnClickListener { userLogout() }
    }

    fun refreshBalance() {
        val user = AccountHelper.instance.getUser()
        if (user != null) {
            mtvBalance.text = user.balance.toString()
        }
    }

    private fun userLogout() {
        dismiss()
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_common)
                .setConvertListener(object : ViewConvertListener() {
                    override fun convertView(view: View, dialog: BaseDialog) {
                        view.tv_msg.text = "确定要退出登录?"
                        view.tv_confirm.setOnClickListener { AccountHelper.instance.userSignOut() }
                        view.tv_cancel.setOnClickListener { dialog.dismiss() }
                        view.iv_close.setOnClickListener { dialog.dismiss() }
                    }
                }).setMargin(40)
                .setOutCancel(true)
                .show(activity.supportFragmentManager)
    }
}