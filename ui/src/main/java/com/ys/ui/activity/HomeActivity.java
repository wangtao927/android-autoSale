package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.ys.data.bean.AdvBean;
import com.ys.data.dao.DaoSession;
import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.service.TimerService;
import com.ys.ui.utils.ToastUtils;

import java.util.List;

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
        btnAbout.setOnClickListener(this);

        // 如果终端号不存在， 则跳转到设置终端信息界面
        if (!isInit()) {
            // 弹出层，输入终端号，初始化机器
            startActivity(new Intent(this, TermInitActivity.class));
            return;
        } else {
            // 启动service
            Intent intent = new Intent(this, TimerService.class);
            startService(intent);
        }
    }
    private boolean isInit() {
        DaoSession session = App.getDaoSession(App.getContext());
        if(App.mcNo == null){
            return false;
        } else {
            // 展示 终端号
            ToastUtils.showShortMessage(App.mcNo);
        }
        return true;
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
            case R.id.btn_about:
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                break;


        }
    }

}
