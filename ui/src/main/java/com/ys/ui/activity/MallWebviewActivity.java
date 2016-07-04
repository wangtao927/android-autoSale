package com.ys.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ys.ui.R;
import com.ys.ui.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by wangtao on 2016/7/1.
 */
public class MallWebviewActivity extends BaseActivity {

    @Bind(R.id.webView)
   WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void create(Bundle savedInstanceState) {

        webView.loadUrl("http://www.baidu.com");

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
