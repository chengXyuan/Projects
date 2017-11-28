package com.example.july.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends Activity {
    private String mConn_url;
    private WebSettings setting;
    private WebView webView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        featureNoTitle();
        setContentView(R.layout.activity_web);
        this.webView = (WebView) findViewById(R.id.webView);
        this.mConn_url = "https://m.caitou999.com/";
        loadURLInAndroid();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.webView.destroy();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4 && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }
        finish();
        return super.onKeyDown(keyCode, event);
    }

    private void loadURLInAndroid() {
        webView.loadUrl(this.mConn_url);
        setting = this.webView.getSettings();
        webView.setHorizontalScrollbarOverlay(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.requestFocus();
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                setting.setDefaultTextEncodingName("GBK");
                setting.setJavaScriptEnabled(true);
                setting.setDefaultZoom(WebSettings.ZoomDensity.FAR);
                setting.setSupportZoom(false);
                setting.setBuiltInZoomControls(false);
                setting.setDisplayZoomControls(false);
                setting.setAppCacheEnabled(true);
                setting.setDomStorageEnabled(true);
                setting.setBlockNetworkImage(true);
                setting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                setting.setSavePassword(false);
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                setting.setBlockNetworkImage(false);
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.startsWith("http:") || url.startsWith("https:") ) {
                    return super.shouldOverrideUrlLoading(view, url);
                }else{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }
        });
        this.webView.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }

    private void featureNoTitle() {
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
    }

}
