package com.ys.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ys.ui.R;
import com.ys.ui.activity.HomeActivity;
import com.ys.ui.utils.PropertyUtils;

import java.math.BigDecimal;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {


    protected int minute;
    protected int second;

    private Window window;
    private Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        window.setAttributes(params);
        getIntent(savedInstanceState);
        setContentView(getLayoutId());

        ButterKnife.bind(this);
        mContext = this;
        create(savedInstanceState);
        backHome();
    }
    protected void backHome() {
        if(findViewById(R.id.btn_back_home)!=null){
            findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(mContext, HomeActivity.class));
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        resetTimeOut();
    }

    public void close(View v){
        finish();
    }

    protected void resetTimeOut() {
        int timeout = PropertyUtils.getInstance().getTransTimeout();
        minute = timeout / 60;
        second = timeout % 60;

    }

    protected String getPrice(Long price) {

        if (price == null) {
            return "0";
        }
        return new BigDecimal(price).divide(new BigDecimal(100)).setScale(2).toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    protected void getIntent(Bundle savedInstanceState){};

    protected abstract int getLayoutId();

    protected abstract void create(Bundle savedInstanceState);

    protected Context getCtx() {
        return App.getContext();
    }

    protected String getMcNo() {
        return App.mcNo;
    }

}
