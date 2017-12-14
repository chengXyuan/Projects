package com.daking.lottery.ui.iview

import com.daking.lottery.base.BaseView
import com.daking.lottery.model.BankModel

interface IWithdrawView : BaseView {

    fun showBankcard(model: BankModel)

    fun withdrawSuccess()
}
