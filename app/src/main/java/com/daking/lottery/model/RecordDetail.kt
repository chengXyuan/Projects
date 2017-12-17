package com.daking.lottery.model

import com.google.gson.annotations.SerializedName

data class RecordDetail(val id: String,//注单号
                        @SerializedName("lotterygamesId") val lotteryGamesId: String,//游戏编号
                        val round: String,//期数
                        val money: Float,//下注金额
                        val moneyAfter: Float,//注后余额
                        val trueWin: Float,//真实输赢(仅已结注单有该字段)
                        val win: Float,//可赢金额(仅未结注单有该字段)
                        val userCut: Float,//退水金额
                        val totalMonty: Float,//下注总额
                        val rate: Float,//赔率
                        val countPay: Int,//下注组数
                        val createdTime: Long,//时间戳
                        val games: String,//彩种名
                        var gamesPlay1: String = "",//彩种下玩法
                        var gamesPlay2: String = "",//gamesPlay1下玩法
                        var gamesPlay3: String = "",//gamesPlay2下玩法
                        var gamesPlay4: String = "",//gamesPlay3下玩法
                        val path: String,//游戏路径
                        val smallBall: String//组合（复式）的游戏名称
)
