package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ys.ui.R;
import com.ys.ui.base.BaseTimerActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangtao on 2016/7/4.
 */
public class YsActivity extends BaseTimerActivity implements View.OnClickListener {


    @Bind(R.id.iv_ys1)
    ImageView ivYs1;
    @Bind(R.id.iv_ys2)
    ImageView ivYs2;
    @Bind(R.id.iv_ys3)
    ImageView ivYs3;
    @Bind(R.id.iv_ys4)
    ImageView ivYs4;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_level1;
    }

    @Override
    protected void create(Bundle savedInstanceState) {

        ivYs1.setOnClickListener(this);
        ivYs2.setOnClickListener(this);
        ivYs3.setOnClickListener(this);
        ivYs4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_ys1:
                // 名厂名药
                finish();
//                startActivity(new Intent(this, YsMCMYActivity.class));
                startActivity(new Intent(this, YsMCMYActivity.class));

                break;
            case R.id.iv_ys2:
                // 用药人群
                finish();
                startActivity(new Intent(this, YsYYRQActivity.class));

                break;
            case R.id.iv_ys3:
                finish();
                // 人体部位
                startActivity(new Intent(this, YsRTBWActivity.class));

                break;
            case R.id.iv_ys4:
                finish();
                // 功效症状

                startActivity(new Intent(this, YsGNGXActivity.class));
                break;
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
                    startActivity(new Intent(YsActivity.this, HomeActivity.class));
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
