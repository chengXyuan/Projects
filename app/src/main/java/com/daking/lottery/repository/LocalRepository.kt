package com.daking.lottery.repository

import com.daking.lottery.R
import com.daking.lottery.app.Constant
import com.daking.lottery.model.GameModel
import com.daking.lottery.model.MineItem

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
        list.add(GameModel(Constant.GAME_CODE_PJ_PK_10,
                "北京PK拾",
                R.drawable.img_logo_bj_pk_10))
        list.add(GameModel(Constant.GAME_CODE_CJ_LOTTERY,
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
        list.add(GameModel(Constant.GAME_CODE_CJ_LUCKY_FARM,
                "重庆幸运农场",
                R.drawable.img_logo_cq_lucky_farm))
        list.add(GameModel(Constant.GAME_CODE_REAL_VIDEO,
                "真人视讯",
                R.drawable.img_logo_asia_game))
        list.add(GameModel(Constant.GAME_CODE_CUSTOMER_SERVICE,
                "在线客户",
                R.drawable.img_customer_service))
        return list
    }

    fun getMineItems(): ArrayList<MineItem> {
        val list = ArrayList<MineItem>()
        list.add(MineItem("个人资料", R.drawable.ic_mine_personal_info))
        list.add(MineItem("修改密码", R.drawable.ic_mine_modify_password))
        list.add(MineItem("我的消息", R.drawable.ic_mine_msg))
        list.add(MineItem("资金管理", R.drawable.ic_mine_money))
        list.add(MineItem("银行卡", R.drawable.ic_mine_bankcard))
        list.add(MineItem("下注记录", R.drawable.ic_mine_bet_record))
        list.add(MineItem("新闻中心", R.drawable.ic_mine_news))
        list.add(MineItem("关于我们", R.drawable.ic_mine_about_us))
        list.add(MineItem("客服中心", R.drawable.ic_mine_customer_service))
        return list
    }
}