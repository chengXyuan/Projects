package com.daking.lottery.ui.presenter

import com.daking.lottery.base.BasePresenter
import com.daking.lottery.model.BankModel
import com.daking.lottery.ui.iview.IWithdrawView
import com.daking.lottery.util.AccountHelper
import com.daking.lottery.util.log
import com.daking.lottery.util.toast

class WithdrawPresenter : BasePresenter<IWithdrawView>() {

    private var balance: Float = 0f

    override fun onAttached() {
        super.onAttached()
        initUser()
        getBankcard()
    }

    private fun initUser() {
        val user = AccountHelper.instance.getUser()
        if (user != null) {
            balance = user.balance
        }
        //刷新一下
        refreshAccountInfo()
    }

    private fun refreshAccountInfo() {
        AccountHelper.instance.refreshAccount(mView, { user ->
            if (user != null) {
                balance = user.balance
            }
        })
    }

    fun getBankcard() {
        mNetRepository.getBankcard()
                .dealObj({ code, msg, model ->
                    log("code=$code, msg: $msg")
                    model?.let { mView.showBankcard(model) }
                })
    }

    fun commitWithdraw(bankModel: BankModel?, amount: String, payPassword: String) {
        when {
            bankModel == null -> {
                toast("请先绑定银行卡")
            }
            amount.isEmpty() -> {
                toast("请输入提现金额")
            }
            amount.toInt() > balance -> {
                toast("余额不足!")
            }
            amount.toInt() < 100 -> {
                toast("提现金额不能低于100元")
            }
            payPassword.length != 4 -> {
                toast("请输入4位提现密码")
            }
            else -> {
                withdraw(bankModel.bankAccount, amount, payPassword)
            }
        }
    }

    private fun withdraw(bankNum: String, amount: String, payPassword: String) {
        mNetRepository.withdraw(bankNum, amount, payPassword)
                .dealObj({ code, msg, model ->
                    log("code=$code, msg: $msg")
                    toast(msg)
                    mView.withdrawSuccess()
                }, { code, msg ->
                    log("code=$code, msg: $msg")
                    toast(msg)
                })
    }
}