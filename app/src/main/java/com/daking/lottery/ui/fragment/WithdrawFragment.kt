package com.daking.lottery.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.view.View
import com.daking.lottery.R
import com.daking.lottery.base.BaseMVPFragment
import com.daking.lottery.event.BankcardChangeEvent
import com.daking.lottery.event.RechargeOrWithdrawEvent
import com.daking.lottery.model.BankModel
import com.daking.lottery.repository.LocalRepository
import com.daking.lottery.ui.activity.BankcardActivity
import com.daking.lottery.ui.iview.IWithdrawView
import com.daking.lottery.ui.presenter.WithdrawPresenter
import com.daking.lottery.util.FormatUtils
import com.daking.lottery.util.Utils
import com.daking.lottery.widget.MyTextWatcher
import com.daking.lottery.widget.OnMultiClickListener
import kotlinx.android.synthetic.main.fragment_withdraw.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.startActivity

class WithdrawFragment : BaseMVPFragment<WithdrawPresenter>(), IWithdrawView {

    private var bankModel: BankModel? = null

    override fun getLayoutResId() = R.layout.fragment_withdraw

    override fun initData(savedInstanceState: Bundle?) {
        rlAddBankcard.setOnClickListener {
            context.startActivity<BankcardActivity>(Pair(BankcardActivity.EXTRA_IS_MODIFY, false))
        }
        rlBankInfo.setOnClickListener {
            context.startActivity<BankcardActivity>(Pair(BankcardActivity.EXTRA_IS_MODIFY, true),
                    Pair(BankcardActivity.EXTRA_ODD_BANK, bankModel!!))
        }
        btnReset.setOnClickListener {
            etAmount.setText("")
            etPayPassword.setText("")
        }
        btnConfirm.setOnClickListener(object : OnMultiClickListener() {
            override fun noMultiClick(view: View) {
                val amount = etAmount.text.trim().toString()
                val payPassword = etPayPassword.text.trim().toString()
                mPresenter.commitWithdraw(bankModel, amount, payPassword)
            }
        })
        //金额首位不能输入0
        etAmount.addTextChangedListener(object : MyTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                if (s.length == 1 && s.toString() == "0") {
                    s.clear()
                }
            }
        })
        //支付密码输入满4位自动收起键盘
        etPayPassword.addTextChangedListener(object : MyTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                if (s.length >= 4) {
                    Utils.hideKeyboard(etPayPassword)
                }
            }
        })
    }

    override fun showBankcard(model: BankModel) {
        bankModel = model
        rlBankInfo.visibility = View.VISIBLE
        rlAddBankcard.visibility = View.GONE
        tvBankName.text = model.bankName
        tvBankcardNum.text = FormatUtils.instance.formatBankNum(model.bankAccount)
        ivBankLogo.setImageResource(LocalRepository.instance.getBankLogoResByName(model.bankName))
    }

    override fun withdrawSuccess() {
        etAmount.setText("")
        etPayPassword.setText("")
        //充值成功后发送一个成功的事件
        EventBus.getDefault().post(RechargeOrWithdrawEvent())
    }

    override fun useEventBus() = true

    @Subscribe
    fun onEvent(event: BankcardChangeEvent) {
        mPresenter.getBankcard()
    }

}
