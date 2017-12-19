package com.daking.lottery.ui.presenter

import com.daking.lottery.base.BasePresenter
import com.daking.lottery.ui.activity.MainActivity
import com.daking.lottery.ui.iview.IModifyPasswordView
import com.daking.lottery.util.isPassword
import com.daking.lottery.util.log
import com.daking.lottery.util.toast
import org.jetbrains.anko.startActivity

class ModifyPasswordPresenter : BasePresenter<IModifyPasswordView>() {

    /**
     * 校验用户输入
     */
    fun commitUpdate(type: Int, oldPassword: String, newPassword: String, ensurePassword: String) {
        when (type) {
            0 -> when {
                !oldPassword.isPassword() -> {
                    toast("请输入6-15位旧密码!")
                }
                !newPassword.isPassword() -> {
                    toast("请输入6-15位新密码!")
                }
                ensurePassword != newPassword -> {
                    toast("两次输入的新密码不一致!")
                }
                else -> updatePassword(oldPassword, newPassword)
            }
            1 -> when {
                oldPassword.length < 4 -> {
                    toast("请输入4位旧的支付密码!")
                }
                newPassword.length < 4 -> {
                    toast("请输入4位新的支付密码!")
                }
                ensurePassword != newPassword -> {
                    toast("两次输入的新密码不一致!")
                }
                else -> updatePayPassword(oldPassword, newPassword)
            }
        }
    }

    /**
     * 修改登录密码
     */
    private fun updatePassword(oldPassword: String, newPassword: String) {
        mNetRepository.updatePassword(oldPassword, newPassword)
                .dealObj({ code, msg, _ ->
                    log("code=$code, msg: $msg")
                    toast("修改成功")
                    mView.getActivity().startActivity<MainActivity>(Pair(MainActivity.MAIN_TAB_POSITION, 3))
                }, { code, msg ->
                    log("code=$code, msg: $msg")
                    toast(msg)
                }, showLoading = true)
    }

    /**
     * 修改支付密码
     */
    private fun updatePayPassword(oldPassword: String, newPassword: String) {
        mNetRepository.updatePayPassword(oldPassword, newPassword)
                .dealObj({ code, msg, _ ->
                    log("code=$code, msg: $msg")
                    toast("修改成功")
                    mView.getActivity().startActivity<MainActivity>(Pair(MainActivity.MAIN_TAB_POSITION, 3))
                }, { code, msg ->
                    log("code=$code, msg: $msg")
                    toast(msg)
                }, showLoading = true)
    }
}