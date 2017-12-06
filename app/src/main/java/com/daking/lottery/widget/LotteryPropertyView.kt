package com.daking.lottery.widget

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import com.daking.lottery.R
import com.daking.lottery.app.Constant
import com.daking.lottery.util.LotteryUtils
import com.google.android.flexbox.FlexboxLayout

class LotteryPropertyView : FlexboxLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setNumbers(gameCode: Int, numbers: List<String>) {
        removeAllViews()
        when (gameCode) {
            Constant.GAME_CODE_HK_MARK_SIX -> {
                //香港六合彩
                val sum = numbers.fold(0) { acc, s -> acc + s.toInt() }
                addPropertyTitleView("总和")
                addPropertyView(sum.toString())
                addPropertyTitleView("生肖")
                numbers.forEach {
                    addPropertyView(LotteryUtils.instance.getZodiac(it.toInt()))
                }
            }
            Constant.GAME_CODE_PJ_PK_10,
            Constant.GAME_CODE_LUCKY_AIRSHIP -> {
                //北京PK拾 //幸运飞艇
                addPropertyTitleView("冠亚和")
                val sum = numbers[0].toInt() + numbers[1].toInt()
                addPropertyView(sum.toString())
                addPropertyView(if (sum >= 12) "大" else "小")
                addPropertyView(if (sum % 2 == 0) "双" else "单")

                addPropertyTitleView("1-5球")
                numbers.take(5).forEachIndexed { index, s ->
                    val property = if (s.toInt() > numbers[numbers.size - 1 - index].toInt()) "龙" else "虎"
                    addPropertyView(property)
                }
            }
            Constant.GAME_CODE_GD_HAPPY_10,
            Constant.GAME_CODE_CJ_LUCKY_FARM -> {
                //重庆幸运农场 广东快乐十分
                val sum = numbers.fold(0) { acc, s -> acc + s.toInt() }
                addPropertyTitleView("总和")
                addPropertyView(sum.toString())

                addPropertyTitleView("尾大小")
                addPropertyView(if (sum % 10 >= 5) "尾大" else "尾小")

                addPropertyTitleView("1-4球")
                numbers.take(4).forEachIndexed { index, s ->
                    val property = if (s.toInt() > numbers[numbers.size - 1 - index].toInt()) "龙" else "虎"
                    addPropertyView(property)
                }
            }
            Constant.GAME_CODE_CJ_LOTTERY -> {
                //重庆时时彩
                val sum = numbers.fold(0) { acc, s -> acc + s.toInt() }
                addPropertyTitleView("总和")
                addPropertyView(sum.toString())
                addPropertyView(if (sum >= 23) "大" else "小")
                addPropertyView(if (sum % 2 == 0) "双" else "单")

                addPropertyTitleView("龙虎")
                val property = when {
                    numbers[0].toInt() > numbers[4].toInt() -> "龙"
                    numbers[0].toInt() < numbers[4].toInt() -> "虎"
                    else -> "和"
                }
                addPropertyView(property)
            }
            Constant.GAME_CODE_LUCKY_28 -> {
                //PC蛋蛋
                val sum = numbers.fold(0) { acc, s -> acc + s.toInt() }
                addPropertyTitleView("总和", 0f)
                addPropertyView(sum.toString())
                addPropertyView(if (sum >= 14) "大" else "小")
                addPropertyView(if (sum % 2 == 0) "双" else "单")
            }
            Constant.GAME_CODE_JS_FAST_3 -> {
                //江苏快3
                val sum = numbers.fold(0) { acc, s -> acc + s.toInt() }
                addPropertyTitleView("总和")
                addPropertyView(sum.toString())
                val property = if (numbers[0] == numbers[1] && numbers[0] == numbers[2]) "通吃"
                else if (sum >= 11) "大" else "小"
                addPropertyView(property)

                addPropertyTitleView("鱼虾蟹")
                numbers.forEach {
                    addPropertyView(LotteryUtils.instance.getYuXiaXie(it.toInt()))
                }
            }
        }
    }

    private fun addPropertyTitleView(text: String, flexGrow: Float = 2f) {
        val layoutParams = FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.MATCH_PARENT)
        layoutParams.flexGrow = flexGrow
        val textView = TextView(context)
        textView.setBackgroundResource(R.color.lottery_property_title_bg)
        textView.textSize = 12f
        textView.gravity = Gravity.CENTER
        textView.text = text
        textView.setTextColor(ContextCompat.getColor(context, R.color.text_normal))
        addView(textView, layoutParams)
    }

    private fun addPropertyView(text: String) {
        val layoutParams = FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.MATCH_PARENT)
        layoutParams.flexGrow = 1f
        val textView = TextView(context)
        textView.textSize = 12f
        textView.gravity = Gravity.CENTER
        textView.text = text
        textView.setTextColor(ContextCompat.getColor(context, R.color.text_normal))
        addView(textView, layoutParams)
    }
}