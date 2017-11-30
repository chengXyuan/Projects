package com.daking.lottery.ui.activity

import android.os.Bundle
import android.view.animation.AlphaAnimation
import com.daking.lottery.R
import com.daking.lottery.base.BaseMVPActivity
import com.daking.lottery.ui.iview.ISplashView
import com.daking.lottery.ui.presenter.SplashPresenter
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity

class SplashActivity : BaseMVPActivity<SplashPresenter>(), ISplashView {

    override fun getLayoutResId() = R.layout.activity_splash

    override fun initData(savedInstanceState: Bundle?) {
        //启动图渐变动画
        val alphaAnim = AlphaAnimation(0.3f, 1.0f)
        alphaAnim.duration = 2000
        iv_splash.startAnimation(alphaAnim)
    }

    override fun toMainPage() {
        startActivity<MainActivity>()
        finish()
    }

    override fun toLoginPage() {
        startActivity<LoginActivity>()
        finish()
    }
}