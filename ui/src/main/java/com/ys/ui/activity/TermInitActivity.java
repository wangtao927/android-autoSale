package com.ys.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ys.data.bean.GoodsBean;
import com.ys.data.bean.McAdminBean;
import com.ys.data.bean.McGoodsBean;
import com.ys.data.bean.McParamsBean;
import com.ys.data.bean.McStatusBean;
import com.ys.data.dao.SaleListBeanDao;
import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.common.http.RetrofitManager;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.common.response.CommonResponse;
import com.ys.ui.common.response.TermInitResult;
import com.ys.ui.utils.StringUtils;

import java.security.Provider;
import java.util.Date;
import java.util.List;

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

    private Location location;

    private String provider;


    @Override
    protected void create(Bundle savedInstanceState) {

        btn_save.setOnClickListener(this);

        getLocation();


    }

    private void getLocation() {
        LocationManager manager= (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        List<String> providerList =  manager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;

        } else {
            // 没有可用的位置服务
        }
        location = manager.getLastKnownLocation(provider);
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
    @Bind(R.id.et_serialNo)
    EditText et_serialNo;

    @Bind(R.id.pb_loading)
    ContentLoadingProgressBar mPbLoading;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                check();
                break;

        }
    }

    private void check() {
        RetrofitManager.builder().mcReset(et_serialNo.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();

                    }
                })
                .subscribe(new Action1<CommonResponse<TermInitResult>>() {
                    @Override
                    public void call(CommonResponse<TermInitResult> response) {
                        Log.d("result", response.toString());
                        if (response.isSuccess()) {
                            // 生成成功  同步数据
                            initTerm(response);
                            Toast.makeText(TermInitActivity.this, "终端号" + response.getExt_data().getMachine().getMc_no(), Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(TermInitActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(TermInitActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                        // 关闭当前active
                        finish();
                        startActivity(new Intent(TermInitActivity.this, MainActivity.class));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideProgress();
                        Toast.makeText(TermInitActivity.this, "获取数据失败" + throwable.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private McStatusBean getInitBean(String termno, String serialNo) {
        McStatusBean bean = new McStatusBean();
        if (location != null) {
            bean.setMr_mc_position(location.getLongitude() + "," + location.getLatitude() );//经纬度
        }
        bean.setMc_no(termno);
        bean.setMc_serial_no(serialNo);
        return bean;
    }

    private void initTerm(CommonResponse<TermInitResult> response) {
        // 1. 保存终端号到sqllite
        McStatusBean mcStatusBean = getInitBean(response.getExt_data().getMachine().getMc_no(), response.getExt_data().getMachine().getMc_serial_no());
        DbManagerHelper.initTermStatus(mcStatusBean);
        // 2. 更新终端参数
        DbManagerHelper.initMcParam(response.getExt_data().getMcparam());
        // 3. 更新商品表
        DbManagerHelper.initGoods(response.getExt_data().getGoods());
        // 4. 更新终端库存
        DbManagerHelper.initMcGoods(response.getExt_data().getMcgoods());
        // 5.更新管理员
        DbManagerHelper.initAdmin(response.getExt_data().getMcadmin());

    }


    public void showProgress() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        mPbLoading.setVisibility(View.GONE);
    }
}
