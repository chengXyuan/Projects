package com.daking.lottery.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.daking.lottery.R
import com.daking.lottery.base.BaseActivity
import com.daking.lottery.util.log
import im.delight.android.webview.AdvancedWebView
import kotlinx.android.synthetic.main.activity_web_view.*


class WebViewActivity : BaseActivity(), AdvancedWebView.Listener {

    companion object {
        val EXTRA_WEB_TITLE = "extra_web_title"
        val EXTRA_WEB_URL = "extra_web_url"
    }

    override fun getLayoutResId() = R.layout.activity_web_view

    override fun initData(savedInstanceState: Bundle?) {

        val title = intent.getStringExtra(EXTRA_WEB_TITLE)
        if (!title.isNullOrEmpty()) {
            titleBar.setTitleMainText(title)
        }
        titleBar.setOnLeftTextClickListener { onBackPressed() }

        val url = intent.getStringExtra(EXTRA_WEB_URL)
        webView.setListener(this, this)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
                try {
                    if (url.startsWith("weixin://") || url.startsWith("alipays://")) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                        return true
                    }
                } catch (e: Exception) {
                    //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    //没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
                    return true
                }
                return false
            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.proceed()
            }
        }
        webView.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                progressBar.progress = newProgress
            }
        }

        log(url)

        webView.loadUrl(url)
    }

    override fun onResume() {
        super.onResume()
        webView.onResume()
    }

    override fun onPause() {
        super.onPause()
        webView.onPause()
    }

    override fun onDestroy() {
        webView.onDestroy()
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (!webView.onBackPressed()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPageStarted(url: String?, favicon: Bitmap?) {
        progressBar.visibility = View.VISIBLE
    }

    override fun onPageFinished(url: String?) {
        progressBar.visibility = View.GONE
    }

    override fun onPageError(errorCode: Int, description: String?, failingUrl: String?) {
        progressBar.visibility = View.GONE
        log("errorCode: $errorCode, description: $description failingUrl: $failingUrl")
    }

    override fun onDownloadRequested(url: String?, suggestedFilename: String?, mimeType: String?, contentLength: Long, contentDisposition: String?, userAgent: String?) {

    }

    override fun onExternalPageRequest(url: String?) {

    }
}