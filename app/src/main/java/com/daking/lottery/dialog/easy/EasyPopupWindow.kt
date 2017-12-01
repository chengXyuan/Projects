@file:Suppress("UNCHECKED_CAST")

package com.daking.lottery.dialog.easy

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.annotation.*
import android.support.v4.widget.PopupWindowCompat
import android.transition.Transition
import android.view.*
import android.widget.PopupWindow

open class EasyPopupWindow(context: Context) : PopupWindow.OnDismissListener {

    private val mContext = context
    //布局id
    private var mLayoutId = 0
    private var mContentView: View? = null
    private var mAnchorView: View? = null
    private var mPopupWindow = PopupWindow()
    private var mDimView: ViewGroup? = null

    //是否可以获取焦点
    private var mFocusable = true
    //是否点击外部消失
    private var mOutsideTouchable = true
    //是否可以点击PopupWindow之外的地方dismiss
    private var mFocusAndOutsideEnable = true
    //是否背景变暗
    private var isBackgroundDim = false
    //getViewTreeObserver监听时 是否只获取宽高
    private var isOnlyGetWH = true

    //背景变暗颜色
    @ColorInt
    private var mDimColor = Color.BLACK
    //背景变暗的不透明度
    private var mDimValue = 0.5f
    private var mWidth = 0//宽
    private var mHeight = 0//高
    private var mOffsetX = 0//x轴偏移
    private var mOffsetY = 0//y轴偏移
    private var mAnimationStyle = 0//动画

    //进入动画
    private var mEnterTransition: Transition? = null
    //退出动画
    private var mExitTransition: Transition? = null
    //消失监听
    private var mOnDismissListener: PopupWindow.OnDismissListener? = null
    //自定义的监听 用于获取准确的PopupWindow宽高，可以重新设置偏移量
    private var mOnAttachedWindowListener: OnAttachedWindowListener? = null
    //竖直方向对其
    private var mVerticalGravity = VerticalGravity.BELOW
    //水平方向对其
    private var mHorizontalGravity = HorizontalGravity.LEFT

    fun <T : EasyPopupWindow> createPopup(): T {
        onPopupWindowCreated()

        if (mContentView == null) {
            mContentView = if (mLayoutId != 0) {
                LayoutInflater.from(mContext).inflate(mLayoutId, null)
            } else {
                throw IllegalArgumentException("The content view is null")
            }
        }

        mPopupWindow.contentView = mContentView

        with(mPopupWindow) {
            width = if (mWidth != 0) mWidth else ViewGroup.LayoutParams.WRAP_CONTENT
            height = if (mHeight != 0) mHeight else ViewGroup.LayoutParams.WRAP_CONTENT

            onPopupWindowViewCreated(mContentView!!)

            if (mAnimationStyle != 0) animationStyle = mAnimationStyle

            if (!mFocusAndOutsideEnable) {
                //from https://github.com/pinguo-zhouwei/CustomPopwindow
                isFocusable = true
                isOutsideTouchable = false
                setBackgroundDrawable(null)
                //注意下面这三个是contentView 不是PopupWindow，响应返回按钮事件
                contentView.isFocusable = true
                contentView.isFocusableInTouchMode = true
                contentView.setOnKeyListener { _, keyCode, _ ->
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        dismiss()
                        return@setOnKeyListener true
                    }
                    return@setOnKeyListener false
                }

                //在Android 6.0以上 ，只能通过拦截事件来解决
                setTouchInterceptor { _, event ->
                    val x = event.x
                    val y = event.y

                    return@setTouchInterceptor (event.action == MotionEvent.ACTION_DOWN
                            && ((x < 0) || (x >= mWidth) || (y < 0) || (y >= mHeight)))
                            || event.action == MotionEvent.ACTION_OUTSIDE
                }
            } else {
                isFocusable = mFocusable
                isOutsideTouchable = mOutsideTouchable
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            setOnDismissListener(this@EasyPopupWindow)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mEnterTransition?.let { enterTransition = mEnterTransition }
                mExitTransition?.let { exitTransition = mExitTransition }
            }
        }

