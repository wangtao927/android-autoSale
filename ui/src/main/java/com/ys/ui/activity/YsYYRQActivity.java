package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ys.ui.R;
import com.ys.ui.base.BaseTimerActivity;

import butterknife.ButterKnife;

/**
 * 用药人群
 * Created by wangtao on 2016/7/4.
 */
public class YsYYRQActivity extends BaseTimerActivity implements View.OnClickListener {



    @Override
    protected int getLayoutId() {
        return R.layout.level2_yyrq;
    }

    @Override
    protected void create(Bundle savedInstanceState) {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }

    }

    @Override
    protected void backHome() {
        if(findViewById(R.id.btn_back_home)!=null){
            findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(YsYYRQActivity.this, HomeActivity.class));
                }
            });
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

    }

}
