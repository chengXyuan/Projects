package com.daking.lottery.ui.iview

import android.os.Bundle
import com.daking.lottery.base.BaseView
import com.daking.lottery.ui.adapter.BetDataAdapter
import com.daking.lottery.ui.adapter.BetTypeAdapter

interface IOddsView : BaseView {

    fun setAdapter(typeAdapter: BetTypeAdapter, dataAdapter: BetDataAdapter)

    fun getArguments(): Bundle

    fun showError(msg: String)

    fun refreshRecyclerLayout()

    fun clearSelection()
}
