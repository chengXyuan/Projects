package com.daking.lottery.model

import com.google.gson.annotations.SerializedName

/**
 * {
 * "isNewRecord": true,
 * "lotterygamesId": "1",
 * "round": "859782",
 * "number": "09,12,17,23,27,36,42,50,54,58,62,64,66,68,71,73,76,78,79,80",
 * "openTime": 1512392401,
 * "sysTime": 1512392164,
 * "isClose": 0
 * }
 */
data class LotteryModel(@SerializedName("lotterygamesId") val gameCode: Int,
                        val round: String,
                        val number: String,
                        val openTime: Long?,
                        val sysTime: Long?,
                        val isClose: Int)