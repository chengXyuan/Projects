package com.daking.lottery.dialog

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daking.lottery.R
import com.daking.lottery.dialog.easy.BasePopupWindow
import com.daking.lottery.dialog.easy.HorizontalGravity
import com.daking.lottery.dialog.easy.VerticalGravity
import com.daking.lottery.model.TypeTitle
import com.daking.lottery.util.LotteryUtils
import kotlinx.android.synthetic.main.popup_layout_more_type.view.*

class MoreGameTypePopup(val context: Context, val gameCode: Int, var list: List<TypeTitle>,
                        private val listener: OnItemSelectListener)
    : BasePopupWindow(context) {

    private lateinit var mAdapter: BaseQuickAdapter<TypeTitle, BaseViewHolder>

    override fun initAttributes() {
        setContentView(R.layout.popup_layout_more_type, -1, -2)
                .setFocusAndOutsideEnable(true)
                .setBackgroundDimEnable(false)
                .setAnimationStyle(R.style.popup_drop_down)

    }

    override fun initViews(view: View) {
        view.recycler_view.setHasFixedSize(true)
        mAdapter = object : BaseQuickAdapter<TypeTitle, BaseViewHolder>(R.layout.item_game_type_title) {
            override fun convert(helper: BaseViewHolder, item: TypeTitle) {
                val textView = helper.getView<TextView>(R.id.tv_game_type)
                textView.tag = item.position
                textView.text = item.title
            }
        }
        view.recycler_view.layoutManager = GridLayoutManager(context, 4)
        view.recycler_view.adapter = mAdapter

        mAdapter.setOnItemClickListener { _, _, position ->
            list = LotteryUtils.instance.getTabTitle(gameCode)
            val key = mAdapter.data[position].position
            val start = key / 4 * 4
            val rgTitles = list.slice(start..start + 3)
            list = list.minus(rgTitles)
            listener.onItemSelect(key % 4, rgTitles)
            dismiss()
        }
    }

    override fun showAtAnchorView(anchor: View, vertGravity: VerticalGravity, horizGravity: HorizontalGravity) {
        mAdapter.setNewData(list)
        super.showAtAnchorView(anchor, vertGravity, horizGravity)
    }

    interface OnItemSelectListener {
        fun onItemSelect(position: Int, rgTitles: List<TypeTitle>)
    }
}
