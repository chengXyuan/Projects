package com.daking.lottery.base

abstract class BaseMVPActivity<P : BasePresenter<*>> : BaseActivity() {

    lateinit var mPresenter: P

    override fun initPresenter() {
        mPresenter = TUtil.getT(this, 0)
        if (this is BaseView) {
            bindPresenter(mPresenter)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <V : BaseView> bindPresenter(presenter: BasePresenter<V>) {
        presenter.setView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetached()
    }
}