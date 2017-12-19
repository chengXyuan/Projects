package com.daking.lottery.ui.activity

import android.os.Bundle
import com.daking.lottery.R
import com.daking.lottery.app.Constant
import com.daking.lottery.base.BaseActivity
import com.daking.lottery.model.MsgModel
import com.daking.lottery.util.FormatUtils
import kotlinx.android.synthetic.main.activity_message_detail.*

class MessageDetailActivity : BaseActivity() {

    companion object {
        val MSG_MODEL = "msg_model"
    }

    override fun getLayoutResId() = R.layout.activity_message_detail

    override fun initData(savedInstanceState: Bundle?) {
        titleBar.setOnLeftTextClickListener { onBackPressed() }
        val model = intent.getSerializableExtra(MSG_MODEL) as MsgModel
        tvDate.text = FormatUtils.instance.convertDate(model.updateTime * 1000)
        tvWelcome.text = Constant.WELCOME
        tvContent.text = model.content
    }
}
