package com.daking.lottery.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import com.daking.lottery.R
import com.daking.lottery.dialog.nice.BaseDialog
import kotlinx.android.synthetic.main.dialog_loading.view.*


class LoadingDialog : BaseDialog() {

    private var message: String? = null

    override fun intLayoutId() = R.layout.dialog_loading

    override fun convertView(view: View, dialog: BaseDialog) {
        if (message.isNullOrEmpty()) {
            view.tvMsg.visibility = View.GONE
        } else {
            view.tvMsg.visibility = View.VISIBLE
            view.tvMsg.text = message
        }
    }

    fun setText(msg: String?): LoadingDialog {
        message = msg
        return this
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }
}