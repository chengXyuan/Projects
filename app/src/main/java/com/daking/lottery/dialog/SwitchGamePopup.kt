package com.daking.lottery.dialog

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.daking.lottery.R
import com.daking.lottery.dialog.easy.BasePopupWindow
import com.daking.lottery.util.LotteryUtils
import com.daking.lottery.util.Utils
import kotlinx.android.synthetic.main.popup_layout_switch_game.view.*

class SwitchGamePopup(val context: Context, val gameCode: Int, val listener: OnItemClickListener)
    : BasePopupWindow(context), View.OnClickListener {

    override fun initAttributes() {
        setContentView(R.layout.popup_layout_switch_game, Utils.dp2px(150), ViewGroup.LayoutParams.WRAP_CONTENT)
        setOutsideTouchable(true)
                .setBackgroundDimEnable(true)
                .setAnimationStyle(R.style.popup_drop_down)
    }

    override fun initViews(view: View) {
        val games = LotteryUtils.instance.getAllOtherGames(gameCode)
        view.llContainer.removeAllViews()
        games.entries.forEach {
            val textView = TextView(context)
            textView.tag = it.key
            textView.text = it.value
            textView.textSize = 16f
            textView.setTextColor(Color.WHITE)
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.dp2px(40))
            textView.gravity = Gravity.CENTER
            textView.setOnClickListener(this)
            view.llContainer.addView(textView, params)
        }
    }

    override fun onClick(v: View) {
        dismiss()
        listener.onItemClick(v.tag as Int)
    }

    interface OnItemClickListener {
        fun onItemClick(gameCode: Int)
    }
}