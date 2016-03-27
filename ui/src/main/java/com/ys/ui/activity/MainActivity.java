package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.ys.ui.R;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.sample.MainMenu;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.btn_get_drug)
    Button btnGetDrug;
    @Bind(R.id.btn_query)
    Button btnQuery;
    @Bind(R.id.btn_sample)
    Button btnSample;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void create(Bundle savedInstanceState) {

        btnGetDrug.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
        btnSample.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_drug:
                startActivity(new Intent(MainActivity.this, GetProductActivity.class));
                break;
            case R.id.btn_query:
                break;
            case R.id.btn_sample:
                startActivity(new Intent(MainActivity.this, MainMenu.class));
                break;
        }
    }

}
