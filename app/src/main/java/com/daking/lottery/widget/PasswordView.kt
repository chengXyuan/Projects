package com.daking.lottery.widget

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.text.InputType
import android.util.AttributeSet
import android.util.TypedValue
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputMethodManager
import com.daking.lottery.R
import com.daking.lottery.util.Utils
import org.jetbrains.anko.collections.forEachWithIndex
import java.util.*
import kotlin.collections.ArrayList


class PasswordView : View {

    private var passwordLength = 6
    private var passwordSpace = 0
    private var pTextColor = 0
    private var pTextSize = 16f
    @DrawableRes
    private var filledItemBackground = 0
    private var unfilledItemBackground = 0
    private var isCursorEnable = true
    private var cursorColor = 0
    private var cursorFlashTime = 600
    private var cipherEnable = true
    private var cipherText = "*"

    //密码框宽度
    private var pWidth = 0
    //密码框高度
    private var pHeight = 0
    //光标是否显示
    private var isCursorShowing = false
    //是否输入完成
    private var isInputComplete = false

    private var password = ArrayList<String>()
    private lateinit var inputManager: InputMethodManager
    private val timer = Timer()
    private lateinit var paint: Paint
    private lateinit var timerTask: TimerTask

    private var passwordListener: PasswordListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        readAttribute(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        readAttribute(context, attrs)
    }

    fun getPassword() = password.joinToString("")

