package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.ys.ui.R;
import com.ys.ui.base.BaseActivity;

import butterknife.Bind;

public class ProductDetailActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.btn_buy_wx)
    ImageButton btnWx;

    @Bind(R.id.btn_buy_zfb)
    ImageButton btnZfb;

    @Bind(R.id.btn_yl)
    ImageButton btnYl;

    @Bind(R.id.btn_sf)
    ImageButton btnSf;

    @Bind(R.id.btn_back_home)
    ImageButton btnBackHome;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy_1;
    }

    @Override
    protected void create(Bundle savedInstanceState) {


        btnBackHome.setOnClickListener(this);
        btnWx.setOnClickListener(this);
        btnZfb.setOnClickListener(this);
        btnYl.setOnClickListener(this);
        btnSf.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.btn_buy_wx:

                break;
            case R.id.btn_buy_zfb:

                break;
            case R.id.btn_yl:

                break;
            case R.id.btn_sf:

                break;

            case R.id.btn_back_home:
                finish();
                startActivity(new Intent(this, HomeActivity.class));
                break;
            default:
                break;

        }
    }
}
