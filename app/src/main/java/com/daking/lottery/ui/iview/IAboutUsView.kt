package com.daking.lottery.ui.iview

import com.daking.lottery.base.BaseView
import com.daking.lottery.model.VersionModel

interface IAboutUsView : BaseView {

    fun getVersionSuccess(model: VersionModel)
}
