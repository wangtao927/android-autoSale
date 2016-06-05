package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ys.ui.R;
import com.ys.ui.base.BaseTimerActivity;
import com.ys.ui.base.PayTimerActivity;

import butterknife.Bind;

/**
 * Created by wangtao on 2016/4/18.
 */
public class PayFailActivity extends BaseTimerActivity {


    @Bind(R.id.transStatus)
    TextView transStatus;

    @Bind(R.id.transFailDetail)
    TextView transFailDetail;

    @Bind(R.id.btn_jx_buy)
    Button btnJxBuy;

    @Bind(R.id.btn_back_home)
    ImageButton btnBackHome;


    protected  int minute;
    protected int second;
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.pay_fail;
    }

    @Override
    protected void create(Bundle savedInstanceState) {

         btnJxBuy.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
                 startActivity(new Intent(PayFailActivity.this, ProductActivity.class));
             }
         });

        btnBackHome.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {

                                               finish();
                                               startActivity(new Intent(PayFailActivity.this, ProductActivity.class));

                                           }
                                       }
        );
    }
}
