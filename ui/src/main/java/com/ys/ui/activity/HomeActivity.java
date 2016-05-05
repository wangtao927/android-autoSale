package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.ys.ui.R;
import com.ys.ui.base.BaseActivity;

import butterknife.Bind;

public class HomeActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.logo)
    ImageView logo;
    @Bind(R.id.tel)
    ImageView tel;
    @Bind(R.id.btn_buy)
    ImageButton btnBuy;
    @Bind(R.id.btn_get_drug)
    ImageButton btnGetDrug;
    @Bind(R.id.btn_smart)
    ImageButton btnSmart;
    @Bind(R.id.btn_shop)
    ImageButton btnShop;
    @Bind(R.id.btn_about)
    ImageButton btnAbout;
    @Bind(R.id.btn_member)
    ImageButton btnMember;
    @Bind(R.id.btn_statement)
    ImageButton btnStatement;
    @Bind(R.id.btn_help)
    ImageButton btnHelp;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
        btnGetDrug.setOnClickListener(this);
        btnBuy.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_drug:
                startActivity(new Intent(HomeActivity.this, GetProductActivity.class));
                break;
            case R.id.btn_buy:
                startActivity(new Intent(HomeActivity.this, ProductActivity.class));
                break;
        }
    }

}
