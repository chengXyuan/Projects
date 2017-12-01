package com.daking.lottery.repository

import com.daking.lottery.R
import com.daking.lottery.app.Constant
import com.daking.lottery.model.GameModel
import java.util.*

/**
 * 本地数据仓库: 为应用提供本地数据(包括写死的和缓存的.)
 */
class LocalRepository private constructor() {

    private object Holder {
        val Instance = LocalRepository()
    }

    companion object {
        val instance = Holder.Instance
    }

    fun getHomeGames(): ArrayList<GameModel> {
        val list = ArrayList<GameModel>()
        list.add(GameModel(Constant.GAME_CODE_BJ_PK10,
                "北京PK拾",
                R.drawable.img_logo_bj_pk_10))
        list.add(GameModel(Constant.GAME_CODE_CQ_SSC,
                "重庆时时彩",
                R.drawable.img_logo_cq_sscai))
        list.add(GameModel(Constant.GAME_CODE_HK_MARK_SIX,
                "香港六合彩",
                R.drawable.img_logo_hk_mark_six))
        list.add(GameModel(Constant.GAME_CODE_LUCKY_28,
                "PC蛋蛋",
                R.drawable.img_logo_lucky_28))
        list.add(GameModel(Constant.GAME_CODE_GD_HAPPY_10,
                "广东快乐十分",
                R.drawable.img_logo_gd_happy_10))
        list.add(GameModel(Constant.GAME_CODE_LUCKY_AIRSHIP,
                "幸运飞艇",
                R.drawable.img_logo_lucky_airship))
        list.add(GameModel(Constant.GAME_CODE_CQ_LUCKY_FARM,
                "重庆幸运农场",
                R.drawable.img_logo_cq_lucky_farm))
        list.add(GameModel(Constant.GAME_CODE_ASIA_GAME,
                "真人视讯",
                R.drawable.img_logo_asia_game))
        list.add(GameModel(Constant.GAME_CODE_CUSTOMER_SERVICE,
                "在线客户",
                R.drawable.img_customer_service))
        return list
    }
}