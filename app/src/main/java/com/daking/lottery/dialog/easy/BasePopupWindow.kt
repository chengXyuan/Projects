package com.daking.lottery.dialog.easy

import android.content.Context
import android.view.View

abstract class BasePopupWindow(context: Context) : EasyPopupWindow(context) {

    override fun onPopupWindowCreated() {
        super.onPopupWindowCreated()
        //执行设置PopupWindow属性也可以通过Builder中设置
        //setContentView(x,x,x)
        //...
        initAttributes()
    }

    override fun onPopupWindowViewCreated(contentView: View) {
        super.onPopupWindowViewCreated(contentView)
        initViews(contentView)
    }

    /**
     * 可以在此方法中设置PopupWindow需要的属性
     */
    abstract fun initAttributes()

    /**
     * 初始化view {@see getView()}
     *
     * @param view
     */
    abstract fun initViews(view: View)
}