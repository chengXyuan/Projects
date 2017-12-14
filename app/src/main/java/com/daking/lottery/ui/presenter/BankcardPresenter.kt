package com.daking.lottery.ui.presenter

import com.daking.lottery.R
import com.daking.lottery.base.BasePresenter
import com.daking.lottery.model.BankModel
import com.daking.lottery.ui.iview.IBankcardView
import com.daking.lottery.util.Utils
import com.daking.lottery.util.log
import com.daking.lottery.util.toast

class BankcardPresenter : BasePresenter<IBankcardView>() {

    fun checkCommit(bankName: String, bankNum: String, bankNum2: String, address: String,
                    oddNum: String, bankModel: BankModel) {
        when {
            oddNum != bankModel.bankAccount -> {
                toast(Utils.getString(R.string.bind_card_tip4))
            }
            else -> {
                checkCommit(bankName, bankNum, bankNum2, address, bankModel.id)
            }
        }
    }

    fun checkCommit(bankName: String, bankNum: String, bankNum2: String, address: String, bankId: Int?) {
        when {
            bankNum.length !in 16..19 -> {
                toast("请输入16-19位银行卡号")
            }
            bankNum2 != bankNum -> {
                toast("两次输入的银行卡号不一致")
            }
            address.isEmpty() -> {
                toast("开户银卡地址不能为空")
            }
            else -> {
                bindOrModifyBankcard(bankId, bankName, bankNum, address)
            }
        }
    }

    private fun bindOrModifyBankcard(id: Int?, bankName: String, bankNum: String, address: String) {
        mNetRepository.bindOrModifyBankcard(id, bankName, bankNum, address)
                .dealObj({ code, msg, _ ->
                    log("code=$code, msg: $msg")
                    toast(msg)
                    mView.bindOrModifySuccess()
                }, { code, msg ->
                    log("code=$code, msg: $msg")
                    toast(msg)
                })
    }
}