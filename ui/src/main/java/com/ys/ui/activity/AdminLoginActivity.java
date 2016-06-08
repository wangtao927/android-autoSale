package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ys.data.bean.McAdminBean;
import com.ys.data.dao.McAdminBeanDao;
import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.utils.ToastUtils;

import java.util.List;

import butterknife.Bind;

/**
 * Created by wangtao on 2016/4/17.
 */
public class AdminLoginActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.et_userno)
    EditText etUserNo;

    @Bind(R.id.et_pwd)
    EditText etPwd;

    @Bind(R.id.btn_login)
    Button btnLogin;

    @Bind(R.id.btn_cancel)
    Button btnCancel;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void create(Bundle savedInstanceState) {

        btnCancel.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_login:

                if (TextUtils.isEmpty(etUserNo.getText()) || TextUtils.isEmpty(etPwd.getText())) {
                    //
                    ToastUtils.showShortMessage("用户名密码不能为空");

                } else {
                    //
                    //List<McAdminBean> list = App.getDaoSession(App.getContext()).getMcAdminBeanDao().loadAll();

                    McAdminBean mcAdminBean = App.getDaoSession(App.getContext()).getMcAdminBeanDao().queryBuilder().where(McAdminBeanDao.Properties.U_no.eq(etUserNo.getText()))
                            .where(McAdminBeanDao.Properties.U_pwd.eq(etPwd.getText())).unique();

                    if (mcAdminBean != null) {
                        Intent detailIntent = new Intent(this, AdminActivity.class);
                        finish();
                        startActivity(detailIntent);
                    } else {
                        ToastUtils.showShortMessage("用户名密码错误");
                    }
//                    Intent detailIntent = new Intent(this, AdminActivity.class);
//                    finish();
//                    startActivity(detailIntent);


                }

                break;
            case R.id.btn_cancel:

                Intent detailIntent = new Intent(this, HomeActivity.class);
                finish();
                startActivity(detailIntent);
                break;

        }

    }
}
