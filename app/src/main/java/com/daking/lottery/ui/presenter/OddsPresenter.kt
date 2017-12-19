package com.daking.lottery.ui.presenter

import com.daking.lottery.app.Constant
import com.daking.lottery.base.BasePresenter
import com.daking.lottery.event.BetClosedEvent
import com.daking.lottery.event.ClearSelectionEvent
import com.daking.lottery.event.SelectStateChangeEvent
import com.daking.lottery.model.BetTypeItem
import com.daking.lottery.model.MultiBetItem
import com.daking.lottery.ui.adapter.BetDataAdapter
import com.daking.lottery.ui.adapter.BetTypeAdapter
import com.daking.lottery.ui.fragment.OddsFragment
import com.daking.lottery.ui.iview.IOddsView
import com.daking.lottery.util.LotteryUtils
import com.daking.lottery.util.MemoryCacheManager
import com.daking.lottery.util.toast
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

class OddsPresenter : BasePresenter<IOddsView>() {

    private var gameCode = 0
    private var fragPosition = 0
    //是否已封盘
    private var isClosed = true
    //选择连中(仅连码需要此参数)
    var typeId = 0
    //"大单", "小单", "大双", "小双"只能选择一个下注, 用一个变量来记录已选择的个数
    private var selectNum = 0
    private var typeData: List<BetTypeItem>? = ArrayList()
    private var betData: List<MultiBetItem>? = ArrayList()

    private lateinit var mTypeAdapter: BetTypeAdapter
    private lateinit var mDataAdapter: BetDataAdapter

    override fun onAttached() {
        super.onAttached()
        val arguments = mView.getArguments()
        gameCode = arguments.getInt(OddsFragment.ARG_GAME_CODE)
        fragPosition = arguments.getInt(OddsFragment.ARG_POSITION)
        isClosed = arguments.getBoolean(OddsFragment.ARG_IS_CLOSED)
        initAdapter()
        getTypeData()
        getItemData()
    }

    private fun initAdapter() {
        mTypeAdapter = BetTypeAdapter(typeData)
        mDataAdapter = BetDataAdapter(gameCode, betData)
        //设置是否封盘
        mTypeAdapter.setClosed(isClosed)
        //设置每个item的宽度
        mTypeAdapter.setSpanSizeLookup { _, position -> mTypeAdapter.data[position].spanSize }
        //点击事件
        mTypeAdapter.setOnItemClickListener { adapter, _, position ->
            typeData?.forEachIndexed { index, item ->
                if (index == position && !item.selected) {
                    typeId = index
                    item.selected = true
                    adapter.notifyItemChanged(index, 0)
                    //
                    if (item.itemType == BetTypeAdapter.TYPE_NORMAL) {
                        getItemData()
                    }
                    //如果切换了选项, 则重置用户选择.
                    mView.clearSelection()
                } else if (index != position && item.selected) {
                    item.selected = false
                    adapter.notifyItemChanged(index, 0)
                }
            }
        }

        mDataAdapter = BetDataAdapter(gameCode, betData)
        //设置是否封盘
        mDataAdapter.setClosed(isClosed)
        //设置每个item的宽度
        mDataAdapter.setSpanSizeLookup { _, position -> mDataAdapter.data[position].spanSize }
        mDataAdapter.setOnItemClickListener { _, _, position ->
            val item = mDataAdapter.getItem(position)
            if (isClosed || item == null) {
                return@setOnItemClickListener
            }

            when (item.itemType) {
                BetDataAdapter.CONTENT_TEXT,
                BetDataAdapter.CONTENT_NUM,
                BetDataAdapter.CONTENT_LIAN -> {
                    if (multiSelectSpecialFour(item)) {
                        return@setOnItemClickListener
                    }
                    item.isSelected = !item.isSelected
                    //此处有一小坑,直接调用adapter.notifyItemChanged(position);刷新数据会出现视觉延迟
                    mDataAdapter.notifyItemChanged(position, 0)
                    //发送选择状态改变的事件
                    EventBus.getDefault().post(SelectStateChangeEvent(item))
                }
            }
        }

        mView.setAdapter(mTypeAdapter, mDataAdapter)
    }

    private fun getTypeData() {
        if (!isCombo()) {
            //非连码的typeData, 直接从LotteryUtil拿, 为空时隐藏recyclerView
            typeData = LotteryUtils.instance.getTypeData(gameCode, fragPosition)
            if (typeData != null && typeData!!.isNotEmpty()) {
                mTypeAdapter.setNewData(typeData)
            }
        } else {
            /*
            * 对于连码, 上面的RecyclerView显示赔率, 下面只显示数字, 同样上面的赔率也不要总是从接口中获取
            * 首先, 根据gameCode和typeCode从内存中获取数据, 如果内存中不存在赔率数据
            * 如果内存中已经存在了赔率数据(内存中仅保存了App本次启动后的数据, 可以认为是最新的数据),
            * 直接展示内存中的数据, 不再进行网络请求, 减轻服务器压力
            * */
            val typeCode = LotteryUtils.instance.getTypeCode(gameCode, fragPosition, typeId)
            val key = "odds_${gameCode}_$typeCode"
            @Suppress("UNCHECKED_CAST")
            typeData = MemoryCacheManager.instance.get(key) as List<BetTypeItem>?
            if (typeData == null || typeData!!.isEmpty()) {
                /*//先查询数据库中的数据, 显示
                typeData = LocalRepository.instance.getOddsLian(gameCode)
                if (typeData == null || typeData!!.isEmpty()) {
                }*/
                typeData = LotteryUtils.instance.getTypeData(gameCode, fragPosition)
                //同时查询最新的数据
                requestOdds(typeCode)
            }
            mTypeAdapter.setNewData(typeData)
        }
    }

