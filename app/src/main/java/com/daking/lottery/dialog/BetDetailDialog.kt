package com.daking.lottery.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daking.lottery.R
import com.daking.lottery.api.NetSubscriber
import com.daking.lottery.dialog.nice.BaseDialog
import com.daking.lottery.model.BetItem
import com.daking.lottery.model.BetRequest
import com.daking.lottery.model.BetTypeItem
import com.daking.lottery.model.MultiBetItem
import com.daking.lottery.repository.NetRepository
import com.daking.lottery.ui.activity.BetActivity
import com.daking.lottery.util.LotteryUtils
import com.daking.lottery.util.RxUtils
import com.daking.lottery.util.Utils
import com.daking.lottery.widget.RecyclerViewDivider
import kotlinx.android.synthetic.main.dialog_bet_detail.view.*
import java.util.*

class BetDetailDialog : BaseDialog() {

    private var gameCode = 0
    private var fragPosition = 0
    private var selectedId = 0
    private var betMoney = 0
    private var betType = 0
    private var mTotal = 0
    private var round = ""
    private var endTime = 0L

    companion object {
        private var typeItem: BetTypeItem? = null
        private lateinit var betItems: List<MultiBetItem>
        private lateinit var mOnBetResultListener: OnBetResultListener

        fun init(gameCode: Int, position: Int, selectedId: Int, betMoney: Int,
                 round: String, endTime: Long, comboCode: BetTypeItem?, itemList: List<MultiBetItem>, listener: OnBetResultListener): BetDetailDialog {
            val detailDialog = BetDetailDialog()
            val bundle = Bundle()
            bundle.putInt("game_code", gameCode)
            bundle.putInt("frag_position", position)
            bundle.putInt("selected_id", selectedId)
            bundle.putInt("bet_money", betMoney)
            bundle.putString("round_num", round)
            bundle.putLong("end_time", endTime)
            typeItem = comboCode
            betItems = itemList
            mOnBetResultListener = listener
            detailDialog.arguments = bundle
            return detailDialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        gameCode = bundle.getInt("game_code")
        fragPosition = bundle.getInt("frag_position")
        selectedId = bundle.getInt("selected_id")
        betMoney = bundle.getInt("bet_money")
        round = bundle.getString("round_num")
        endTime = bundle.getLong("end_time")
        betType = LotteryUtils.instance.getBetType(gameCode, fragPosition)
    }

    override fun intLayoutId() = R.layout.dialog_bet_detail

    override fun convertView(view: View, dialog: BaseDialog) {
        //点击事件
        view.iv_close.setOnClickListener { dismiss() }
        view.tv_dialog_cancel.setOnClickListener { dismiss() }
        view.tv_dialog_confirm.setOnClickListener { requestBet() }
        val clickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, v, position ->
            if (v.id == R.id.iv_delete) {
                betItems.minus(position)
                if (betItems.isEmpty()) {
                    dismiss()
                } else {
                    refreshRecycler(betType, view)
                    adapter.notifyItemRemoved(position)
                }
            }
        }

        //RecyclerView
        when (betType) {
            LotteryUtils.BET_TYPE_ONE -> {
                /*
                * 下注方式一:适用于 北京赛车，幸运飞艇，PC蛋蛋（幸运二八），重庆时时彩，广东快乐十分（除连码外），
                * 重庆幸运农场（除连码外），香港六合彩（特码，正码，正码特，正码1-6，半波，一肖、尾数，特码生肖）
                */
                view.recycler_view.layoutManager = LinearLayoutManager(context)
                view.recycler_view.addItemDecoration(RecyclerViewDivider(context,
                        RecyclerViewDivider.HORIZONTAL_DIVIDER, R.drawable.shape_divider_line))
                val adapter = object : BaseQuickAdapter<MultiBetItem, BaseViewHolder>(R.layout.item_bet_data, betItems) {
                    override fun convert(helper: BaseViewHolder, item: MultiBetItem) {
                        helper.setText(R.id.tv_bet_type, "${item.typeName} [${item.betItem?.name}]")
                        helper.setText(R.id.tv_bet_money, betMoney.toString())
                        helper.addOnClickListener(R.id.iv_delete)
                    }
                }
                adapter.onItemChildClickListener = clickListener
                view.recycler_view.adapter = adapter
            }
            LotteryUtils.BET_TYPE_TWO,
            LotteryUtils.BET_TYPE_THREE -> {
                /*
                * 下注方式三:适用于（过关 ，合肖，生肖连，尾数连，全不中）
                */
                view.tv_bet_name.visibility = View.VISIBLE
                view.tv_bet_name.text = typeItem!!.typeName
                view.recycler_view.layoutManager = GridLayoutManager(context, 2)
                view.recycler_view.addItemDecoration(RecyclerViewDivider(context,
                        RecyclerViewDivider.BOTH_SET, R.drawable.shape_divider_line))
                val adapter = object : BaseQuickAdapter<MultiBetItem, BaseViewHolder>(R.layout.item_bet_data2, betItems) {
                    override fun convert(helper: BaseViewHolder, item: MultiBetItem) {
                        if (betType == LotteryUtils.BET_TYPE_TWO) {
                            helper.setText(R.id.tv_bet_num, item.number.toString())
                        } else {
                            helper.setText(R.id.tv_bet_num, item.betItem!!.name)
                        }
                    }
                }
                adapter.setSpanSizeLookup { _, position ->
                    if (position == betItems.size - 1 && position % 2 == 0) {
                        0
                    } else 1
                }
                view.recycler_view.adapter = adapter
            }
        }

        refreshRecycler(betType, view)
    }

