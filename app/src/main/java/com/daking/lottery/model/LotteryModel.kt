package com.daking.lottery.model

import com.daking.lottery.app.Constant
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
                        val isClose: Int) {

    fun getGameName(): String {
        return when (gameCode) {
            Constant.GAME_CODE_PJ_FUNNY_8//北京快乐8
            -> "北京快乐8"
            Constant.GAME_CODE_GD_5_IN_11//广东11选5
            -> "广东11选5"
            Constant.GAME_CODE_PJ_PK_10//北京赛车
            -> "北京PK拾"
            Constant.GAME_CODE_CJ_LOTTERY//重庆时时彩
            -> "重庆时时彩"
            Constant.GAME_CODE_LUCKY_AIRSHIP//幸运飞艇
            -> "幸运飞艇"
            Constant.GAME_CODE_LUCKY_28//PC蛋蛋
            -> "PC蛋蛋"
            Constant.GAME_CODE_GD_HAPPY_10//广东快乐十分
            -> "广东快乐十分"
            Constant.GAME_CODE_CJ_LUCKY_FARM//重庆幸运农场
            -> "重庆幸运农场"
            Constant.GAME_CODE_HK_MARK_SIX//香港六合彩
            -> "香港六合彩"
            Constant.GAME_CODE_JS_FAST_3//江苏快三
            -> "江苏快三"
            else -> ""
        }
    }
}