    private fun getItemData() {
        val typeCode = LotteryUtils.instance.getTypeCode(gameCode, fragPosition, typeId)
        if (isCombo()) {
            //对于连码的, 赔率显示在了上面RecyclerView, 下面只需要选择下注的数字, 无需网络请求.
            betData = LotteryUtils.instance.getBetDataLian(gameCode, typeCode)
        } else {
            /*
            * 首先, 根据gameCode和typeCode从内存中获取数据, 如果内存中不存在赔率数据
            * 则查询数据库中的数据显示, 并请求接口, 同步服务器与本地数据库中的数据
            * 如果内存中已经存在了赔率数据(内存中仅保存了App本次启动后的数据, 可以认为是最新的数据),
            * 直接展示内存中的数据, 不再进行网络请求, 减轻服务器压力
            * */
            val key = "odds_${gameCode}_$typeCode"
            @Suppress("UNCHECKED_CAST")
            betData = MemoryCacheManager.instance.get(key) as List<MultiBetItem>?
            if (betData == null || betData!!.isEmpty()) {
                //先查询数据库中的数据, 显示
                //betData = LocalRepository.instance.getOddsData(gameCode, typeCode)
                //同时查询最新的数据
                requestOdds(typeCode)
            }
        }
        if (betData != null && betData!!.isNotEmpty()) {
            mDataAdapter.setNewData(betData)
        }
    }

    /**
     * 是否是连码: (广东快乐十分（连码），重庆幸运农场（连码），香港六合彩（连码）)
     */
    private fun isCombo(): Boolean {
        return (gameCode == Constant.GAME_CODE_GD_HAPPY_10 && fragPosition == 3)
                || (gameCode == Constant.GAME_CODE_CJ_LUCKY_FARM && fragPosition == 3)
                || (gameCode == Constant.GAME_CODE_HK_MARK_SIX && fragPosition == 4)
    }

    /**
     * item是不是 大单/大双/小单/小双 中的一个, 是否可选
     * 因为这四个只能同时选一个, 选择的时候要判断一下
     */
    private fun multiSelectSpecialFour(item: MultiBetItem): Boolean {
        return when (gameCode) {
            Constant.GAME_CODE_LUCKY_28, Constant.GAME_CODE_HK_MARK_SIX
            -> when (item.betItem?.name) {
                "大单", "小单", "大双", "小双" -> {
                    when {
                        item.isSelected -> {
                            selectNum--
                            false
                        }
                        selectNum == 0 -> {
                            selectNum++
                            false
                        }
                        else -> {
                            toast("大单 小单 大双 小双四个选项一局只能选一个投注")
                            true
                        }
                    }
                }
                else -> false
            }
            else -> false
        }
    }

    /**
     * 获取赔率
     */
    fun requestOdds(typeCode: String = LotteryUtils.instance.getTypeCode(gameCode, fragPosition, typeId)) {
        mNetRepository.getGameOdds(typeCode)
                .dealArray({ _, _, data ->
                    if (data == null || data.isEmpty()) return@dealArray
                    if (isCombo()) {
                        typeData = LotteryUtils.instance.reassembleTypeData(gameCode, data[0].list)
                        mTypeAdapter.setNewData(typeData)
                        //在内存中保存一份
                        val key = "odds_${gameCode}_$typeCode"
                        MemoryCacheManager.instance.put(key, typeData!!)
                        //更新数据库中的数据
                        //LocalRepository.instance.saveOddsLian(gameCode, typeData!!)
                    } else {
                        betData = LotteryUtils.instance.reassembleBetData(gameCode, fragPosition, typeCode, data)
                        mDataAdapter.setNewData(betData)
                        mView.refreshRecyclerLayout()
                        //在内存中保存一份
                        val key = "odds_${gameCode}_$typeCode"
                        MemoryCacheManager.instance.put(key, betData!!)
                        //更新数据库中的数据
                        //LocalRepository.instance.saveOddsData(gameCode, typeCode, betData!!)

                        val betType = LotteryUtils.instance.getBetType(gameCode, fragPosition)
                        if (betType == LotteryUtils.BET_TYPE_THREE) {
                            mTypeAdapter.data[typeId].key = data[0].duplex
                        }
                    }
                }, { _, msg ->
                    mDataAdapter.setNewData(null)
                    mView.showError(msg)
                })
    }

    override fun useEventBus() = true

    @Subscribe
    fun onEvent(event: ClearSelectionEvent) {
        selectNum = 0
        mDataAdapter.clearSelect()
    }

    @Subscribe
    fun onEvent(event: BetClosedEvent) {
        if (gameCode == event.gameCode) {
            isClosed = event.isClosed
            mTypeAdapter.setClosed(isClosed)
            mDataAdapter.setClosed(isClosed)
        }
    }
}