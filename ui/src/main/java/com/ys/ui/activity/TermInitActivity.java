package com.ys.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ys.data.bean.McStatusBean;
import com.ys.ui.R;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.common.http.RetrofitManager;
import com.ys.ui.common.response.TermInitResponse;

import java.util.Date;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 初始化终端参数  初始化终端号
 */
public class TermInitActivity extends BaseActivity implements View.OnClickListener {
    private String TAG = "TermInitActivity";

    @Override
    protected void create(Bundle savedInstanceState) {

        btn_save.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.my_main;
    }

    @Bind(R.id.btn_save)
    Button btn_save;
    @Bind(R.id.et_termno)
    EditText et_termno;
    @Bind(R.id.et_serialNo)
    EditText et_serialNo;

    private McStatusBean getInitBean(String termno) {
        McStatusBean bean = new McStatusBean();
        bean.setMc_no(termno);
        bean.setAddtime(new Date());
        return bean;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                check();
                // 到后台去检验 终端号是否合法
                 /* RetrofitManager manager = RetrofitManager.builder();
                  TermInitResponse response = manager.mcReset();
                  ToastUtils.showShortMessage(response.toString(), App.getContext());*/
                // 存储到  sqllite 中
//                App.getDaoSession(App.getContext()).getMcStatusBeanDao().insertOrReplace(getInitBean(et_termno.getText().toString()));
//                McStatusBean bean = App.getDaoSession(App.getContext()).getMcStatusBeanDao().load(1);
//                ToastUtils.showError(et_termno.getText().toString() + bean.getMr_id(), App.getContext());


//         LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        double latitude = location.getLatitude();     //经度
//        double longitude = location.getLongitude(); //纬度
//        double altitude =  location.getAltitude();     //海拔
                break;

        }
    }

    private void check() {
        RetrofitManager.builder().mcReset(et_termno.getText().toString(), et_serialNo.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //showProgress();
                        int  i = 0;
                    }
                })
                .subscribe(new Action1<TermInitResponse>() {
                    @Override
                    public void call(TermInitResponse response) {
                        Toast.makeText(TermInitActivity.this, "成功", Toast.LENGTH_SHORT).show();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(TermInitActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                        //hideProgress();
                    }
                });
    }
}
