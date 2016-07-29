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



    @Bind(R.id.iv_jfhg)
    ImageView ivJfhg;
    @Bind(R.id.iv_mfzx)
    ImageView ivMfzx;
    @Bind(R.id.iv_hyth)
    ImageView ivHyth;
    @Bind(R.id.iv_hycj)
    ImageView ivHycj;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vip;
    }

    @Override
    protected void create(Bundle savedInstanceState) {

        ivJfhg.setOnClickListener(this);
        ivMfzx.setOnClickListener(this);
        ivHyth.setOnClickListener(this);
        ivHycj.setOnClickListener(this);

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
        Intent intent = new Intent(VipActivity.this, VipBuyActivity.class);
        switch (v.getId()) {
            case R.id.iv_jfhg:

                intent.putExtra("index", 1);

                break;
            case R.id.iv_mfzx:

                intent.putExtra("index", 2);


                break;
            case R.id.iv_hyth:

                intent.putExtra("index", 3);

                break;

            case R.id.iv_hycj:

                intent = new Intent(VipActivity.this, ZhuanPanActivity.class);
                break;

        }
        finish();

        startActivity(intent);
    }
}
