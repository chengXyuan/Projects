package com.daking.lottery.dialog.nice

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.View

class NiceDialog : BaseDialog() {

    companion object {
        fun init() = NiceDialog()
    }

    private var convertListener: ViewConvertListener? = null

    override fun intLayoutId() = layoutId

    override fun convertView(view: View, dialog: BaseDialog) {
        if (convertListener != null) {
            convertListener!!.convertView(view, dialog)
        }
    }


    fun setLayoutId(@LayoutRes layoutId: Int): NiceDialog {
        this.layoutId = layoutId
        return this
    }

    fun setConvertListener(convertListener: ViewConvertListener): NiceDialog {
        this.convertListener = convertListener
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            convertListener = savedInstanceState.getParcelable("listener")
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.let { outState.putParcelable("listener", convertListener) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        convertListener = null
    }
}