package com.daking.lottery.model

import android.support.annotation.DrawableRes

data class BankRes(val bankName: String,
                   val shortName: String,
                   @DrawableRes val logoRes: Int)
