package com.daking.lottery.ui.activity

import android.os.Bundle
import android.view.View
import com.daking.lottery.R
import com.daking.lottery.base.BaseMVPActivity
import com.daking.lottery.event.BankcardChangeEvent
import com.daking.lottery.model.BankModel
import com.daking.lottery.repository.LocalRepository
import com.daking.lottery.ui.iview.IBankcardView
import com.daking.lottery.ui.presenter.BankcardPresenter
import com.daking.lottery.widget.OnMultiClickListener
import kotlinx.android.synthetic.main.activity_bankcard.*
import org.greenrobot.eventbus.EventBus

class BankcardActivity : BaseMVPActivity<BankcardPresenter>(), IBankcardView {

    private var bankModel: BankModel? = null

    companion object {
        val EXTRA_IS_MODIFY = "extra_is_modify"
        val EXTRA_ODD_BANK = "extra_odd_bank"
    }

    private var isModify = false

    override fun getLayoutResId() = R.layout.activity_bankcard

    override fun initData(savedInstanceState: Bundle?) {
        isModify = intent.getBooleanExtra(EXTRA_IS_MODIFY, false)
        titleBar.setTitleMainText(if (isModify) "修改银行卡" else "绑定银行卡")
        if (isModify) {
            bankModel = intent.getSerializableExtra(EXTRA_ODD_BANK) as BankModel
            tvOddBankTips.visibility = View.VISIBLE
            llOddBankName.visibility = View.VISIBLE
            llOddBankNum.visibility = View.VISIBLE
            tvOddBankName.text = bankModel?.bankName
        } else {
            tvOddBankTips.visibility = View.GONE
            llOddBankName.visibility = View.GONE
            llOddBankNum.visibility = View.GONE
        }
        tvSelectedBank.text = LocalRepository.instance.getBankcardSupported()[0].bankName
        initListener()
    }

    /**
     * View的事件监听
     */
    private fun initListener() {
        titleBar.setOnLeftTextClickListener { onBackPressed() }
        llSelectBank.setOnClickListener { }
        btnCommit.setOnClickListener(object : OnMultiClickListener() {
            override fun noMultiClick(view: View) {
                val bankName = tvSelectedBank.text.trim().toString()
                val bankNum = etBankNum.bankNum
                val bankNum2 = etBankNumAgain.bankNum
                val address = etRegisteredAddress.text.trim().toString()
                if (isModify) {
                    val oddNum = etOddBankNum.bankNum
                    mPresenter.checkCommit(bankName, bankNum, bankNum2, address, oddNum, bankModel!!)
                } else {
                    mPresenter.checkCommit(bankName, bankNum, bankNum2, address, null)
                }
            }
        })
    }

    override fun bindOrModifySuccess() {
        EventBus.getDefault().post(BankcardChangeEvent())
        finish()
    }
}