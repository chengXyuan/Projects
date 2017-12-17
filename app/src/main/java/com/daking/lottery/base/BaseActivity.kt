package com.daking.lottery.base

import android.os.Bundle
import com.daking.lottery.R
import com.daking.lottery.dialog.LoadingDialog
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import org.greenrobot.eventbus.EventBus

abstract class BaseActivity : RxAppCompatActivity() {

    private val loadingDialog: LoadingDialog by lazy { LoadingDialog() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        initStatusBar()
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

    open fun initStatusBar() {}

    open fun initPresenter() {}

    open fun useEventBus() = false

    fun getActivity() = this

    fun <T> bindLifecycle(): LifecycleTransformer<T> {
        return bindUntilEvent(ActivityEvent.DESTROY)
    }

    fun showLoadingDialog(msg: String? = getString(R.string.loading)) {
        loadingDialog.setText(msg).show(supportFragmentManager)
    }

    fun dismissLoadingDialog() {
        loadingDialog.dismissAllowingStateLoss()
    }

    protected abstract fun getLayoutResId(): Int

    protected abstract fun initData(savedInstanceState: Bundle?)
}