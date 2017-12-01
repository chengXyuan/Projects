package com.daking.lottery.dialog.nice

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StyleRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.*
import com.daking.lottery.R
import com.daking.lottery.util.Utils

/**
 *
 */
abstract class BaseDialog : DialogFragment() {

    companion object {
        val MARGIN = "margin"
        val WIDTH = "width"
        val HEIGHT = "height"
        val DIM = "dim_amount"
        val BOTTOM = "show_bottom"
        val CANCEL = "out_cancel"
        val ANIM = "anim_style"
        val LAYOUT = "layout_id"
    }

    //左右边距(dp)
    private var margin = 0
    //宽度
    private var width = 0
    //高度
    private var height = 0
    //背景不透明度
    private var dimAmount = 0.5F
    //是否底部显示
    private var showBottom = false
    //是否点击外部消失
    private var outCancel = true

    @StyleRes
    var animStyle = 0

    @LayoutRes
    var layoutId = 0

    abstract fun intLayoutId(): Int

    abstract fun convertView(view: View, dialog: BaseDialog)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.NiceDialog)
        layoutId = intLayoutId()

        //恢复保存的数据
        if (savedInstanceState != null) {
            margin = savedInstanceState.getInt(MARGIN)
            width = savedInstanceState.getInt(WIDTH)
            height = savedInstanceState.getInt(HEIGHT)
            dimAmount = savedInstanceState.getFloat(DIM)
            showBottom = savedInstanceState.getBoolean(BOTTOM)
            outCancel = savedInstanceState.getBoolean(CANCEL)
            animStyle = savedInstanceState.getInt(ANIM)
            layoutId = savedInstanceState.getInt(LAYOUT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(layoutId, container, false)
        convertView(view!!, this)
        return view
    }

    override fun onStart() {
        super.onStart()
        initParams()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.let {
            with(outState) {
                putInt(MARGIN, margin)
                putInt(WIDTH, width)
                putInt(HEIGHT, height)
                putFloat(DIM, dimAmount)
                putBoolean(BOTTOM, showBottom)
                putBoolean(CANCEL, outCancel)
                putInt(ANIM, animStyle)
                putInt(LAYOUT, layoutId)
            }
        }
    }

    private fun initParams() {
        val window = dialog.window
        if (window != null) {
            val lp = window.attributes
            //调节灰色背景透明度[0-1]，默认0.5f
            lp.dimAmount = dimAmount
            //是否在底部显示
            if (showBottom) {
                lp.gravity = Gravity.BOTTOM
            }

            //设置dialog宽度
            lp.width = when (width) {
                0 -> Utils.getScreenWidth(context) - 2 * Utils.dp2px(margin)
                -1 -> WindowManager.LayoutParams.WRAP_CONTENT
                else -> Utils.dp2px(width)
            }

            //设置dialog高度
            lp.height = if (height == 0) {
                WindowManager.LayoutParams.WRAP_CONTENT
            } else {
                Utils.dp2px(height)
            }

            //设置dialog进入/退出动画
            if (animStyle != 0) window.setWindowAnimations(animStyle)

            //将以上属性设置到dialog的window
            window.attributes = lp
        }
        isCancelable = outCancel
    }

    fun setMargin(margin: Int): BaseDialog {
        this.margin = margin
        return this
    }

    fun setWidth(width: Int): BaseDialog {
        this.width = width
        return this
    }

    fun setHeight(height: Int): BaseDialog {
        this.height = height
        return this
    }

    fun setDimAmount(dimAmount: Float): BaseDialog {
        this.dimAmount = dimAmount
        return this
    }

    fun setShowBottom(showBottom: Boolean): BaseDialog {
        this.showBottom = showBottom
        return this
    }

    fun setOutCancel(outCancel: Boolean): BaseDialog {
        this.outCancel = outCancel
        return this
    }

    fun setAnimStyle(@StyleRes animStyle: Int): BaseDialog {
        this.animStyle = animStyle
        return this
    }

    fun show(manager: FragmentManager): BaseDialog {
        val ft = manager.beginTransaction()
        if (this.isAdded) {
            ft.remove(this)
        }
        ft.add(this, System.currentTimeMillis().toString())
        ft.commitAllowingStateLoss()
        return this
    }

}