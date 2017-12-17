package com.daking.lottery.model

import com.google.gson.annotations.SerializedName

data class RecordSummary(val id: String,//投注单号
                         @SerializedName("lotterygamesId") val lotteryGamesId: String,
                         val money: Float,
                         val total: Float,
                         val win: Float,
                         val backwater: Float,
                         val sum: Float,
                         val status: Int,//0-未结算 1-已结算
                         val createdTime: Long,
                         val path: String)