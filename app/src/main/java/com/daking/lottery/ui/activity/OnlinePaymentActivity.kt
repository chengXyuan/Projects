package com.daking.lottery.ui.activity

import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.view.View
import com.daking.lottery.R
import com.daking.lottery.base.BaseMVPActivity
import com.daking.lottery.model.OnlinePayChannel
import com.daking.lottery.model.OnlinePayModel
import com.daking.lottery.ui.adapter.AmountGridAdapter
import com.daking.lottery.ui.adapter.ChannelGridAdapter
import com.daking.lottery.ui.iview.IOnlinePaymentView
import com.daking.lottery.ui.presenter.OnlinePaymentPresenter
import com.daking.lottery.util.Utils
import com.daking.lottery.widget.MyTextWatcher
import kotlinx.android.synthetic.main.activity_online_payment.*
import org.jetbrains.anko.startActivity
import java.util.*

class OnlinePaymentActivity : BaseMVPActivity<OnlinePaymentPresenter>(), IOnlinePaymentView {

    private lateinit var payModel: OnlinePayModel
    private lateinit var payChannel: OnlinePayChannel
    private lateinit var amountAdapter: AmountGridAdapter

    companion object {
        val PAY_IN_DATA = "pay_in_data"
    }

    override fun getLayoutResId() = R.layout.activity_online_payment

    override fun initData(savedInstanceState: Bundle?) {
        payModel = intent.getSerializableExtra(PAY_IN_DATA) as OnlinePayModel
        payChannel = payModel.channel[0]
        titleBar.setTitleMainText(payModel.getPayName())
        ivLogo.setImageResource(payModel.getLargeLogoRes())
        initListener()
        setMoneyLimit()
        if (payModel.channel.size > 1) {
            gvChannels.visibility = View.GONE
            tvChannelHint.visibility = View.GONE
            initChannelGridView()
        } else {
            gvChannels.visibility = View.GONE
            tvChannelHint.visibility = View.GONE
        }
        initAmountGridView()
    }

    private fun initListener() {
        titleBar.setOnLeftTextClickListener { onBackPressed() }
        btnReset.setOnClickListener {
            gvAmounts.clearChoices()
            amountAdapter.notifyDataSetChanged()
            etRechargeAmount.setText("")
        }
        btnConfirm.setOnClickListener {
            Utils.hideKeyboard(etRechargeAmount)
            val amount = etRechargeAmount.text.toString()
            with(payChannel) {
                mPresenter.commitRecharge(amount, minimumAmount, maximumAmount, businessCode,
                        domain, notifyUrl, remark, paymentClass, rechargeOffer, payTypeId, name)
            }
        }
        etRechargeAmount.addTextChangedListener(object : MyTextWatcher() {
            override fun afterTextChanged(e: Editable) {
                if (e.isNotEmpty()) {
                    val s = e.toString()
                    if (e.length == 1 && ("0" == s || "." == s)) {
                        e.clear()
                        return
                    }
                    val max = payChannel.maximumAmount
                    if (s.toFloat() > max) {
                        etRechargeAmount.setText(max.toString())
                        etRechargeAmount.setSelection(max.toString().length)
                    }
                }
            }
        })
    }

    /**
     * 显示充值的金额限制
     */
    private fun setMoneyLimit() {
        val text = "单笔下限<font color='#2F64D4'>${payChannel.minimumAmount}</font>，单笔上限<font color='#2F64D4'>${payChannel.maximumAmount}</font>"
        tvMoneyLimit.text = Html.fromHtml(text)
    }

    /**
     * 支付通道
     */
    private fun initChannelGridView() {
        val adapter = ChannelGridAdapter(this, payModel.channel)
        gvChannels.adapter = adapter
        gvChannels.setItemChecked(0, true)
        gvChannels.setOnItemClickListener { _, _, position, _ ->
            payChannel = payModel.channel[position]
            setMoneyLimit()
        }
    }

    /**
     * 支付金额选项
     */
    private fun initAmountGridView() {
        val amounts = arrayOf(50, 100, 300, 500, 800, 1000, 2000, 3000).map {
            (it + Random().nextInt(5) + 1).toString()
        }
        amountAdapter = AmountGridAdapter(this, amounts)
        gvAmounts.adapter = amountAdapter
        gvAmounts.setOnItemClickListener { _, _, position, _ ->
            etRechargeAmount.setText(amounts[position])
            etRechargeAmount.setSelection(etRechargeAmount.length())
        }
    }

    override fun getOnlinePayUrl(url: String) {
        startActivity<WebViewActivity>(Pair(WebViewActivity.EXTRA_WEB_TITLE, "在线支付"),
                Pair(WebViewActivity.EXTRA_WEB_URL, url))
        finish()
    }
}