package com.daking.lottery.ui.activity

import android.os.Bundle
import com.daking.lottery.R
import com.daking.lottery.base.BaseMVPActivity
import com.daking.lottery.model.VersionModel
import com.daking.lottery.ui.iview.IAboutUsView
import com.daking.lottery.ui.presenter.AboutUsPresenter
import com.daking.lottery.util.Utils
import kotlinx.android.synthetic.main.activity_about_us.*

class AboutUsActivity : BaseMVPActivity<AboutUsPresenter>(), IAboutUsView {

    override fun getLayoutResId() = R.layout.activity_about_us

    override fun initData(savedInstanceState: Bundle?) {
        tvCurrentVersion.text = String.format("当前版本%s", Utils.getVersionName(this))
        titleBar.setOnLeftTextClickListener { finish() }
    }

    override fun getVersionSuccess(model: VersionModel) {
        tvLastVersion.text = String.format("最新版本%s", model.versionName)
    }
}