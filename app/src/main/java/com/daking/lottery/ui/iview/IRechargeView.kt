package com.daking.lottery.ui.iview

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.daking.lottery.base.BaseView

interface IRechargeView : BaseView {

    fun showPayWays(data: List<MultiItemEntity>)

    fun onFailure(msg: String)

    fun onComplete()
}
