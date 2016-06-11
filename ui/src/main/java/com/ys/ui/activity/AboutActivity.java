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
 * Created by wangtao on 2016/6/9.
 */
public class AboutActivity extends BaseTimerActivity implements View.OnClickListener{

   @Bind(R.id.logo)
   ImageView logo;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void create(Bundle savedInstanceState) {

        //

        logo.setOnClickListener(this);



    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.logo:

                Intent detailIntent = new Intent(this, AdminLoginActivity.class);
                finish();
                startActivity(detailIntent);
                break;

            default:

                break;
        }

    }

    protected void backHome() {
        if (findViewById(R.id.btn_back_home) != null) {
            findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(AboutActivity.this, HomeActivity.class));
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
