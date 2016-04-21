package com.ys.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ys.data.bean.McStatusBean;
import com.ys.data.dao.DaoMaster;
import com.ys.data.dao.DaoSession;
import com.ys.data.dao.McStatusBeanDao;
import com.ys.ui.activity.MainActivity;
import com.ys.ui.activity.TermInitActivity;

import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private Window window;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        window.setAttributes(params);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        create(savedInstanceState);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    protected abstract int getLayoutId();

    protected abstract void create(Bundle savedInstanceState);

    protected Context getCtx() {
        return   App.getContext();
    }

}