        return this as T
    }

    open fun onPopupWindowViewCreated(contentView: View) {}

    open fun onPopupWindowCreated() {}

    open fun onPopupWindowDismiss() {}

    /****设置属性方法****/

    fun setContentView(contentView: View): EasyPopupWindow {
        this.mContentView = contentView
        this.mLayoutId = 0
        return this
    }


    fun setContentView(@LayoutRes layoutId: Int): EasyPopupWindow {
        this.mContentView = null
        this.mLayoutId = layoutId
        return this
    }

    fun setContentView(contentView: View, width: Int, height: Int): EasyPopupWindow {
        this.mContentView = contentView
        this.mLayoutId = 0
        this.mWidth = width
        this.mHeight = height
        return this
    }

    fun setContentView(@LayoutRes layoutId: Int, width: Int, height: Int): EasyPopupWindow {
        this.mContentView = null
        this.mLayoutId = layoutId
        this.mWidth = width
        this.mHeight = height
        return this
    }

    fun setWidth(width: Int): EasyPopupWindow {
        this.mWidth = width
        return this
    }

    fun setHeight(height: Int): EasyPopupWindow {
        this.mHeight = height
        return this
    }

    fun setAnchorView(view: View): EasyPopupWindow {
        this.mAnchorView = view
        return this
    }

    fun setVerticalGravity(verticalGravity: VerticalGravity): EasyPopupWindow {
        this.mVerticalGravity = verticalGravity
        return this
    }

    fun setHorizontalGravity(horizontalGravity: HorizontalGravity): EasyPopupWindow {
        this.mHorizontalGravity = horizontalGravity
        return this
    }

    fun setOffsetX(offsetX: Int): EasyPopupWindow {
        this.mOffsetX = offsetX
        return this
    }

    fun setOffsetY(offsetY: Int): EasyPopupWindow {
        this.mOffsetY = offsetY
        return this
    }

    fun setAnimationStyle(@StyleRes animationStyle: Int): EasyPopupWindow {
        this.mAnimationStyle = animationStyle
        return this
    }

    fun setFocusable(focusable: Boolean): EasyPopupWindow {
        this.mFocusable = focusable
        return this
    }

    fun setOutsideTouchable(outsideTouchable: Boolean): EasyPopupWindow {
        this.mOutsideTouchable = outsideTouchable
        return this
    }

    /**
     * 是否可以点击PopupWindow之外的地方dismiss
     *
     * @param focusAndOutsideEnable
     * @return
     */
    fun setFocusAndOutsideEnable(focusAndOutsideEnable: Boolean): EasyPopupWindow {
        this.mFocusAndOutsideEnable = focusAndOutsideEnable
        return this
    }

    /**
     * 背景变暗支持api>=18
     *
     * @param isDim
     * @return
     */
    fun setBackgroundDimEnable(isDim: Boolean): EasyPopupWindow {
        this.isBackgroundDim = isDim
        return this
    }

    fun setDimValue(@FloatRange(from = 0.0, to = 1.0) dimValue: Float): EasyPopupWindow {
        this.mDimValue = dimValue
        return this
    }

    fun setDimColor(@ColorInt color: Int): EasyPopupWindow {
        this.mDimColor = color
        return this
    }

    fun setDimView(dimView: ViewGroup): EasyPopupWindow {
        this.mDimView = dimView
        return this
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun setEnterTransition(enterTransition: Transition): EasyPopupWindow {
        this.mEnterTransition = enterTransition
        return this
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun setExitTransition(exitTransition: Transition): EasyPopupWindow {
        this.mExitTransition = exitTransition
        return this
    }

    /**
     * 使用此方法需要在创建的时候调用setAnchorView()等属性设置{@see setAnchorView()}
     */
    fun showAsDropDown() {
        if (mAnchorView != null) showAsDropDown(mAnchorView!!, mOffsetX, mOffsetY)
    }

    fun showAsDropDown(anchor: View, offsetX: Int, offsetY: Int) {
        isOnlyGetWH = true
        handleBackgroundDim()
        mAnchorView = anchor
        mOffsetX = offsetX
        mOffsetY = offsetY
        addGlobalLayoutListener(mPopupWindow.contentView)
        mPopupWindow.showAsDropDown(anchor, offsetX, offsetY)
    }

    fun showAsDropDown(anchor: View) {
        handleBackgroundDim()
        mAnchorView = anchor
        isOnlyGetWH = true
        addGlobalLayoutListener(mPopupWindow.contentView)
        mPopupWindow.showAsDropDown(anchor)
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    fun showAsDropDown(anchor: View, offsetX: Int, offsetY: Int, gravity: Int) {
        handleBackgroundDim()
        mAnchorView = anchor
        mOffsetX = offsetX
        mOffsetY = offsetY
        isOnlyGetWH = true
        addGlobalLayoutListener(mPopupWindow.contentView)
        PopupWindowCompat.showAsDropDown(mPopupWindow, anchor, offsetX, offsetY, gravity)
    }

    fun showAtLocation(parent: View, gravity: Int, offsetX: Int, offsetY: Int) {
        handleBackgroundDim()
        mAnchorView = parent
        mOffsetX = offsetX
        mOffsetY = offsetY
        isOnlyGetWH = true
        addGlobalLayoutListener(mPopupWindow.contentView)
        mPopupWindow.showAtLocation(parent, gravity, offsetX, offsetY)
    }

    /**
     * 相对anchor view显示
     *
     * 使用此方法需要在创建的时候调用setAnchorView()等属性设置{@see setAnchorView()}
     *
     * 注意：如果使用 VerticalGravity 和 HorizontalGravity 时，请确保使用之后 PopupWindow 没有超出屏幕边界，
     * 如果超出屏幕边界，VerticalGravity 和 HorizontalGravity 可能无效，从而达不到你想要的效果。
     */
    fun showAtAnchorView() {
        if (mAnchorView == null) {
            return
        }
        showAtAnchorView(mAnchorView!!, mVerticalGravity, mHorizontalGravity)
    }

    /**
     * 相对anchor view显示，适用 宽高不为match_parent
     *
     * 注意：如果使用 VerticalGravity 和 HorizontalGravity 时，请确保使用之后 PopupWindow 没有超出屏幕边界，
     * 如果超出屏幕边界，VerticalGravity 和 HorizontalGravity 可能无效，从而达不到你想要的效果。
     */
    fun showAtAnchorView(anchor: View, vertGravity: VerticalGravity, horizGravity: HorizontalGravity) {
        showAtAnchorView(anchor, vertGravity, horizGravity, 0, 0)
    }

    /**
     * 相对anchor view显示，适用 宽高不为match_parent
     *
     * 注意：如果使用 VerticalGravity 和 HorizontalGravity 时，请确保使用之后 PopupWindow 没有超出屏幕边界，
     * 如果超出屏幕边界，VerticalGravity 和 HorizontalGravity 可能无效，从而达不到你想要的效果。
     *
     * @param anchor
     * @param vertGravity  垂直方向的对齐方式
     * @param horizGravity 水平方向的对齐方式
     * @param x            水平方向的偏移
     * @param y            垂直方向的偏移
     */
    fun showAtAnchorView(anchor: View, vertGravity: VerticalGravity, horizGravity: HorizontalGravity, x: Int, y: Int) {
        mAnchorView = anchor
        mOffsetX = x
        mOffsetY = y
        mVerticalGravity = vertGravity
        mHorizontalGravity = horizGravity
        isOnlyGetWH = false
        //处理背景变暗
        handleBackgroundDim()
        val contentView = mPopupWindow.contentView
        addGlobalLayoutListener(contentView)
        contentView!!.measure(0, View.MeasureSpec.UNSPECIFIED)
        val measuredW = contentView.measuredWidth
        val measuredH = contentView.measuredHeight

        val ox = calculateX(anchor, horizGravity, measuredW, x)
        val oy = calculateY(anchor, vertGravity, measuredH, y)
        PopupWindowCompat.showAsDropDown(mPopupWindow, anchor, ox, oy, Gravity.NO_GRAVITY)
    }

    /**
     * 根据垂直gravity计算y偏移
     */
    private fun calculateY(anchor: View, vertGravity: VerticalGravity, measuredH: Int, y: Int): Int {
        return when (vertGravity) {
            VerticalGravity.ABOVE -> y - (measuredH + anchor.height)
            VerticalGravity.ALIGN_BOTTOM -> y - measuredH
            VerticalGravity.CENTER -> y - (anchor.height / 2 + measuredH / 2)
            VerticalGravity.ALIGN_TOP -> y - anchor.height
            else -> y
        }
    }

    /**
     * 根据垂直gravity计算y偏移
     */
    private fun calculateX(anchor: View, horizGravity: HorizontalGravity, measuredW: Int, x: Int): Int {
        return when (horizGravity) {
            HorizontalGravity.LEFT -> x - measuredW
            HorizontalGravity.ALIGN_RIGHT -> x - (measuredW - anchor.width)
            HorizontalGravity.CENTER -> x + (anchor.width / 2 - measuredW / 2)
            HorizontalGravity.RIGHT -> x + anchor.width
            else -> x
        }
    }

    /**
     * 更新PopupWindow位置，校验PopupWindow位置
     * 修复高度或者宽度写死时或者内部有ScrollView时，弹出的位置不准确问题
     */
    private fun updateLocation(width: Int, height: Int, anchor: View, vertGravity: VerticalGravity,
                               horizGravity: HorizontalGravity, x: Int, y: Int) {
        val ox = calculateX(anchor, horizGravity, width, x)
        val oy = calculateY(anchor, vertGravity, height, y)
        mPopupWindow.update(anchor, ox, oy, width, height)
    }

    //监听器, 用于PopupWindow弹出时获取准确的宽高
    private val mOnGlobalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener
            = ViewTreeObserver.OnGlobalLayoutListener {
        mWidth = mPopupWindow.contentView.width
        mHeight = mPopupWindow.contentView.height
        //回调
        mOnAttachedWindowListener?.let {
            mOnAttachedWindowListener!!.onAttachedWindow(mWidth, mHeight, this@EasyPopupWindow)
        }
        //只获取宽高时，不执行更新操作
        if (isOnlyGetWH) {
            removeGlobalLayoutListener()
            return@OnGlobalLayoutListener
        }

        updateLocation(mWidth, mHeight, mAnchorView!!, mVerticalGravity, mHorizontalGravity, mOffsetX, mOffsetY)
        removeGlobalLayoutListener()
    }

    fun setOnDismissListener(listener: PopupWindow.OnDismissListener): EasyPopupWindow {
        this.mOnDismissListener = listener
        return this
    }

    fun setOnAttachedWindowListener(listener: OnAttachedWindowListener): EasyPopupWindow {
        this.mOnAttachedWindowListener = listener
        return this
    }

    /**
     * 处理背景变暗
     * https://blog.nex3z.com/2016/12/04/%E5%BC%B9%E5%87%BApopupwindow%E5%90%8E%E8%AE%A9%E8%83%8C%E6%99%AF%E5%8F%98%E6%9A%97%E7%9A%84%E6%96%B9%E6%B3%95/
     */
    @SuppressLint("ObsoleteSdkInt")
    private fun handleBackgroundDim() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (isBackgroundDim) {
                if (mDimView != null) {
                    applyDim(mDimView!!)
                } else {
                    val activity = mPopupWindow.contentView.context as Activity
                    applyDim(activity)
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun applyDim(activity: Activity) {
        //activity跟布局
        val rootView = activity.window.decorView.rootView
        val dim = ColorDrawable(mDimColor)
        dim.setBounds(0, 0, rootView.width, rootView.height)
        dim.alpha = (255 * mDimValue).toInt()

        rootView.overlay.add(dim)
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun applyDim(dimView: ViewGroup) {
        val dim = ColorDrawable(mDimColor)
        dim.setBounds(0, 0, dimView.width, dimView.height)
        dim.alpha = (255 * mDimValue).toInt()

        dimView.overlay.add(dim)
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun clearBackgroundDim() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (isBackgroundDim) {
                if (mDimView != null) {
                    clearDim(mDimView!!)
                } else {
                    val activity = mPopupWindow.contentView.context as Activity
                    clearDim(activity)
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun clearDim(activity: Activity) {
        val rootView = activity.window.decorView.rootView
        rootView.overlay.clear()
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun clearDim(dimView: ViewGroup) {
        dimView.overlay.clear()
    }

    fun getPopupWindow() = mPopupWindow

    fun <T : View> getView(@IdRes viewId: Int): T {
        return mPopupWindow.contentView.findViewById(viewId)
    }

    fun dismiss() {
        mPopupWindow.dismiss()
    }

    override fun onDismiss() {
        handleDismiss()
    }

    /**
     * PopupWindow消失后处理一些逻辑
     */
    private fun handleDismiss() {
        if (mOnDismissListener != null) {
            mOnDismissListener!!.onDismiss()
        }

        removeGlobalLayoutListener()
        //清除背景变暗
        clearBackgroundDim()
        if (mPopupWindow.isShowing) {
            mPopupWindow.dismiss()
        }

        onPopupWindowDismiss()
    }

    private fun addGlobalLayoutListener(contentView: View) {
        contentView.viewTreeObserver.addOnGlobalLayoutListener(mOnGlobalLayoutListener)
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun removeGlobalLayoutListener() {
        if (Build.VERSION.SDK_INT >= 16) {
            mPopupWindow.contentView.viewTreeObserver.removeOnGlobalLayoutListener(mOnGlobalLayoutListener)
        } else {
            mPopupWindow.contentView.viewTreeObserver.removeGlobalOnLayoutListener(mOnGlobalLayoutListener)
        }
    }

    interface OnAttachedWindowListener {
        /**
         * 在 show方法之后 updateLocation之前执行
         * @param width   PopupWindow准确的宽
         * @param height  PopupWindow准确的高
         */
        fun onAttachedWindow(width: Int, height: Int, easyPop: EasyPopupWindow)
    }
}
