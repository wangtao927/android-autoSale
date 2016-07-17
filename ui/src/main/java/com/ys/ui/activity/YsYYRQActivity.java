package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ys.ui.R;
import com.ys.ui.base.BaseTimerActivity;
import com.ys.ui.common.constants.GngxEnum;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 用药人群
 * Created by wangtao on 2016/7/4.
 */
public class YsYYRQActivity extends BaseTimerActivity implements View.OnClickListener {


    @Bind(R.id.iv_cryy)
    ImageView miVcryy;

    @Bind(R.id.iv_etyy)
    ImageView miVetyy;

    @Bind(R.id.iv_nxyy)
    ImageView mnxyy;
    @Bind(R.id.iv_nryy)
    ImageView mnryy;

    @Override
    protected int getLayoutId() {
        return R.layout.level2_yyrq;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
        miVcryy.setOnClickListener(this);
        miVetyy.setOnClickListener(this);
        mnxyy.setOnClickListener(this);
        mnryy.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(YsYYRQActivity.this, YsDetailActivity.class);
        switch (v.getId()) {

            case R.id.iv_cryy:
                finish();

                intent.putExtra("index", 201);
                intent.putExtra("desc", "老人用药");
                startActivity(intent);
                break;
            case R.id.iv_etyy:
                finish();
                intent.putExtra("index", 202);
                intent.putExtra("desc", "儿童用药");
                startActivity(intent);
                break;
            case R.id.iv_nxyy:
                finish();
                intent.putExtra("index", 203);
                intent.putExtra("desc", "男性用药");
                startActivity(intent);
                break;
            case R.id.iv_nryy:
                finish();
                intent.putExtra("index", 204);
                intent.putExtra("desc", "女性用药");
                startActivity(intent);
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
