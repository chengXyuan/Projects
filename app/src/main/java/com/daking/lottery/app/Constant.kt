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
        val GAME_CODE_JS_QUICK3 = 172
        val GAME_CODE_BJ_PK10 = 51
        val GAME_CODE_CQ_SSC = 2
        val GAME_CODE_HK_MARK_SIX = 69
        val GAME_CODE_LUCKY_28 = 160
        val GAME_CODE_GD_HAPPY_10 = 3
        val GAME_CODE_LUCKY_AIRSHIP = 171
        val GAME_CODE_CQ_LUCKY_FARM = 47
        val GAME_CODE_ASIA_GAME = -1
        val GAME_CODE_CUSTOMER_SERVICE = -2
        /*===============game code start=================*/
    }
}