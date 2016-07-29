package com.ys.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
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

    @Bind(R.id.pb_loading)
    ContentLoadingProgressBar mPbLoading;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void create(Bundle savedInstanceState) {

        minute = 10;
        second =0;
        webView.loadUrl("http://www.bayyw.com/app/index.html");
       //webView.loadUrl("http://112.74.69.111:8080/index.php");
       // webView.loadUrl("http://www.baidu.com");

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageFinished(WebView view,String url)
            {
                hideProgress();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showProgress();
            }
        });


    }
    @Override
    protected void backHome() {
        if(findViewById(R.id.btn_back_home)!=null){
            findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(MallWebviewActivity.this, HomeActivity.class));
                }
            });
        }
    }


    public void showProgress() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        mPbLoading.setVisibility(View.GONE);
    }


}
