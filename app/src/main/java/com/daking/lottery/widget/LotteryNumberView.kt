package com.daking.lottery.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import com.daking.lottery.app.Constant
import com.daking.lottery.util.LotteryUtils
import com.daking.lottery.util.Utils
import com.google.android.flexbox.FlexboxLayout

class LotteryNumberView : FlexboxLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setNumbers(gameCode: Int, numbers: List<String>) {
        removeAllViews()
        numbers.forEachIndexed { index, number ->
            val layoutParams = FlexboxLayout.LayoutParams(Utils.dp2px(21), Utils.dp2px(21))
            layoutParams.marginStart = Utils.dp2px(3)
            layoutParams.bottomMargin = Utils.dp2px(3)
            val textView = TextView(context)
            textView.setTextColor(Color.WHITE)
            textView.gravity = Gravity.CENTER
            textView.textSize = 12f
            when (gameCode) {
                Constant.GAME_CODE_PJ_PK_10,
                Constant.GAME_CODE_CJ_LUCKY_FARM,
                Constant.GAME_CODE_LUCKY_AIRSHIP -> {
                    //背景图包含了号码, 不设置文字.
                }
                else -> {
                    textView.text = number
                }
            }

            if (gameCode == Constant.GAME_CODE_PJ_FUNNY_8) {
                //北京快乐8有20个号码, 第11个号码时强制换行.
                layoutParams.isWrapBefore = index % 10 == 0
            }

            textView.setBackgroundResource(LotteryUtils.instance.getLotteryNumBg(gameCode, number))
            addView(textView, layoutParams)
            //一些特殊情况的处理
            when (gameCode) {
                Constant.GAME_CODE_HK_MARK_SIX -> {
                    //相关六合彩, 特码与其他号码间用'+'号连接
                    if (index == 5) {
                        val tvPlus = TextView(context)
                        tvPlus.text = "+"
                        tvPlus.gravity = Gravity.CENTER
                        tvPlus.textSize = 14f
                        tvPlus.setTextColor(Color.GRAY)
                        addView(tvPlus, layoutParams)
                    }
                }
                Constant.GAME_CODE_LUCKY_28 -> {
                    //PC蛋蛋, 每个号码之间用'+'号连接, 和值用'='连接
                    if (index != numbers.size - 1) {
                        val tvPlus = TextView(context)
                        tvPlus.text = "+"
                        tvPlus.gravity = Gravity.CENTER
                        tvPlus.textSize = 14f
                        tvPlus.setTextColor(Color.GRAY)
                        addView(tvPlus, layoutParams)
                    } else {
                        val tvPlus = TextView(context)
                        tvPlus.text = "="
                        tvPlus.gravity = Gravity.CENTER
                        tvPlus.textSize = 14f
                        tvPlus.setTextColor(Color.GRAY)
                        addView(tvPlus, layoutParams)

                        val tvSum = TextView(context)
                        tvSum.setTextColor(Color.WHITE)
                        tvSum.gravity = Gravity.CENTER
                        tvSum.textSize = 12f
                        val sum = numbers.fold(0) { acc, s -> acc + s.toInt() }
                        tvSum.text = sum.toString()
                        tvSum.setBackgroundResource(LotteryUtils.instance.getPCSumBg(sum))
                        addView(tvSum, layoutParams)
                    }
                }
            }
        }
    }
}