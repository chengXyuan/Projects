package com.daking.lottery.ui.iview

import com.daking.lottery.base.BaseView
import com.daking.lottery.model.MsgModel

interface IMyMessageView : BaseView {

    fun showMessages(data: List<MsgModel>?)

    fun showError(msg: String)

    fun onComplete()
}
