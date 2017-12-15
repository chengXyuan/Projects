package com.daking.lottery.ui.activity

import android.os.Bundle
import android.text.Editable
import android.view.View
import com.bumptech.glide.Glide
import com.daking.lottery.R
import com.daking.lottery.base.BaseMVPActivity
import com.daking.lottery.model.OfflinePayChannel
import com.daking.lottery.model.OfflinePayModel
import com.daking.lottery.ui.iview.IOfflinePaymentView
import com.daking.lottery.ui.presenter.OfflinePaymentPresenter
import com.daking.lottery.widget.MyTextWatcher
import kotlinx.android.synthetic.main.activity_offline_payment.*
import java.text.SimpleDateFormat
import java.util.*

class OfflinePaymentActivity : BaseMVPActivity<OfflinePaymentPresenter>(), IOfflinePaymentView {

    private lateinit var payModel: OfflinePayModel
    private lateinit var payChannel: OfflinePayChannel

    companion object {
        val PAY_IN_DATA = "pay_in_data"
    }

    override fun getLayoutResId() = R.layout.activity_offline_payment

    override fun initData(savedInstanceState: Bundle?) {
        payModel = intent.getSerializableExtra(PAY_IN_DATA) as OfflinePayModel
        payChannel = payModel.channel[0]
        titleBar.setTitleMainText(payModel.getPayName())
        initCurrentDateTime()
        initListener()
        showPayeeInfo()
    }

    private fun initCurrentDateTime() {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val dateTime = format.format(Date(System.currentTimeMillis()))
        tvDate.text = dateTime.split(" ".toRegex())[0]
        tvTime.text = dateTime.split(" ".toRegex())[1]
    }

    private fun initListener() {
        etRechargeAmount.hint = "请输入充值金额，最低${payChannel.minimumAmount}元"
        etRechargeAmount.addTextChangedListener(object : MyTextWatcher() {
            override fun afterTextChanged(e: Editable) {
                if (e.isNotEmpty()) {
                    val s = e.toString()
                    if (s.length == 1 && ("0" == s || "." == s)) {
                        e.clear()
                        return
                    }
                    if (s.toFloat() > 100000) {
                        etRechargeAmount.setText(100000.toString())
                        etRechargeAmount.setSelection(etRechargeAmount.length())
                    }
                    //限制小数点后只能输入两位
                    val posDot = s.indexOf(".")
                    if (posDot <= 0)
                        return
                    if (s.length - posDot - 1 > 2) {
                        e.delete(posDot + 3, posDot + 4)
                    }
                }
            }
        })
        titleBar.setOnLeftTextClickListener { onBackPressed() }
        tvDate.setOnClickListener { }
        tvTime.setOnClickListener { }
        btnConfirm.setOnClickListener {
            val realName = etRealName.text.trim().toString()
            val amount = etRechargeAmount.text.trim().toString()
            val date = tvDate.text.trim().toString()
            val time = tvTime.text.trim().toString()
            val orderNo = etOrderNo.text.trim().toString()
            with(payChannel) {
                mPresenter.commitOfflinePay(realName, amount, minimumAmount,
                        "$date $time:00", orderNo, payTypeId, address, code, rechargeOffer)
            }
        }
    }

    private fun showPayeeInfo() {
        tvPayMethod.text = payModel.getPayName().replace("转账", "收款信息")
        tvBankAddress.text = payChannel.address
        tvPayeeName.text = payChannel.user
        tvBankNum.text = payChannel.code
        val url = payChannel.imageUrl
        if (url.isNullOrEmpty() || payChannel.payTypeId == 5) {
            ivQrCode.visibility = View.GONE
        } else {
            ivQrCode.visibility = View.VISIBLE
            Glide.with(this)
                    .load(url)
                    .into(ivQrCode)
        }
    }

    override fun paySuccess() {
        finish()
    }
}