    private fun readAttribute(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typeArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordView)
            passwordLength = typeArray.getInt(R.styleable.PasswordView_passwordLength, 6)
            passwordSpace = typeArray.getDimension(R.styleable.PasswordView_passwordSpace,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, resources.displayMetrics)).toInt()
            pTextColor = typeArray.getColor(R.styleable.PasswordView_pTextColor, Color.GRAY)
            pTextSize = typeArray.getDimension(R.styleable.PasswordView_pTextSize,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14f, resources.displayMetrics))
            filledItemBackground = typeArray.getResourceId(R.styleable.PasswordView_filledItemBackground, 0)
            unfilledItemBackground = typeArray.getResourceId(R.styleable.PasswordView_unfilledItemBackground, 0)
            isCursorEnable = typeArray.getBoolean(R.styleable.PasswordView_isCursorEnable, true)
            cursorColor = typeArray.getColor(R.styleable.PasswordView_cursorColor, Color.GRAY)
            cursorFlashTime = typeArray.getInt(R.styleable.PasswordView_cursorFlashTime, 600)
            cipherEnable = typeArray.getBoolean(R.styleable.PasswordView_cipherEnable, true)
            typeArray.recycle()
        }

        init(context)
    }

    fun init(context: Context) {
        inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        isFocusableInTouchMode = true
        setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                when (keyCode) {
                    KeyEvent.KEYCODE_DEL -> {
                        //删除操作
                        if (password.isEmpty()) {
                            return@setOnKeyListener true
                        }
                        password.removeAt(password.size - 1)
                        isInputComplete = false
                        postInvalidate()
                        return@setOnKeyListener true
                    }
                    in KeyEvent.KEYCODE_0..KeyEvent.KEYCODE_9 -> {
                        //是数字
                        if (isInputComplete) {
                            return@setOnKeyListener true
                        }
                        add((keyCode - 7).toString())
                        postInvalidate()
                        return@setOnKeyListener true
                    }
                }
            }
            false
        }

        paint = Paint()
        paint.isAntiAlias = true
        timerTask = object : TimerTask() {
            override fun run() {
                isCursorEnable = !isCursorEnable
                postInvalidate()
            }
        }
    }

    /**
     * 增加
     */
    private fun add(c: String) {
        if (password.size <= passwordLength) {
            password.add(c)
            if (password.size == passwordLength) {
                isInputComplete = true
                if (passwordListener != null) {
                    passwordListener!!.passwordComplete(password.joinToString(""))
                }
                inputManager.hideSoftInputFromWindow(windowToken, 0)
            }
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        //密码框大小等于 (宽度 - 密码框间距 *(密码位数 - 1)) / 密码位数
        pWidth = (width - (passwordLength - 1) * passwordSpace) / passwordLength
        pHeight = height
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //绘制密码框
        drawRect(canvas, paint)
        //绘制光标
        drawCursor(canvas, paint)
        //绘制文本
        drawCipherText(canvas, paint)
    }

    private fun drawRect(canvas: Canvas, paint: Paint) {
        val fillBitmap = getBitmap(filledItemBackground)
        val unfillBitmap = getBitmap(unfilledItemBackground)
        for (i in 0 until passwordLength) {
            val startX = paddingLeft + (pWidth + passwordSpace) * i
            val startY = paddingTop
            val stopX = paddingLeft + (pWidth + passwordSpace) * i + pWidth
            val stopY = paddingTop + height
            val rect = Rect(startX, startY, stopX, stopY)
            if (i <= password.size && hasFocus()) {
                val src = Rect(0, 0, fillBitmap.width, fillBitmap.height)
                canvas.drawBitmap(fillBitmap, src, rect, paint)
            } else {
                val src = Rect(0, 0, unfillBitmap.width, unfillBitmap.height)
                canvas.drawBitmap(unfillBitmap, src, rect, paint)
            }
        }
    }

    private fun drawCursor(canvas: Canvas, paint: Paint) {
        //画笔初始化
        paint.color = cursorColor
        paint.strokeWidth = Utils.dp2px(1.5f)
        paint.style = Paint.Style.FILL
        val cursorPosition = password.size
        //光标未显示 && 开启光标 && 输入位数未满 && 获得焦点
        if (!isCursorShowing && isCursorEnable && !isInputComplete && hasFocus()) {
            // 起始点x = paddingLeft + 单个密码框大小 / 2 + (单个密码框大小 + 密码框间距) * 光标下标
            // 起始点y = paddingTop + (单个密码框大小 - 光标大小) / 2
            // 终止点x = 起始点x
            // 终止点y = 起始点y + 光标高度
            canvas.drawLine((paddingLeft + pWidth / 2 + (pWidth + passwordSpace) * cursorPosition).toFloat(),
                    (paddingTop + (height - pTextSize) / 2),
                    (paddingLeft + pWidth / 2 + (pWidth + passwordSpace) * cursorPosition).toFloat(),
                    (paddingTop + (height + pTextSize) / 2),
                    paint)
        }
    }

    private fun drawCipherText(canvas: Canvas, paint: Paint) {
        //画笔初始化
        paint.color = pTextColor
        paint.textSize = pTextSize
        paint.textAlign = Paint.Align.CENTER
        paint.style = Paint.Style.FILL
        //文字居中的处理
        val r = Rect()
        canvas.getClipBounds(r)
        val cHeight = r.height()
        paint.getTextBounds(cipherText, 0, cipherText.length, r)
        val y = cHeight / 2f + r.height() / 2f - r.bottom
        //根据输入的密码位数，进行for循环绘制
        password.forEachWithIndex { index, it ->
            // x = paddingLeft + 单个密码框大小/2 + ( 密码框大小 + 密码框间距 ) * i
            // y = paddingTop + 文字居中所需偏移量
            if (cipherEnable) {
                //没有开启明文显示，绘制密码密文
                canvas.drawText(cipherText,
                        (paddingLeft + pWidth / 2 + (pWidth + passwordSpace) * index).toFloat(),
                        paddingTop + y, paint)
            } else {
                //明文显示，直接绘制密码
                canvas.drawText(it,
                        (paddingLeft + pWidth / 2 + (pWidth + passwordSpace) * index).toFloat(),
                        paddingTop + y, paint)
            }
        }
    }

    private fun getBitmap(@DrawableRes resId: Int): Bitmap {
        val drawable = ContextCompat.getDrawable(context, resId)
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(pWidth, pHeight, Bitmap.Config.ARGB_8888)
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, pWidth, pHeight)
        drawable.draw(canvas)
        return bitmap
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                requestFocus()
                inputManager.showSoftInput(this, InputMethodManager.SHOW_FORCED)
                true
            }
            else -> super.onTouchEvent(event)
        }
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (!hasWindowFocus) {
            inputManager.hideSoftInputFromWindow(windowToken, 0)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        //cursorFlashTime为光标闪动的间隔时间
        timer.scheduleAtFixedRate(timerTask, 0L, cursorFlashTime.toLong())
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        timer.cancel()
    }

    override fun onCreateInputConnection(outAttrs: EditorInfo?): InputConnection? {
        outAttrs?.let { outAttrs.inputType = InputType.TYPE_CLASS_NUMBER }
        return super.onCreateInputConnection(outAttrs)
    }

    fun setPasswordListener(listener: PasswordListener) {
        this.passwordListener = listener
    }

    interface PasswordListener {
        fun passwordComplete(password: String)
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable("superState", super.onSaveInstanceState())
        bundle.putString("password", password.joinToString())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is Bundle) {
            password = state.getString("password").split(",") as ArrayList<String>
            super.onRestoreInstanceState(state.getParcelable<Parcelable>("superState"))
        }
        super.onRestoreInstanceState(state)
    }
}