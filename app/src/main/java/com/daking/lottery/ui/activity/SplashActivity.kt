package com.daking.lottery.ui.activity

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.animation.AlphaAnimation
import com.daking.lottery.R
import com.daking.lottery.base.BaseMVPActivity
import com.daking.lottery.dialog.DownloadDialog
import com.daking.lottery.model.VersionModel
import com.daking.lottery.ui.iview.ISplashView
import com.daking.lottery.ui.presenter.SplashPresenter
import com.daking.lottery.util.log
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

    override fun showVersionDialog(model: VersionModel) {
        log("showVersionDialog")
        val builder = AlertDialog.Builder(this)
                .setTitle("应用更新")
                .setMessage("新版本升级v${model.versionName}\n让您享受更流畅的游戏过程！")
                .setPositiveButton("马上更新", { _, _ ->
                    showDownloadDialog(model.versionUrl)
                })
        if (model.forceUpdate == 0) {
            builder.setCancelable(false)
        } else {
            builder.setNegativeButton("以后再说") { dialog, _ ->
                dialog.cancel()
                mPresenter.toNextPage()
            }
        }
        builder.create().show()
    }

    private fun showDownloadDialog(versionUrl: String) {
        DownloadDialog.newInstance(versionUrl)
                .setOnClickLaterListener(object : DownloadDialog.OnClickLaterListener {
                    override fun onLater() {
                        mPresenter.toNextPage()
                    }
                })
                .setMargin(36)
                .setOutCancel(false)
                .show(supportFragmentManager)
    }
}