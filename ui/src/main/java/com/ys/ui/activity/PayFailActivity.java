package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseTimerActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangtao on 2016/4/18.
 */
public class PayFailActivity extends BaseTimerActivity {


    @Bind(R.id.btn_jx_buy)
    Button btnJxBuy;

    @Bind(R.id.btn_back_home)
    ImageButton btnBackHome;


    //protected  int minute = 5;
    //protected int second = 0;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.pay_fail;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
         minute = 5;
         second = 0;
         btnJxBuy.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
                 startActivity(new Intent(App.getContext(), ProductActivity.class));
             }
         });

        btnBackHome.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   finish();
                   startActivity(new Intent(App.getContext(), ProductActivity.class));

               }
           }
        );
    }

}
