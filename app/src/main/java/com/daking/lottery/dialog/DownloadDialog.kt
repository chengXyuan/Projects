package com.daking.lottery.dialog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v4.content.ContextCompat
import android.text.format.Formatter
import android.view.View
import com.daking.lottery.R
import com.daking.lottery.api.DownloadClient
import com.daking.lottery.app.App
import com.daking.lottery.dialog.nice.BaseDialog
import com.daking.lottery.util.FileUtils
import com.daking.lottery.util.format
import com.daking.lottery.util.log
import com.international.wtw.lottery.event.ProgressListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.dialog_update.*
import kotlinx.android.synthetic.main.dialog_update.view.*
import okhttp3.ResponseBody
import java.io.File
import java.io.IOException


class DownloadDialog : BaseDialog() {

    private lateinit var downloadUrl: String
    private val apkFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "${App.instance.packageName}.apk")
    private var mOnClickLaterListener: OnClickLaterListener? = null

    companion object {
        fun newInstance(url: String): DownloadDialog {
            val bundle = Bundle()
            bundle.putString("download_url", url)
            val downloadDialog = DownloadDialog()
            downloadDialog.arguments = bundle
            return downloadDialog
        }
    }


    fun setOnClickLaterListener(onClickLaterListener: OnClickLaterListener): DownloadDialog {
        mOnClickLaterListener = onClickLaterListener
        return this
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
        view.tvLater.setOnClickListener {
            dismiss()
            mOnClickLaterListener?.let { mOnClickLaterListener!!.onLater() }
        }
        view.tvReDownload.setOnClickListener { actionDownload(view) }
        //自动开始下载
        actionDownload(view)
    }

    private fun actionDownload(view: View) {
        DownloadClient(object : ProgressListener {
            override fun update(total: Long, contentLength: Long, done: Boolean) {
                log("total=$total, current=$contentLength")
                progress.progress = (1f * contentLength / total).toInt()
                val totalSize = Formatter.formatFileSize(activity, total)
                val currSize = Formatter.formatFileSize(activity, contentLength)
                view.tvDownloadedSize.text = "$currSize/$totalSize"
                view.tvPercent.text = (1f * contentLength / total).format()
            }
        }).getApiStore().download(downloadUrl)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map { t: ResponseBody -> t.byteStream() }
                .observeOn(Schedulers.computation())
                .doOnNext { inputStream ->
                    try {
                        //保存文件
                        FileUtils.instance.writeFile(inputStream, apkFile)
                    } catch (e: IOException) {
                        throw RuntimeException(e.message)
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //onNext
                }, {
                    //onError
                    view.tvTitleHint.text = "下载失败,请联系客服下载最新版本"
                    view.tvTitleHint.setTextColor(ContextCompat.getColor(App.instance, R.color.red500))
                    view.llBottom.visibility = View.VISIBLE
                }, {
                    //onComplete
                    view.tvTitleHint.text = "下载完成"
                    dismiss()
                    installApk()
                })
    }

    /**
     * 安装APK
     */
    private fun installApk() {
        if (!apkFile.exists()) {
            return
        }
        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.setDataAndType(Uri.parse("file://" + apkFile.toString()),
                "application/vnd.android.package-archive")
        activity.startActivity(intent)
    }

    interface OnClickLaterListener {
        fun onLater()
    }
}