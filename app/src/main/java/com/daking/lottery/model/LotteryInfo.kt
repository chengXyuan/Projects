package com.daking.lottery.model

data class LotteryInfo(val next: NextModel?, val last: LastModel?)

data class NextModel(val round: String,//期数
                     val endTime: Long,//开奖时间
                     val closeTime: Long,//封盘时间
                     val timestamp: Long,//当前服务器时间
                     val isClose: Int//是否封盘  1封盘 0未封盘
)

data class LastModel(val round: String,//期数
                     val number: List<String>//开奖号码
)
