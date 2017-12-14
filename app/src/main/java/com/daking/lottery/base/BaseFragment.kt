package com.daking.lottery.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daking.lottery.R
import com.daking.lottery.dialog.LoadingDialog
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.support.RxFragment
import org.greenrobot.eventbus.EventBus

abstract class BaseFragment : RxFragment() {

    protected var mRootView: View? = null
    private val loadingDialog: LoadingDialog by lazy { LoadingDialog() }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater?.inflate(getLayoutResId(), container, false)
        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initPresenter()
        initData(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        if (useEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onStop() {
        super.onStop()
        if (useEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    open fun useEventBus() = false

    open fun initPresenter() {}

    fun <T> bindLifecycle(): LifecycleTransformer<T> {
        return bindUntilEvent(FragmentEvent.DESTROY_VIEW)
    }

    fun showLoadingDialog(msg: String? = getString(R.string.loading)) {
        loadingDialog.setText(msg).show(fragmentManager)
    }

    fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }

    abstract fun getLayoutResId(): Int

    abstract fun initData(savedInstanceState: Bundle?)
}