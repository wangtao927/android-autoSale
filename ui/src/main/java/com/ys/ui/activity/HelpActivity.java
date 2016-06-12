package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ys.ui.R;
import com.ys.ui.base.BaseTimerActivity;

import butterknife.ButterKnife;

/**
 * Created by wangtao on 2016/6/9.
 */
public class HelpActivity extends BaseTimerActivity {





    @Override
    protected int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    protected void create(Bundle savedInstanceState) {




    }

    protected void backHome() {
        if (findViewById(R.id.btn_back_home) != null) {
            findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(HelpActivity.this, HomeActivity.class));
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
