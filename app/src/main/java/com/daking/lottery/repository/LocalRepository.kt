package com.daking.lottery.repository

import com.daking.lottery.R
import com.daking.lottery.app.App
import com.daking.lottery.app.Constant
import com.daking.lottery.model.*
import com.daking.lottery.util.log
import io.objectbox.kotlin.boxFor
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

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

    /**
     * 获取首页九宫格数据
     */
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

    /**
     * 获取个人中心九宫格数据
     */
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

    fun getBankcardSupported(): ArrayList<BankRes> {
        val list = ArrayList<BankRes>()
        list.add(BankRes("工商银行", "ICBC", R.drawable.icon_bank_logo_icbc))
        list.add(BankRes("建设银行", "CCB", R.drawable.icon_bank_logo_ccb))
        list.add(BankRes("招商银行", "CMB", R.drawable.icon_bank_logo_cmb))
        list.add(BankRes("农业银行", "ABC", R.drawable.icon_bank_logo_abc))
        list.add(BankRes("中国银行", "BOC", R.drawable.icon_bank_logo_bc))
        list.add(BankRes("邮政储蓄银行", "PSBS", R.drawable.icon_bank_logo_psbc))
        list.add(BankRes("民生银行", "CMBC", R.drawable.icon_bank_logo_cmbc))
        list.add(BankRes("兴业银行", "CIB", R.drawable.icon_bank_logo_ibc))
        list.add(BankRes("中信银行", "CTTIC", R.drawable.icon_bank_logo_cib))
        list.add(BankRes("渤海银行", "CBHB", R.drawable.icon_bank_logo_cbhb))
        list.add(BankRes("光大银行", "CEB", R.drawable.icon_bank_logo_ceb))
        list.add(BankRes("广发银行", "GDB", R.drawable.icon_bank_logo_gdb))
        list.add(BankRes("华夏银行", "HXB", R.drawable.icon_bank_logo_hxb))
        list.add(BankRes("平安银行", "PAB", R.drawable.icon_bank_logo_pab))
        list.add(BankRes("浦发银行", "SPDB", R.drawable.icon_bank_logo_spdb))
        list.add(BankRes("北京农商银行", "BJRCB", R.drawable.icon_bank_logo_brcb))
        list.add(BankRes("上海银行", "SHB", R.drawable.icon_bank_logo_bs))
        list.add(BankRes("交通银行", "BOCO", R.drawable.icon_bank_logo_bcomm))
        return list
    }

    fun getBankLogoResByName(bankName: String): Int {
        val banks = getBankcardSupported()
        return banks.find { it.bankName == bankName }?.logoRes ?: 0
    }

    /**
     * 根据gameCode和typeCode获取赔率
     */
    fun getOddsData(gameCode: Int, typeCode: String): List<MultiBetItem> {
        val boxStore = App.instance.boxStore
        return boxStore.boxFor(MultiBetItem::class).query()
                .equal(MultiBetItem_.gameCode, gameCode.toLong())
                .equal(MultiBetItem_.typeCode, typeCode)
                .build().find()
    }

    /**
     * 根据gameCode和typeCode保存赔率到数据库
     */
    fun saveOddsData(gameCode: Int, typeCode: String, data: List<MultiBetItem>) {
        Observable.just(data)
                .observeOn(Schedulers.io())//在IO线程中执行保存操作
                .subscribe { it ->
                    val boxStore = App.instance.boxStore
                    val betItemBox = boxStore.boxFor(MultiBetItem::class)
                    //查询就的数据并删除
                    val oddData = getOddsData(gameCode, typeCode)
                    betItemBox.remove(oddData)
                    //保存新的数据到数据库
                    betItemBox.put(it)
                    log("保存数据 gameCode = $gameCode, typeCode = $typeCode, 共${data.size}条!")
                }
    }

    /**
     * 获取连码赔率
     */
    fun getOddsLian(gameCode: Int): List<BetTypeItem> {
        val boxStore = App.instance.boxStore
        return boxStore.boxFor(BetTypeItem::class).query()
                .equal(BetTypeItem_.gameCode, gameCode.toLong())
                .build().find()
    }

    /**
     * 保存连码赔率
     */
    fun saveOddsLian(gameCode: Int, data: List<BetTypeItem>) {
        Observable.just(data)
                .observeOn(Schedulers.io())//在IO线程中执行保存操作
                .subscribe { it ->
                    val boxStore = App.instance.boxStore
                    val betItemBox = boxStore.boxFor(BetTypeItem::class)
                    //查询就的数据并删除
                    val oddData = getOddsLian(gameCode)
                    betItemBox.remove(oddData)
                    //保存新的数据到数据库
                    betItemBox.put(it)
                    log("保存数据 gameCode = $gameCode, 共${data.size}条!")
                }
    }
}