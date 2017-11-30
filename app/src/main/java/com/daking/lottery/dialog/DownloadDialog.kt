package com.daking.lottery.dialog

import android.os.Bundle
import android.view.View
import com.daking.lottery.R
import com.daking.lottery.dialog.nice.BaseDialog
import kotlinx.android.synthetic.main.dialog_update.*


class DownloadDialog : BaseDialog() {

    private lateinit var downloadUrl: String

    companion object {
        fun newInstance(url: String): DownloadDialog {
            val bundle = Bundle()
            bundle.putString("download_url", url)
            val downloadDialog = DownloadDialog()
            downloadDialog.arguments = bundle
            return downloadDialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        downloadUrl = arguments.getString("download_url")
        if (downloadUrl.isNotEmpty() && !downloadUrl.startsWith("http")) {
            downloadUrl = "http://$downloadUrl"
        }
    }

    override fun intLayoutId() = R.layout.dialog_update

    override fun convertView(view: View, dialog: BaseDialog) {
        //设置点击事件
        tv_later_besides.setOnClickListener { dismiss() }
        tv_re_download.setOnClickListener { actionDownload() }
        //自动开始下载
        actionDownload()
    }

    private fun actionDownload() {

    }
}