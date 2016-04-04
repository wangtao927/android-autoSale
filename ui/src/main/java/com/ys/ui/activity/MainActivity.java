package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ys.data.bean.McStatusBean;
import com.ys.data.dao.DaoSession;
import com.ys.data.dao.McStatusBeanDao;
import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.sample.MainMenu;
import com.ys.ui.utils.ToastUtils;

import java.util.List;

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
        // 如果终端号不存在， 则跳转到设置终端信息界面
        if (!isInit()) {
            // 弹出层，输入终端号，初始化机器
            startActivity(new Intent(this, TermInitActivity.class));
        }
    }
    private boolean isInit() {
        DaoSession session = App.getDaoSession(App.getContext());
        McStatusBeanDao statusBeanDao = session.getMcStatusBeanDao();

        List<McStatusBean> lists = statusBeanDao.loadAll();
        if (null == lists || lists.isEmpty()) {
            return false;
        } else {
            // 展示 终端号
            ToastUtils.showShortMessage(lists.get(0).getMc_no(), App.getContext());
        }
        return true;
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
            case R.id.btn_print:
                startActivity(new Intent(MainActivity.this, PrintActivity.class));
                break;
            case R.id.btn_admin:
                startActivity(new Intent(MainActivity.this, PrintActivity.class));
                break;
        }
    }

}
