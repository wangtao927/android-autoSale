package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ys.ui.R;
import com.ys.ui.base.BaseActivity;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.btn_get_drug)
    Button btnGetDrug;

    @Bind(R.id.btn_query)
    Button btnQuery;

    @Bind(R.id.btn_sample)
    Button btnSample;

    @Bind(R.id.btn_print)
    Button btnPrint;

    @Bind(R.id.btn_admin)
    Button btn_admin;

    @Bind(R.id.btn_qrcode)
    Button btn_qrcode;

    @Bind(R.id.btn_buy)
    Button btn_buy;

    @Bind(R.id.btn_home)
    Button btnHome;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
        btnGetDrug.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
        btnSample.setOnClickListener(this);
        btnPrint.setOnClickListener(this);
        btn_admin.setOnClickListener(this);
        btn_qrcode.setOnClickListener(this);
        btn_buy.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        // 如果终端号不存在， 则跳转到设置终端信息界面
        if (!isInit()) {
            // 弹出层，输入终端号，初始化机器
            startActivity(new Intent(this, TermInitActivity.class));
            return;
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_get_drug:
                finish();
                startActivity(new Intent(MainActivity.this, GetProductActivity.class));
                break;
            case R.id.btn_query:
                finish();
                startActivity(new Intent(MainActivity.this, OutGoodsActivity.class));
                break;
            case R.id.btn_buy:
                finish();

                startActivity(new Intent(MainActivity.this, ProductActivity.class));
                break;
            case R.id.btn_sample:
                finish();
                startActivity(new Intent(MainActivity.this, InitActivity.class));
                break;
            case R.id.btn_print:
                finish();
                startActivity(new Intent(MainActivity.this, com.ys.ui.serial.print.activity.MainMenu.class));
                break;
            case R.id.btn_admin:
                finish();
                startActivity(new Intent(MainActivity.this, AdminActivity.class));
                break;
            case R.id.btn_qrcode:
                finish();
                startActivity(new Intent(MainActivity.this, QRcodeActivity.class));
                break;
            case R.id.btn_home:
                finish();
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                break;

        }
    }

}
