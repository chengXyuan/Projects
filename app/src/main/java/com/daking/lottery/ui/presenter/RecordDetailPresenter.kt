package com.daking.lottery.ui.presenter

import com.daking.lottery.app.Constant
import com.daking.lottery.base.BasePresenter
import com.daking.lottery.event.UnsettledCountEvent
import com.daking.lottery.ui.fragment.RecordDetailFragment
import com.daking.lottery.ui.iview.IRecordDetailView
import org.greenrobot.eventbus.EventBus

class RecordDetailPresenter : BasePresenter<IRecordDetailView>() {

    fun getRecordDetail(type: Int, mPageIndex: Int, timestamp: Long?) {
        when (type) {
            RecordDetailFragment.TYPE_UNSETTLED -> {
                //未结
                getUnsettledOrders(mPageIndex, timestamp)
            }
            RecordDetailFragment.TYPE_SETTLED -> {
                //已结
                getSettledOrders(mPageIndex, timestamp)
            }
        }
    }

    private fun getUnsettledOrders(mPageIndex: Int, timestamp: Long?) {
        mNetRepository.getUnsettedOrders(mPageIndex, Constant.PAGE_SIZE, timestamp)
                .dealArray({ total, _, data ->
                    EventBus.getDefault().post(UnsettledCountEvent(total))
                    mView.showRecord(data)
                }, { _, msg ->
                    mView.showError(msg)
                    mView.onComplete()
                }, {
                    mView.onComplete()
                })
    }

    private fun getSettledOrders(mPageIndex: Int, timestamp: Long?) {
        mNetRepository.getSettledOrders(mPageIndex, Constant.PAGE_SIZE, timestamp)
                .dealArray({ _, _, data ->
                    mView.showRecord(data)
                }, { _, msg ->
                    mView.showError(msg)
                    mView.onComplete()
                }, {
                    mView.onComplete()
                })
    }
}