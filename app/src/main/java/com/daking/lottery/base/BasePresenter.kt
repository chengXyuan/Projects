package com.daking.lottery.base

import com.daking.lottery.api.NetSubscriber
import com.daking.lottery.app.App
import com.daking.lottery.model.Root
import com.daking.lottery.repository.NetRepository
import com.daking.lottery.util.RxUtils
import com.daking.lottery.util.Utils
import com.daking.lottery.util.log
import io.reactivex.Flowable
import org.greenrobot.eventbus.EventBus


open class BasePresenter<V : BaseView> {

    open lateinit var mView: V
    open lateinit var mNetRepository: NetRepository

    fun setView(view: V) {
        mView = view
        mNetRepository = NetRepository.instance
        onAttached()
    }

    open fun onAttached() {
        if (useEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    open fun onDetached() {
        if (useEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    open fun useEventBus() = false

    /**
     * 处理网络请求响应, 返回结果是对象
     */
    protected fun <T> Flowable<Root<T>>.dealObj(
            success: (code: Int, msg: String, model: T?) -> Unit,
            failure: (code: Int, msg: String) -> Unit = { code, msg -> log("code=$code, msg: $msg") },
            complete: () -> Unit = {},
            showLoading: Boolean = false,
            loadingMsg: String = "加载中..."
    ) {
        this.compose(mView.bindLifecycle())
                .compose(RxUtils.io2Main())
                .subscribe(object : NetSubscriber<T>() {

                    override fun onStart() {
                        super.onStart()
                        if (!Utils.isNetworkAvailable(App.instance)) {
                            cancel()
                            onFailure(9000, "网络异常，请检查网络连接后重试")
                        } else if (showLoading) {
                            mView.showLoadingDialog(loadingMsg)
                        }
                    }

                    override fun onSuccess(code: Int, msg: String, data: List<T>?) {
                        success(code, msg, if (data == null) null else data[0])
                    }

                    override fun onFailure(code: Int, msg: String) {
                        if (showLoading) {
                            mView.dismissLoadingDialog()
                        }
                        failure(code, msg)
                    }

                    override fun onComplete() {
                        super.onComplete()
                        if (showLoading) {
                            mView.dismissLoadingDialog()
                        }
                        complete()
                    }
                })
    }

    /**
     * 处理网络请求响应, 返回结果是集合
     */
    protected fun <T> Flowable<Root<T>>.dealArray(
            success: (code: Int, msg: String, data: List<T>?) -> Unit,
            failure: (code: Int, msg: String) -> Unit = { code, msg -> log("code=$code, msg: $msg") },
            complete: () -> Unit = {}
    ) {
        this.compose(mView.bindLifecycle())
                .compose(RxUtils.io2Main())
                .subscribe(object : NetSubscriber<T>() {
                    override fun onSuccess(code: Int, msg: String, data: List<T>?) {
                        success(code, msg, data)
                    }

                    override fun onFailure(code: Int, msg: String) {
                        failure(code, msg)
                    }

                    override fun onComplete() {
                        super.onComplete()
                        complete()
                    }
                })
    }

}
