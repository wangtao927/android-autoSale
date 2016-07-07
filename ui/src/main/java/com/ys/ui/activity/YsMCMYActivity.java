package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ys.ui.R;
import com.ys.ui.base.BaseTimerActivity;

import butterknife.ButterKnife;

/**
 * 名厂名药
 * Created by wangtao on 2016/7/8.
 */
public class YsMCMYActivity extends BaseTimerActivity implements View.OnClickListener {



    @Override
    protected int getLayoutId() {
        return R.layout.level2_mcmy;
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
                    startActivity(new Intent(YsMCMYActivity.this, HomeActivity.class));
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
