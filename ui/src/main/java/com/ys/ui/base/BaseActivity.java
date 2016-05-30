package com.ys.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ys.ui.R;
import com.ys.ui.activity.HomeActivity;
import com.ys.ui.activity.ProductActivity;

import java.math.BigDecimal;

import butterknife.ButterKnife;

public abstract class BaseActivity extends Activity {


    protected int minute;
    protected int second;

    private Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mContext = this;
        create(savedInstanceState);
        backHome();

    }

    protected boolean isInit() {
        if (App.mcNo == null) {
            return false;
        }
        return true;
    }

    protected void backHome() {
        if (findViewById(R.id.btn_back_home) != null) {
            findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(mContext, ProductActivity.class));
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void close(View v) {
        finish();
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


    protected abstract int getLayoutId();

    protected abstract void create(Bundle savedInstanceState);

    protected Context getCtx() {
        return App.getContext();
    }

    protected String getMcNo() {
        return App.mcNo;
    }

}
