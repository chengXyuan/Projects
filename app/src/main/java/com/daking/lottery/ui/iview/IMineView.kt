package com.daking.lottery.ui.iview

import com.daking.lottery.base.BaseView
import com.daking.lottery.model.UserModel
import com.daking.lottery.ui.adapter.MineAdapter

interface IMineView : BaseView {

    fun refreshUser(user: UserModel?)

    fun showMineItems(mineAdapter: MineAdapter)
}
