package com.daking.lottery.util

import com.daking.lottery.R
import com.daking.lottery.app.Constant

class LotteryUtils private constructor() {

    private object Holder {
        val INSTANCE = LotteryUtils()
    }

    companion object {
        val instance = Holder.INSTANCE
    }

    fun getGameName(gameCode: Int): String {
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

    fun getLotteryNumBg(gameCode: Int, number: String): Int {
        return when (gameCode) {
            Constant.GAME_CODE_PJ_PK_10,
            Constant.GAME_CODE_LUCKY_AIRSHIP -> {
                when (number.toInt()) {
                    1 -> R.drawable.img_pk10_1
                    2 -> R.drawable.img_pk10_2
                    3 -> R.drawable.img_pk10_3
                    4 -> R.drawable.img_pk10_4
                    5 -> R.drawable.img_pk10_5
                    6 -> R.drawable.img_pk10_6
                    7 -> R.drawable.img_pk10_7
                    8 -> R.drawable.img_pk10_8
                    9 -> R.drawable.img_pk10_9
                    else -> R.drawable.img_pk10_10
                }
            }
            Constant.GAME_CODE_HK_MARK_SIX -> {
                when (number.toInt()) {
                    1, 2, 7, 8, 12, 13, 18, 19, 23, 24, 29, 30,
                    34, 35, 40, 45, 46 -> R.drawable.img_mark_six_bg_red
                    3, 4, 9, 10, 14, 15, 20, 25, 26, 31, 36, 37,
                    41, 42, 47, 48 -> R.drawable.img_mark_six_bg_blue
                    else -> R.drawable.img_mark_six_bg_green
                }
            }
            Constant.GAME_CODE_GD_HAPPY_10 -> {
                when (number.toInt()) {
                    19, 20 -> R.drawable.shape_dot_red
                    else -> R.drawable.shape_dot_blue
                }
            }
            Constant.GAME_CODE_JS_FAST_3 -> {
                when (number.toInt()) {
                    1, 2 -> R.drawable.shape_dot_red
                    3, 4 -> R.drawable.shape_dot_blue
                    else -> R.drawable.shape_dot_green
                }
            }
            Constant.GAME_CODE_CJ_LUCKY_FARM -> {
                when (number.toInt()) {
                    1 -> R.drawable.img_lucky_farm_1
                    2 -> R.drawable.img_lucky_farm_2
                    3 -> R.drawable.img_lucky_farm_3
                    4 -> R.drawable.img_lucky_farm_4
                    5 -> R.drawable.img_lucky_farm_5
                    6 -> R.drawable.img_lucky_farm_6
                    7 -> R.drawable.img_lucky_farm_7
                    8 -> R.drawable.img_lucky_farm_8
                    9 -> R.drawable.img_lucky_farm_9
                    10 -> R.drawable.img_lucky_farm_10
                    11 -> R.drawable.img_lucky_farm_11
                    12 -> R.drawable.img_lucky_farm_12
                    13 -> R.drawable.img_lucky_farm_13
                    14 -> R.drawable.img_lucky_farm_14
                    15 -> R.drawable.img_lucky_farm_15
                    16 -> R.drawable.img_lucky_farm_16
                    17 -> R.drawable.img_lucky_farm_17
                    18 -> R.drawable.img_lucky_farm_18
                    19 -> R.drawable.img_lucky_farm_19
                    else -> R.drawable.img_lucky_farm_20
                }
            }
            else -> R.drawable.shape_dot_blue
        }
    }

    fun getPCSumBg(sum: Int): Int {
        return when (sum) {
            3, 6, 9, 12, 15, 18, 21, 24 -> R.drawable.shape_dot_red
            1, 4, 7, 10, 16, 19, 22, 25 -> R.drawable.shape_dot_green
            2, 5, 8, 11, 17, 20, 23, 26 -> R.drawable.shape_dot_blue
            else -> R.drawable.shape_dot_grey
        }
    }

    /**
     * 获取六合彩生肖属性
     */
    fun getZodiac(number: Int) = when (number % 12) {
        10 -> "鼠"
        9 -> "牛"
        8 -> "虎"
        7 -> "兔"
        6 -> "龙"
        5 -> "蛇"
        4 -> "马"
        3 -> "羊"
        2 -> "猴"
        1 -> "鸡"
        0 -> "狗"
        else -> "猪"
    }

    fun getYuXiaXie(number: Int) = when (number) {
        1 -> "鱼"
        2 -> "虾"
        3 -> "葫芦"
        4 -> "金钱"
        5 -> "蟹"
        6 -> "鸡"
        else -> ""
    }

}