    private fun refreshRecycler(betType: Int, view: View) {
        mTotal = LotteryUtils.instance.getTotalBetNum(gameCode, fragPosition, selectedId, betItems.size)
        view.tv_total_count.text = mTotal.toString()
        view.tv_total_money.text = (mTotal * betMoney).toString()
        val size = if (betType == LotteryUtils.BET_TYPE_ONE) 5 else 10
        val params = view.recycler_view.layoutParams
        params.height = if (betItems.size > size) Utils.dp2px(150)
        else ViewGroup.LayoutParams.WRAP_CONTENT
        view.recycler_view.layoutParams = params
    }

    private fun requestBet() {
        val list = ArrayList<BetItem>()
        when (betType) {
            LotteryUtils.BET_TYPE_ONE -> {
                betItems.forEach {
                    it.betItem?.let { item ->
                        list.add(BetItem(item.key, item.odds, item.backwater))
                    }
                }
            }
            LotteryUtils.BET_TYPE_TWO -> {
                val key = betItems.map { it.number }.joinToString(prefix = "${typeItem!!.typeName},")
                list.add(BetItem(key, typeItem!!.odds, typeItem!!.backwater))
            }
            LotteryUtils.BET_TYPE_THREE -> {
                val key = betItems.map { it.number }.joinToString(prefix = "${typeItem!!.typeName},")
                val odds = betItems.map { it.betItem!!.odds.toFloat() }.min()
                val backwater = betItems.map { it.betItem!!.backwater.toFloat() }.min()
                list.add(BetItem(key, odds.toString(), backwater.toString()))
            }
        }
        val betRequest = BetRequest(endTime, round, (betMoney * mTotal).toString(), mTotal, list)
        //提交下注
        mOnBetResultListener.onCommit()
        NetRepository.instance.betting(betRequest)
                .compose(RxUtils.io2Main())
                .subscribe(object : NetSubscriber<Unit>() {
                    override fun onSuccess(code: Int, msg: String, data: List<Unit>?) {
                        dismiss()
                        mOnBetResultListener.onBetResult(true, msg)
                    }

                    override fun onFailure(code: Int, msg: String) {
                        dismiss()
                        mOnBetResultListener.onBetResult(false, msg)
                    }
                })
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        //弹窗消失时清空之前的选择
        if (activity is BetActivity) {
            val betActivity = activity as BetActivity
            betActivity.clearSelection()
        }
    }

    interface OnBetResultListener {
        fun onCommit()

        fun onBetResult(isSuccess: Boolean, msg: String)
    }
}