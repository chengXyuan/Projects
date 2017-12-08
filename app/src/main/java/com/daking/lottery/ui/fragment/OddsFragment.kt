package com.daking.lottery.ui.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.daking.lottery.R

import com.daking.lottery.base.BaseMVPFragment
import com.daking.lottery.model.BetTypeItem
import com.daking.lottery.ui.activity.BetActivity
import com.daking.lottery.ui.adapter.BetDataAdapter
import com.daking.lottery.ui.adapter.BetTypeAdapter
import com.daking.lottery.ui.iview.IOddsView
import com.daking.lottery.ui.presenter.OddsPresenter
import com.daking.lottery.util.LotteryUtils
import com.daking.lottery.util.Utils
import kotlinx.android.synthetic.main.fragment_odds.*

class OddsFragment : BaseMVPFragment<OddsPresenter>(), IOddsView {

    companion object {
        val ARG_GAME_CODE = "arg_game_code"
        val ARG_POSITION = "arg_position"
        val ARG_IS_CLOSED = "arg_is_closed"

        fun newInstance(gameCode: Int, position: Int, isClosed: Boolean): OddsFragment {
            val fragment = OddsFragment()
            val args = Bundle()
            args.putInt(ARG_GAME_CODE, gameCode)
            args.putInt(ARG_POSITION, position)
            args.putBoolean(ARG_IS_CLOSED, isClosed)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mErrorView: View
    private lateinit var mTvError: TextView
    private lateinit var mTypeAdapter: BetTypeAdapter
    private lateinit var mDataAdapter: BetDataAdapter

    override fun getLayoutResId() = R.layout.fragment_odds

    override fun setAdapter(typeAdapter: BetTypeAdapter, dataAdapter: BetDataAdapter) {
        //上部下注类型RecyclerView
        recycler_types.setHasFixedSize(true)
        recycler_types.isNestedScrollingEnabled = false
        recycler_types.layoutManager = GridLayoutManager(context, LotteryUtils.TOTAL_SPAN_SIZE)
        mTypeAdapter = typeAdapter
        recycler_types.adapter = typeAdapter
        //下注选项recycler
        recycler_items.setHasFixedSize(true)
        recycler_items.isNestedScrollingEnabled = false
        recycler_items.layoutManager = GridLayoutManager(context, LotteryUtils.TOTAL_SPAN_SIZE)
        mDataAdapter = dataAdapter
        recycler_items.adapter = dataAdapter

        initEmptyView()
    }

    override fun initData(savedInstanceState: Bundle?) {
        refreshRecyclerLayout()
    }

    private fun initEmptyView() {
        mErrorView = layoutInflater.inflate(R.layout.layout_error_view,
                recycler_items.parent as ViewGroup, false)
        mErrorView.setOnClickListener { mPresenter.requestOdds() }
        mTvError = mErrorView.findViewById(R.id.tv_error_view_msg)
    }

    override fun refreshRecyclerLayout() {
        val params = recycler_items.layoutParams as LinearLayout.LayoutParams
        val data = mDataAdapter.data
        if (data.isNotEmpty() && data[0].itemType == BetDataAdapter.TITLE) {
            params.topMargin = Utils.dp2px(-5)
        } else {
            params.topMargin = Utils.dp2px(5)
        }
        recycler_items.layoutParams = params
    }

    override fun clearSelection() {
        if (activity is BetActivity) {
            val betActivity = activity as BetActivity
            betActivity.clearSelection()
        }
    }

    override fun showError(msg: String) {
        mTvError.text = msg
        mDataAdapter.emptyView = mErrorView
    }

    fun getSelectedId() = mPresenter.typeId

    fun getSelectItem(): BetTypeItem? {
        return if (mTypeAdapter.data.isEmpty()) {
            null
        } else {
            mTypeAdapter.data[getSelectedId()]
        }
    }
}
