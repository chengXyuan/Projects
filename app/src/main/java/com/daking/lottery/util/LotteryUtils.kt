package com.daking.lottery.util

import com.daking.lottery.R
import com.daking.lottery.app.Constant
import com.daking.lottery.model.*
import com.daking.lottery.ui.adapter.BetDataAdapter
import com.daking.lottery.ui.adapter.BetTypeAdapter
import org.jetbrains.anko.collections.forEachWithIndex
import java.util.*

class LotteryUtils private constructor() {

    private object Holder {
        val INSTANCE = LotteryUtils()
    }

    companion object {
        val instance = Holder.INSTANCE
        /**
         * 下注页面RecyclerView总宽度
         */
        val TOTAL_SPAN_SIZE = 60
        /**
         * 下注方式一:适用于 北京赛车，幸运飞艇，PC蛋蛋（幸运二八），重庆时时彩，广东快乐十分（除连码外），
         * 重庆幸运农场（除连码外），香港六合彩（特码，正码，正码特，正码1-6，半波，一肖、尾数，特码生肖）
         */
        val BET_TYPE_ONE = 0x01
        /**
         * 下注方式二:适用于 广东快乐十分（连码），重庆幸运农场（连码），香港六合彩（连码）
         */
        val BET_TYPE_TWO = 0x02
        /**
         * 下注方式三:适用于（过关 ，合肖，生肖连，尾数连，全不中）
         */
        val BET_TYPE_THREE = 0x03
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

    fun getAllOtherGames(gameCode: Int): LinkedHashMap<Int, String> {
        val map = LinkedHashMap<Int, String>()
        map.put(Constant.GAME_CODE_PJ_PK_10, "北京PK拾")
        map.put(Constant.GAME_CODE_CJ_LOTTERY, "重庆时时彩")
        map.put(Constant.GAME_CODE_HK_MARK_SIX, "香港六合彩")
        map.put(Constant.GAME_CODE_LUCKY_AIRSHIP, "幸运飞艇")
        map.put(Constant.GAME_CODE_LUCKY_28, "PC蛋蛋")
        map.put(Constant.GAME_CODE_GD_HAPPY_10, "广东快乐十分")
        map.put(Constant.GAME_CODE_CJ_LUCKY_FARM, "重庆幸运农场")
        map.remove(gameCode)
        return map
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

    fun getTabTitle(gameCode: Int): MutableList<TypeTitle> {
        val list = ArrayList<TypeTitle>()
        when (gameCode) {
            Constant.GAME_CODE_PJ_PK_10 -> {//北京赛车
                list.add(TypeTitle(0, "两面盘"))
                list.add(TypeTitle(1, "冠亚组合"))
                list.add(TypeTitle(2, "一~五名"))
                list.add(TypeTitle(3, "六~十名"))
            }
            Constant.GAME_CODE_CJ_LOTTERY -> {//重庆时时彩
                list.add(TypeTitle(0, "两面盘"))
                list.add(TypeTitle(1, "数字盘"))
                list.add(TypeTitle(2, "前、中、后"))
            }
            Constant.GAME_CODE_LUCKY_AIRSHIP -> {//幸运飞艇
                list.add(TypeTitle(0, "两面盘"))
                list.add(TypeTitle(1, "冠亚组合"))
                list.add(TypeTitle(2, "一~五名"))
                list.add(TypeTitle(3, "六~十名"))
            }
            Constant.GAME_CODE_GD_HAPPY_10 -> {//广东快乐十分
                list.add(TypeTitle(0, "两面盘"))
                list.add(TypeTitle(1, "一~四球"))
                list.add(TypeTitle(2, "五~八球"))
                list.add(TypeTitle(3, "连码"))
            }
            Constant.GAME_CODE_CJ_LUCKY_FARM -> {//重庆幸运农场
                list.add(TypeTitle(0, "两面盘"))
                list.add(TypeTitle(1, "一~四球"))
                list.add(TypeTitle(2, "五~八球"))
                list.add(TypeTitle(3, "连码"))
            }
            Constant.GAME_CODE_HK_MARK_SIX -> {//香港六合彩
                list.add(TypeTitle(0, "特码"))
                list.add(TypeTitle(1, "正码"))
                list.add(TypeTitle(2, "正码特"))
                list.add(TypeTitle(3, "正码1~6"))
                list.add(TypeTitle(4, "连码"))
                list.add(TypeTitle(5, "半波"))
                list.add(TypeTitle(6, "一肖/尾数"))
                list.add(TypeTitle(7, "特码生肖"))
                list.add(TypeTitle(8, "合肖"))
                list.add(TypeTitle(9, "生肖连"))
                list.add(TypeTitle(10, "尾数连"))
                list.add(TypeTitle(11, "全不中"))
            }
            Constant.GAME_CODE_LUCKY_28 -> {//PC蛋蛋
                list.add(TypeTitle(0, "混色和波色"))
                list.add(TypeTitle(1, "特码"))
            }
        }
        return list
    }

    fun getTypeCode(gameCode: Int, position: Int, typeId: Int): String {
        when (gameCode) {
            Constant.GAME_CODE_PJ_PK_10 -> when (position) {//北京赛车
                0 -> return "17"
                1 -> return "18"
                2 -> return "4421"
                3 -> return "4422"
            }
            Constant.GAME_CODE_CJ_LOTTERY -> when (position) {//重庆时时彩
                0 -> return "20"
                1 -> return "21"
                2 -> return "000"
            }
            Constant.GAME_CODE_LUCKY_28 -> when (position) {//PC蛋蛋
                0 -> return "111"
                1 -> return "1143"
            }
            Constant.GAME_CODE_LUCKY_AIRSHIP -> when (position) {//幸运飞艇
                0 -> return "27"
                1 -> return "28"
                2 -> return "4423"
                3 -> return "4424"
            }
            Constant.GAME_CODE_GD_HAPPY_10 -> when (position) {//广东快乐十分
                0 -> return "31"
                1 -> return "4425"
                2 -> return "4426"
                3 -> return "41"
            }
            Constant.GAME_CODE_CJ_LUCKY_FARM -> when (position) {//重庆幸运农场
                0 -> return "42"
                1 -> return "4427"
                2 -> return "4428"
                3 -> return "52"
            }
            Constant.GAME_CODE_HK_MARK_SIX -> when (position) {//香港六合彩
                0 -> when (typeId) {//特码
                    0//特B
                    -> return "2251"
                    1//特A
                    -> return "2250"
                }
                1 -> when (typeId) {//正码
                    0//正A
                    -> return "2383"
                    1//正B
                    -> return "2384"
                }
                2 -> when (typeId) {//正特码
                    0//正1特
                    -> return "2490"
                    1//正2特
                    -> return "2491"
                    2//正3特
                    -> return "2492"
                    3//正4特
                    -> return "2493"
                    4//正5特
                    -> return "2494"
                    5//正6特
                    -> return "2495"
                }
                3//正码1-6
                -> return "56"
                4//连码
                -> return "58"
                5//半波
                -> return "59"
                6//一肖/尾数
                -> return "60"
                7//特码生肖
                -> return "61"
                8 -> {//合肖
                    when (typeId) {
                        0//二肖
                        -> return "3040"
                        1//三肖
                        -> return "3041"
                        2//四肖
                        -> return "3042"
                        3//五肖
                        -> return "3043"
                        4//六肖
                        -> return "3044"
                    }
                }
                9 -> {//生肖连
                    when (typeId) {
                        0 -> return "3105"
                        1 -> return "3106"
                        2 -> return "3107"
                        3 -> return "3108"
                        4 -> return "3109"
                        5 -> return "3110"
                        6 -> return "3111"
                    }
                }
                10 -> when (typeId) {//尾数连
                    0 -> return "3196"
                    1 -> return "3197"
                    2 -> return "3198"
                    3 -> return "3199"
                    4 -> return "3200"
                    5 -> return "3201"
                }
                11 -> when (typeId) {//全不中
                    0//五不中
                    -> return "3274"
                    1//六不中
                    -> return "3275"
                    2//七不中
                    -> return "3276"
                    3//八不中
                    -> return "3277"
                    4//九不中
                    -> return "3278"
                    5//十不中
                    -> return "3279"
                    6//十一不中
                    -> return "3280"
                    7//十二不中
                    -> return "3281"
                }
            }
        }
        return ""
    }

    fun getTypeData(gameCode: Int, position: Int): List<BetTypeItem> {
        val list = ArrayList<BetTypeItem>()
        when (gameCode) {
            Constant.GAME_CODE_GD_HAPPY_10,
            Constant.GAME_CODE_CJ_LOTTERY -> {//北京赛车//幸运飞艇
                if (position == 3) {
                    val names = arrayOf("任选二", "选二直连", "选二连组", "任选二",
                            "选三前直", "选三连组", "任选四", "任选五")
                    names.forEachWithIndex { i, it ->
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_COMBO,
                                TOTAL_SPAN_SIZE / 4, it, "封盘", selected = i == 0))
                    }
                }
            }
            Constant.GAME_CODE_HK_MARK_SIX -> {//香港六合彩

                when (position) {
                    0 -> {//特码
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 2, "特B", selected = true))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 2, "特A"))
                    }
                    1 -> {//正码
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 2, "正A", selected = true))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 2, "正B"))
                    }
                    2 -> {//正特码
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 3, "正1特", selected = true))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 3, "正2特"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 3, "正3特"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 3, "正4特"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 3, "正5特"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 3, "正6特"))
                    }
                    4 -> {//连码
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_COMBO,
                                TOTAL_SPAN_SIZE / 4, "三全中", "封盘", selected = true))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_COMBO,
                                TOTAL_SPAN_SIZE / 4, "中二", "封盘"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_COMBO,
                                TOTAL_SPAN_SIZE / 4, "中三", "封盘"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_COMBO,
                                TOTAL_SPAN_SIZE / 4, "二全中", "封盘"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_COMBO,
                                TOTAL_SPAN_SIZE / 4, "中特", "封盘"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_COMBO,
                                TOTAL_SPAN_SIZE / 4, "中二", "封盘"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_COMBO,
                                TOTAL_SPAN_SIZE / 4, "四中一", "封盘"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_COMBO,
                                TOTAL_SPAN_SIZE / 4, "特串", "封盘"))
                    }
                    8 -> {//合肖
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 5, "二肖", selected = true))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 5, "三肖"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 5, "四肖"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 5, "五肖"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 5, "六肖"))
                    }
                    9 -> {//生肖连
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 4, "二肖连中", selected = true))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 4, "三肖连中"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 4, "四肖连中"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 4, "五肖连中"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 3, "二肖不连中"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 3, "三肖不连中"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 3, "四肖不连中"))
                    }
                    10 -> {
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 3, "二尾连中", selected = true))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 3, "三尾连中"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 3, "四尾连中"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 3, "二尾不连中"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 3, "三尾不连中"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 3, "四尾不连中"))
                    }
                    11 -> {
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 4, "五全不中", selected = true))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 4, "六全不中"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 4, "七全不中"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 4, "八全不中"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 4, "九全不中"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 4, "十全不中"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 4, "十一全不中"))
                        list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_NORMAL,
                                TOTAL_SPAN_SIZE / 4, "十二全不中"))
                    }
                }
            }
        }
        return list
    }

    fun reassembleTypeData(gameCode: Int, oddsList: List<OddsItem>): List<BetTypeItem> {
        val list = ArrayList<BetTypeItem>()
        oddsList.forEachWithIndex { i, item ->
            list.add(BetTypeItem(gameCode, BetTypeAdapter.TYPE_COMBO, TOTAL_SPAN_SIZE / 4,
                    item.name, item.odds, item.key, item.backwater, selected = i == 0))
        }
        return list
    }

    fun getBetDataLian(gameCode: Int, typeCode: String): List<MultiBetItem> {
        val list = ArrayList<MultiBetItem>()
        when (gameCode) {
            Constant.GAME_CODE_GD_HAPPY_10,
            Constant.GAME_CODE_CJ_LUCKY_FARM -> {//广东快乐十分//重庆幸运农场
                (1..20).mapTo(list) {
                    MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_LIAN,
                            TOTAL_SPAN_SIZE / 4, "连码", number = it)
                }
            }
            Constant.GAME_CODE_HK_MARK_SIX -> {
                (1..49).mapTo(list) {
                    if (it <= 44) {
                        MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_LIAN,
                                TOTAL_SPAN_SIZE / 4, "连码", number = it)
                    } else {
                        MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_LIAN,
                                TOTAL_SPAN_SIZE / 5, "连码", number = it)
                    }
                }
            }
        }
        return list
    }

    /**
     * 对赔率数据进行重新组装
     */
    fun reassembleBetData(gameCode: Int, position: Int, typeCode: String, oddsList: List<OddsModel>): List<MultiBetItem> {
        val list = ArrayList<MultiBetItem>()
        when (gameCode) {
            Constant.GAME_CODE_PJ_PK_10,
            Constant.GAME_CODE_LUCKY_AIRSHIP -> when (position) {//北京赛车//幸运飞艇
                0 -> {
                    addData2List(gameCode, typeCode, list, oddsList, 5,
                            0, BetDataAdapter.CONTENT_TEXT)
                    addData2List(gameCode, typeCode, list, oddsList, 5,
                            5, BetDataAdapter.CONTENT_TEXT)
                }
                1 -> {
                    oddsList.forEachWithIndex { i, oddsModel ->
                        val (name, _, items) = oddsModel
                        list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.TITLE,
                                TOTAL_SPAN_SIZE, name))
                        items.forEachWithIndex { j, item ->
                            if (i == 0 && j >= 12) {
                                list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_TEXT,
                                        TOTAL_SPAN_SIZE / 5, name, betItem = item))
                            } else {
                                list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_TEXT,
                                        TOTAL_SPAN_SIZE / 4, name, betItem = item))
                            }
                        }
                    }
                }
                2, 3 -> addData2List(gameCode, typeCode, list, oddsList, 5,
                        0, BetDataAdapter.CONTENT_TEXT)
            }
            Constant.GAME_CODE_CJ_LOTTERY -> when (position) {//重庆时时彩
                0 -> {
                    addData2List(gameCode, typeCode, list, oddsList, 5,
                            0, BetDataAdapter.CONTENT_TEXT)
                    list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.TITLE,
                            TOTAL_SPAN_SIZE, oddsList[5].name.replace(" ", "")))
                    oddsList[5].list.forEachWithIndex { i, item ->
                        if (i < 4) {
                            list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_TEXT,
                                    TOTAL_SPAN_SIZE / 4, oddsList[5].name, betItem = item))
                        } else {
                            list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_TEXT,
                                    TOTAL_SPAN_SIZE / 3, oddsList[5].name, betItem = item))
                        }
                    }
                }
                1 -> addData2List(gameCode, typeCode, list, oddsList, 5,
                        0, BetDataAdapter.CONTENT_NUM)
                2 -> addData2List(gameCode, typeCode, list, oddsList, 3,
                        0, BetDataAdapter.CONTENT_TEXT)
            }
            Constant.GAME_CODE_GD_HAPPY_10,
            Constant.GAME_CODE_CJ_LUCKY_FARM -> when (position) {//广东快乐十分//重庆幸运农场
                0 -> {//两面盘
                    list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.TITLE,
                            TOTAL_SPAN_SIZE, oddsList[8].name))
                    oddsList[8].list.forEach {
                        list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_TEXT,
                                TOTAL_SPAN_SIZE / 4, oddsList[0].name, betItem = it))
                    }
                    addData2List(gameCode, typeCode, list, oddsList, 4,
                            0, BetDataAdapter.CONTENT_TEXT)
                    addData2List(gameCode, typeCode, list, oddsList, 4,
                            4, BetDataAdapter.CONTENT_TEXT)
                }
                1, 2 -> {//一~四//五~八
                    addData2List(gameCode, typeCode, list, oddsList, 4,
                            0, BetDataAdapter.CONTENT_TEXT)
                }
                3 -> {//连码
                    (1..20).mapTo(list) {
                        MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_LIAN,
                                TOTAL_SPAN_SIZE / 4, "连码", number = it)
                    }
                }
            }
            Constant.GAME_CODE_HK_MARK_SIX -> when (position) {//香港六合彩
                0, 1 -> {//特码//正码
                    addMarkSixNumber(gameCode, typeCode, list, oddsList[0])
                    list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.TITLE,
                            TOTAL_SPAN_SIZE, oddsList[1].name))
                    oddsList[1].list.forEachWithIndex { i, item ->
                        if (i < 12) {
                            list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_TEXT,
                                    TOTAL_SPAN_SIZE / 4, oddsList[1].name, betItem = item))
                        } else {
                            list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_TEXT,
                                    TOTAL_SPAN_SIZE / 5, oddsList[1].name, betItem = item))
                        }
                    }
                }
                2 -> {//正码特
                    addMarkSixNumber(gameCode, typeCode, list, oddsList[0])
                    list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.TITLE,
                            TOTAL_SPAN_SIZE, oddsList[1].name))
                    oddsList[1].list.forEachWithIndex { i, item ->
                        if (i < 4) {
                            list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_TEXT,
                                    TOTAL_SPAN_SIZE / 4, oddsList[1].name, betItem = item))
                        } else {
                            list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_TEXT,
                                    TOTAL_SPAN_SIZE / 5, oddsList[1].name, betItem = item))
                        }
                    }
                }
                3 -> {//正码1~6
                    addData2List(gameCode, typeCode, list, oddsList, 3,
                            0, BetDataAdapter.CONTENT_TEXT)
                    addData2List(gameCode, typeCode, list, oddsList, 3,
                            3, BetDataAdapter.CONTENT_TEXT)
                }
                4 -> {//连码
                    (1..49).mapTo(list) {
                        if (it <= 44) {
                            MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_LIAN,
                                    TOTAL_SPAN_SIZE / 4, "连码", number = it)
                        } else {
                            MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_LIAN,
                                    TOTAL_SPAN_SIZE / 5, "连码", number = it)
                        }
                    }
                }
                5, 7 -> {//半波//特码生肖
                    oddsList.forEach {
                        it.list.forEach {
                            list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_TEXT,
                                    TOTAL_SPAN_SIZE / 3, oddsList[0].name, betItem = it))
                        }
                    }
                }
                6 -> {//一肖/尾肖
                    oddsList.forEachWithIndex { i, item ->
                        list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.TITLE, TOTAL_SPAN_SIZE, item.name))
                        item.list.forEach {
                            list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_TEXT,
                                    TOTAL_SPAN_SIZE / if (i == 0) 5 else 4, oddsList[i].name, betItem = it))
                        }
                    }
                }
                8, 9 -> {//合肖//生肖连
                    oddsList.forEach { it ->
                        it.list.forEach { item ->
                            list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_TEXT,
                                    TOTAL_SPAN_SIZE / 4, it.name, betItem = item))
                        }
                    }
                }
                10 -> {//尾数连
                    oddsList[0].list.forEachWithIndex { i, item ->
                        if (i < 6) {
                            list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_TEXT,
                                    TOTAL_SPAN_SIZE / 3, oddsList[0].name, betItem = item))
                        } else {
                            list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_TEXT,
                                    TOTAL_SPAN_SIZE / 4, oddsList[0].name, betItem = item))
                        }
                    }
                }
                11 -> {//全不中
                    addMarkSixNumber(gameCode, typeCode, list, oddsList[0])
                }
            }
            Constant.GAME_CODE_LUCKY_28 -> when (position) {//PC蛋蛋
                0 -> {//混合 波色
                    oddsList.forEachWithIndex { i, model ->
                        list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.TITLE,
                                TOTAL_SPAN_SIZE, model.name))
                        model.list.forEachWithIndex { j, item ->
                            if (i == 0 && j < 8) {
                                list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_TEXT,
                                        TOTAL_SPAN_SIZE / 4, model.name, betItem = item))
                            } else {
                                list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_TEXT,
                                        TOTAL_SPAN_SIZE / 3, model.name, betItem = item))
                            }
                        }
                    }
                }
                1 -> {//特码
                    oddsList.forEach { it ->
                        list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.TITLE, TOTAL_SPAN_SIZE, it.name))
                        it.list.forEach { item ->
                            list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_TEXT,
                                    TOTAL_SPAN_SIZE / 4, it.name, betItem = item))
                        }
                    }
                }
            }
        }
        return list
    }

    private fun addData2List(gameCode: Int, typeCode: String, list: MutableList<MultiBetItem>,
                             oddsList: List<OddsModel>, columnCount: Int, startColumn: Int, contentType: Int) {
        val (_, _, items) = oddsList[startColumn]
        val size = items.size
        (0 until columnCount).mapTo(list) {
            MultiBetItem(gameCode, typeCode, BetDataAdapter.TITLE,
                    TOTAL_SPAN_SIZE / columnCount, oddsList[it + startColumn].name)
        }
        (0 until size * columnCount).mapTo(list) {
            MultiBetItem(gameCode, typeCode, contentType, TOTAL_SPAN_SIZE / columnCount,
                    oddsList[it % columnCount + startColumn].name,
                    betItem = oddsList[it % columnCount + startColumn].list[it / columnCount])
        }
    }

    private fun addMarkSixNumber(gameCode: Int, typeCode: String, list: MutableList<MultiBetItem>, oddsBean: OddsModel) {
        oddsBean.list.forEachWithIndex { i, oddsItem ->
            if (i < 44) {
                list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_NUM,
                        TOTAL_SPAN_SIZE / 4, oddsBean.name, betItem = oddsItem))
            } else {
                list.add(MultiBetItem(gameCode, typeCode, BetDataAdapter.CONTENT_NUM,
                        TOTAL_SPAN_SIZE / 5, oddsBean.name, betItem = oddsItem))
            }
        }
    }

    fun getBetType(gameCode: Int, position: Int): Int {
        return when (gameCode) {
            Constant.GAME_CODE_GD_HAPPY_10,
            Constant.GAME_CODE_CJ_LUCKY_FARM -> {
                if (position == 3) BET_TYPE_TWO else BET_TYPE_ONE
            }
            Constant.GAME_CODE_HK_MARK_SIX -> when (position) {
                4 -> BET_TYPE_TWO
                8, 9, 10, 11 -> BET_TYPE_THREE
                else -> BET_TYPE_ONE
            }
            else -> BET_TYPE_ONE
        }
    }

    fun checkBetBeans(gameCode: Int, position: Int, selectId: Int, selectBeans: List<MultiBetItem>): String? {
        if (selectBeans.isEmpty()) {
            return Utils.getString(R.string.select_betting_item)
        }
        var message: String? = null
        val size = selectBeans.size
        when (gameCode) {
            Constant.GAME_CODE_GD_HAPPY_10//广东快乐十分
                , Constant.GAME_CODE_CJ_LUCKY_FARM//重庆幸运农场
            -> if (position == 3) {
                when (selectId) {
                    0//任选二
                    -> message = getBetLimitString(size, 2, 8)
                    1//选二直连
                    -> message = getBetLimitString(size, 2, 8)
                    2//选二连组
                    -> message = getBetLimitString(size, 2, 8)
                    3//任选三
                    -> message = getBetLimitString(size, 3, 8)
                    4//选三前直
                    -> message = getBetLimitString(size, 3, 8)
                    5//选三连组
                    -> message = getBetLimitString(size, 3, 8)
                    6//任选四
                    -> message = getBetLimitString(size, 4, 6)
                    7//任选五
                    -> message = getBetLimitString(size, 5, 6)
                }
            }
            Constant.GAME_CODE_HK_MARK_SIX//香港六合彩
            -> when (position) {
                4//连码
                -> when (selectId) {
                    0//三全中
                        , 1//中二
                        , 2//中三
                    -> message = getBetLimitString(size, 3, 10)
                    3//二全中
                        , 4//中特
                        , 5//中二
                        , 7//特串
                    -> message = getBetLimitString(size, 2, 10)
                    6//四中一
                    -> message = getBetLimitString(size, 4, 10)
                }
                8//合肖
                -> when (selectId) {
                    0//二肖
                    -> if (size != 2)
                        message = String.format(Utils.getString(R.string.bet_just), 2)
                    1//三肖
                    -> if (size != 3)
                        message = String.format(Utils.getString(R.string.bet_just), 3)
                    2//四肖
                    -> if (size != 4)
                        message = String.format(Utils.getString(R.string.bet_just), 4)
                    3//五肖
                    -> if (size != 5)
                        message = String.format(Utils.getString(R.string.bet_just), 5)
                    4//六肖
                    -> if (size != 6)
                        message = String.format(Utils.getString(R.string.bet_just), 6)
                }
                9//生肖连
                -> when (selectId) {
                    0//二肖连中
                    -> message = getBetLimitString(size, 2, 8)
                    1//三肖连中
                    -> message = getBetLimitString(size, 3, 8)
                    2//四肖连中
                    -> message = getBetLimitString(size, 4, 8)
                    3//五肖连中
                    -> message = getBetLimitString(size, 5, 8)
                    4//二肖不连中
                    -> message = getBetLimitString(size, 2, 8)
                    5//三肖不连中
                    -> message = getBetLimitString(size, 3, 8)
                    6//四肖不连中
                    -> message = getBetLimitString(size, 4, 8)
                }
                10//尾数连
                -> when (selectId) {
                    0//二尾连中
                    -> message = getBetLimitString(size, 2, 8)
                    1//三尾连中
                    -> message = getBetLimitString(size, 3, 8)
                    2//四尾连中
                    -> message = getBetLimitString(size, 4, 8)
                    3//二尾不连中
                    -> message = getBetLimitString(size, 2, 8)
                    4//三尾不连中
                    -> message = getBetLimitString(size, 3, 8)
                    5//四尾不连中
                    -> message = getBetLimitString(size, 4, 8)
                }
                11//全不中
                -> when (selectId) {
                    0//五不中
                    -> message = getBetLimitString(size, 5, 10)
                    1//六不中
                    -> message = getBetLimitString(size, 6, 10)
                    2//七不中
                    -> message = getBetLimitString(size, 7, 10)
                    3//八不中
                    -> message = getBetLimitString(size, 8, 10)
                    4//九不中
                    -> message = getBetLimitString(size, 9, 10)
                    5//十不中
                    -> message = getBetLimitString(size, 10, 12)
                    6//十一不中
                    -> message = getBetLimitString(size, 11, 13)
                    7//十二不中
                    -> message = getBetLimitString(size, 12, 15)
                }
            }
        }
        return message
    }

    private fun getBetLimitString(size: Int, min: Int, max: Int): String? {
        return if (size < min || size > max) {
            String.format(Utils.getString(R.string.bet_limit), min, max)
        } else null
    }

    fun getTotalBetNum(gameCode: Int, position: Int, selectId: Int, size: Int): Int {
        var total = size
        when (gameCode) {
            Constant.GAME_CODE_GD_HAPPY_10//广东快乐十分
                , Constant.GAME_CODE_CJ_LUCKY_FARM//重庆幸运农场
            -> if (position == 3) {
                when (selectId) {
                    0 -> total = getCombination(size, 2)
                    1 -> total = getCombination(size, 2)
                    2 -> total = getCombination(size, 2)
                    3 -> total = getCombination(size, 3)
                    4 -> total = getCombination(size, 3)
                    5 -> total = getCombination(size, 3)
                    6 -> total = getCombination(size, 4)
                    7 -> total = getCombination(size, 5)
                }
            }
            Constant.GAME_CODE_HK_MARK_SIX//香港六合彩
            -> when (position) {
                4//连码
                -> when (selectId) {
                    0//三全中
                        , 1//中二
                        , 2//中三
                    -> total = getCombination(size, 3)
                    3//二全中
                        , 4//中特
                        , 5//中二
                        , 7//特串
                    -> total = getCombination(size, 2)
                    6//四中一
                    -> total = getCombination(size, 4)
                }
                8//合肖
                -> when (selectId) {
                    0//二肖
                    -> total = getCombination(size, 2)
                    1//三肖
                    -> total = getCombination(size, 3)
                    2//四肖
                    -> total = getCombination(size, 4)
                    3//五肖
                    -> total = getCombination(size, 5)
                    4//六肖
                    -> total = getCombination(size, 6)
                }
                9//生肖连
                -> when (selectId) {
                    0//二肖连中
                    -> total = getCombination(size, 2)
                    1//三肖连中
                    -> total = getCombination(size, 3)
                    2//四肖连中
                    -> total = getCombination(size, 4)
                    3//五肖连中
                    -> total = getCombination(size, 5)
                    4//二肖不连中
                    -> total = getCombination(size, 2)
                    5//三肖不连中
                    -> total = getCombination(size, 3)
                    6//四肖不连中
                    -> total = getCombination(size, 4)
                }
                10//尾数连
                -> when (selectId) {
                    0//二尾连中
                    -> total = getCombination(size, 2)
                    1//三尾连中
                    -> total = getCombination(size, 3)
                    2//四尾连中
                    -> total = getCombination(size, 4)
                    3//二尾不连中
                    -> total = getCombination(size, 2)
                    4//三尾不连中
                    -> total = getCombination(size, 3)
                    5//四尾不连中
                    -> total = getCombination(size, 4)
                }
                11//全不中
                -> when (selectId) {
                    0//五不中
                    -> total = getCombination(size, 5)
                    1//六不中
                    -> total = getCombination(size, 6)
                    2//七不中
                    -> total = getCombination(size, 7)
                    3//八不中
                    -> total = getCombination(size, 8)
                    4//九不中
                    -> total = getCombination(size, 9)
                    5//十不中
                    -> total = getCombination(size, 10)
                    6//十一不中
                    -> total = getCombination(size, 11)
                    7//十二不中
                    -> total = getCombination(size, 12)
                }
            }
        }
        return total
    }

    /**
     * 从n个数中取k个数出来的组合有多少种
     * 按排列与组合公式 count = n!/(k!*(n-k)!)
     */
    private fun getCombination(n: Int, k: Int): Int {
        if (n < k) {
            return 0
        } else if (k == 0 || n == k) {
            return 1
        }
        var count = 1
        for (i in n downTo k + 1) {
            count *= i
        }
        for (i in 1..n - k) {
            count /= i
        }
        return count
    }
}
