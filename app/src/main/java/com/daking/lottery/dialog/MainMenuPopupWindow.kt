package com.daking.lottery.dialog

import android.support.v4.app.FragmentActivity
import android.view.View
import android.view.ViewGroup
import com.daking.lottery.R
import com.daking.lottery.dialog.easy.BasePopupWindow
import com.daking.lottery.util.AccountHelper
import com.daking.lottery.util.Utils
import kotlinx.android.synthetic.main.popup_main_menu.view.*

class MainMenuPopupWindow(private val activity: FragmentActivity) : BasePopupWindow(activity) {

    override fun initAttributes() {
        setContentView(R.layout.popup_main_menu,
                Utils.dp2px(210), ViewGroup.LayoutParams.WRAP_CONTENT)
        setFocusAndOutsideEnable(true)
                .setBackgroundDimEnable(true)
                .setAnimationStyle(R.style.popup_drop_down)

    }

    override fun initViews(view: View) {
        view.tv_balance.setOnClickListener { }
        view.tv_recharge.setOnClickListener { }
        view.tv_withdraw.setOnClickListener { }
        view.tv_bet_record.setOnClickListener { }
        view.tv_personal_center.setOnClickListener { }
        view.tv_game_rule.setOnClickListener { }
        view.tv_service.setOnClickListener { }
        view.tv_log_out.setOnClickListener { userLogout() }
    }

    private fun userLogout() {
        dismiss()
        CommonDialog.init("确定要退出登录?")
                .setDialogListener(object : CommonDialog.OnDialogListener {
                    override fun onConfirm(dialog: CommonDialog) {
                        AccountHelper.instance.userSignOut()
                    }
                }).setMargin(40)
                .setOutCancel(true)
                .show(activity.supportFragmentManager)
    }
}