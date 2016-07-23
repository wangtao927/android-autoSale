package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ys.ui.R;
import com.ys.ui.adapter.GalleryAdapter;
import com.ys.ui.base.BaseTimerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangtao on 2016/6/9.
 */
public class VipActivity extends BaseTimerActivity implements View.OnClickListener {


    @Bind(R.id.iv_hyhg)
    ImageView mjfhg;

    @Bind(R.id.iv_hycj)
    ImageView mjfcj;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vip;
    }

    @Override
    protected void create(Bundle savedInstanceState) {

        mjfcj.setOnClickListener(this);
        mjfhg.setOnClickListener(this);

    }



    protected void backHome() {
        if (findViewById(R.id.btn_back_home) != null) {
            findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(VipActivity.this, HomeActivity.class));
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_hyhg:

                finish();
                startActivity(new Intent(VipActivity.this, ScoreListActivity.class));

                break;
            case R.id.iv_hycj:
                finish();
                startActivity(new Intent(VipActivity.this, ZhuanPanActivity.class));
                break;

        }
    }
}
