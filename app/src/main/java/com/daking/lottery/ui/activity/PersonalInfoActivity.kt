package com.daking.lottery.ui.activity

import android.os.Bundle
import com.daking.lottery.R

import com.daking.lottery.base.BaseMVPActivity
import com.daking.lottery.model.UserModel
import com.daking.lottery.ui.iview.IPersonalInfoView
import com.daking.lottery.ui.presenter.PersonalInfoPresenter
import com.daking.lottery.util.format
import kotlinx.android.synthetic.main.activity_personal_info.*
import org.jetbrains.anko.startActivity

class PersonalInfoActivity : BaseMVPActivity<PersonalInfoPresenter>(), IPersonalInfoView {

    override fun getLayoutResId() = R.layout.activity_personal_info

    override fun initData(savedInstanceState: Bundle?) {
        titleBar.setOnLeftTextClickListener { finish() }
        tvRecharge.setOnClickListener {
            startActivity<MainActivity>(Pair(MainActivity.MAIN_TAB_POSITION, 2))
        }
        tvWithdraw.setOnClickListener {
            startActivity<MainActivity>(Pair(MainActivity.MAIN_TAB_POSITION, 2),
                    Pair(MainActivity.FUNDING_TAB_POSITION, 1))
        }
    }

    override fun refreshUser(user: UserModel?) {
        user?.let {
            tvAccount.text = user.username
            tvBalance.text = String.format("￥%s", user.balance.format())
            tvRealName.text = user.realName
            tvEmail.text = user.email
        }
    }
}