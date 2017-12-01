package com.daking.lottery.dialog

import android.os.Bundle
import android.view.View
import com.daking.lottery.R
import com.daking.lottery.dialog.nice.BaseDialog
import kotlinx.android.synthetic.main.dialog_common.view.*

class CommonDialog : BaseDialog() {

    /**
     * 是否只有一个按钮(true:一个按钮, false:两个按钮)
     */
    private var isOneBtn = false
    private lateinit var msg: String
    private lateinit var rootView: View
    private var dialogListener: OnDialogListener? = null

    companion object {
        private val ONE_BUTTON = "one_button"
        private val MSG_CONTENT = "msg_content"
        fun init(msg: String, isOneBtn: Boolean = false): CommonDialog {
            val dialog = CommonDialog()
            val args = Bundle()
            args.putString(MSG_CONTENT, msg)
            args.putBoolean(ONE_BUTTON, isOneBtn)
            dialog.arguments = args
            return dialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isOneBtn = arguments.getBoolean(ONE_BUTTON)
        msg = arguments.getString(MSG_CONTENT)
    }

    override fun intLayoutId() = R.layout.dialog_common

    override fun convertView(view: View, dialog: BaseDialog) {
        rootView = view
        view.tv_msg.text = msg
        view.iv_close.setOnClickListener { dismiss() }
        view.tv_cancel.visibility = if (isOneBtn) View.GONE else View.VISIBLE
        if (!isOneBtn) {
            view.tv_cancel.setOnClickListener {
                if (dialogListener != null) {
                    dialogListener!!.onCancel(this)
                } else {
                    dismiss()
                }
            }
        }
        view.tv_confirm.setOnClickListener {
            if (dialogListener != null) {
                dialogListener!!.onConfirm(this)
            }
        }
    }

    fun setDialogListener(listener: OnDialogListener): CommonDialog {
        this.dialogListener = listener
        return this
    }

    interface OnDialogListener {

        fun onConfirm(dialog: CommonDialog)

        fun onCancel(dialog: CommonDialog) {
            dialog.dismiss()
        }
    }
}