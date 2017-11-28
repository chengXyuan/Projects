package com.daking.lottery.base

abstract class BaseMVPFragment<P : BasePresenter<*>> : BaseFragment() {

    lateinit var mPresenter: P

    override fun initPresenter() {
        mPresenter = TUtil.getT(this, 0)
        bindPresenter(mPresenter)
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