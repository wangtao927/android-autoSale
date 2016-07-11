package com.ys.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ys.ui.R;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.base.BaseTimerActivity;

import butterknife.Bind;

/**
 * Created by wangtao on 2016/7/1.
 */
public class MallWebviewActivity extends BaseTimerActivity {

    @Bind(R.id.webView)
   WebView webView;

    int minute = 5;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void create(Bundle savedInstanceState) {

        webView.loadUrl("http://www.bayyw.com/app/index.html");
       // webView.loadUrl("http://www.baidu.com");

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }
}
