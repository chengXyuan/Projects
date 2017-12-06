package com.daking.lottery.app

interface Constant {
    companion object {
        val REMEMBER_ACCOUNT = "remember_account"
        val USERNAME = "username"
        val PASSWORD = "password"
        val SERVICE_URL = "service_url"

        val PAGE_SIZE = 20

        val REGEX_USERNAME = "^[a-zA-Z0-9]{6,15}$"
        val REGEX_PASSWORD = "^[a-zA-Z0-9]{6,15}$"
        val REGEX_REAL_NAME = "^[\\u4e00-\\u9fa5]{2,8}$"

        /*===============game code start=================*/
        val GAME_CODE = "game_code"
        val GAME_CODE_PJ_FUNNY_8 = 1//北京快乐8
        val GAME_CODE_GD_5_IN_11 = 2//广东11选5
        val GAME_CODE_PJ_PK_10 = 3//北京PK拾
        val GAME_CODE_CJ_LOTTERY = 4//重庆时时彩
        val GAME_CODE_LUCKY_AIRSHIP = 5//幸运飞艇
        val GAME_CODE_LUCKY_28 = 6 //PC 蛋蛋
        val GAME_CODE_GD_HAPPY_10 = 7//广东快乐十分
        val GAME_CODE_CJ_LUCKY_FARM = 8//重庆幸运农场
        val GAME_CODE_HK_MARK_SIX = 9//香港六合彩
        val GAME_CODE_JS_FAST_3 = 10//江苏快3
        val GAME_CODE_REAL_VIDEO = -1
        val GAME_CODE_CUSTOMER_SERVICE = -2
        /*===============game code start=================*/

        /*================一些写死的URL==================*/
        val BASE_URL = "http://111.68.10.210/userBetting/"
        val GAME_RULE_WEBSITE = "http://androidapi.alcp66.com/#/todayRule"//游戏规则
        val LOTTERY_WEBSITE = "https://pk10tv.com/"//开奖网
    }
}