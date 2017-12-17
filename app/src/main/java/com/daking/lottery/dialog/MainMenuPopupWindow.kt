package com.daking.lottery.dialog

import android.support.v4.app.FragmentActivity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.daking.lottery.R
import com.daking.lottery.app.Constant
import com.daking.lottery.dialog.easy.BasePopupWindow
import com.daking.lottery.dialog.nice.BaseDialog
import com.daking.lottery.dialog.nice.NiceDialog
import com.daking.lottery.dialog.nice.ViewConvertListener
import com.daking.lottery.ui.activity.MainActivity
import com.daking.lottery.ui.activity.WebViewActivity
import com.daking.lottery.util.AccountHelper
import com.daking.lottery.util.SPUtils
import com.daking.lottery.util.Utils
import com.daking.lottery.util.format
import kotlinx.android.synthetic.main.dialog_common.view.*
import kotlinx.android.synthetic.main.popup_main_menu.view.*
import org.jetbrains.anko.startActivity

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
        view.tv_recharge.setOnClickListener {
            //跳转到充值页面
            activity.startActivity<MainActivity>(Pair(MainActivity.MAIN_TAB_POSITION, 2))
            dismiss()
        }
        view.tv_withdraw.setOnClickListener {
            //跳转到提现页面
            activity.startActivity<MainActivity>(Pair(MainActivity.MAIN_TAB_POSITION, 2),
                    Pair(MainActivity.FUNDING_TAB_POSITION, 1))
            dismiss()
        }
        view.tv_bet_record.setOnClickListener {
            //跳转到交易记录页面
            activity.startActivity<MainActivity>(Pair(MainActivity.MAIN_TAB_POSITION, 2),
                    Pair(MainActivity.FUNDING_TAB_POSITION, 2))
            dismiss()
        }
        view.tv_personal_center.setOnClickListener {
            //跳转到个人中心页面
            activity.startActivity<MainActivity>(Pair(MainActivity.MAIN_TAB_POSITION, 4))
            dismiss()
        }
        view.tv_game_rule.setOnClickListener {
            //跳转到游戏规则页面(WebView)
            val sessionId = AccountHelper.instance.getSessionId()
            val url = Constant.GAME_RULE_WEBSITE + "&oid=$sessionId"
            activity.startActivity<WebViewActivity>(Pair(WebViewActivity.EXTRA_WEB_TITLE, "游戏规则"),
                    Pair(WebViewActivity.EXTRA_WEB_URL, url))
            dismiss()
        }
        view.tv_service.setOnClickListener {
            //跳转到客服页面(WebView)
            val url = SPUtils.instance.getString(Constant.SERVICE_URL, "")
            activity.startActivity<WebViewActivity>(Pair(WebViewActivity.EXTRA_WEB_TITLE, "在线客服"),
                    Pair(WebViewActivity.EXTRA_WEB_URL, url!!))
            dismiss()
        }
        view.tv_log_out.setOnClickListener { userLogout() }
    }

    fun refreshBalance() {
        val user = AccountHelper.instance.getUser()
        if (user != null) {
            mtvBalance.text = user.balance.format()
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