package com.daking.lottery.model

import android.support.annotation.DrawableRes

data class GameModel(val gameCode: Int,
                     val gameName: String,
                     @DrawableRes val logoResId: Int)