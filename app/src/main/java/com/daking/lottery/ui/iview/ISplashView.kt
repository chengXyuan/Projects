package com.daking.lottery.ui.iview

import com.daking.lottery.base.BaseView
import com.daking.lottery.model.VersionModel

interface ISplashView : BaseView {

    fun toMainPage()

    fun toLoginPage()

    fun showVersionDialog(model: VersionModel)
}
