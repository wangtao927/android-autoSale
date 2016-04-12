package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.common.http.RetrofitManager;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.common.response.CommonResponse;
import com.ys.ui.common.response.TermInitResult;
import com.ys.ui.utils.StringUtils;

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
    @Bind(R.id.et_serialNo)
    EditText et_serialNo;



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                check();

//         LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        double latitude = location.getLatitude();     //经度
//        double longitude = location.getLongitude(); //纬度
//        double altitude =  location.getAltitude();     //海拔
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
                        //showProgress();
                        int  i = 0;
                    }
                })
                .subscribe(new Action1<CommonResponse<TermInitResult>>() {
                    @Override
                    public void call(CommonResponse<TermInitResult> response) {
                        Log.d("result", response.toString());
                        if (response.isSuccess()) {
                             // 生成成功  同步数据
                            initTerm(response);
                            Toast.makeText(TermInitActivity.this, "终端号"+response.getExt_data().getMachine().getMc_no() , Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(TermInitActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(TermInitActivity.this, response.getMsg() , Toast.LENGTH_SHORT).show();
                        }
                        startActivity(new Intent(TermInitActivity.this, MainActivity.class));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(TermInitActivity.this, "获取数据失败" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        //hideProgress();
                    }
                });
    }


    private void initTerm(CommonResponse<TermInitResult> response) {
        // 1. 保存终端号到sqllite
        DbManagerHelper.initTermStatus(response.getExt_data().getMachine().getMc_no(), response.getExt_data().getMachine().getMc_serial_no());
        // 2. 更新终端参数
        DbManagerHelper.initMcParam(response.getExt_data().getMcparam());
        // 3. 更新商品表
        DbManagerHelper.initGoods(response.getExt_data().getGoods());
        // 4. 更新终端库存
        DbManagerHelper.initMcGoods(response.getExt_data().getMcgoods());
        // 5.更新管理员
        DbManagerHelper.initAdmin(response.getExt_data().getMcadmin());

    }
//
//    /**
//     *  保存终端号   保证这张表就一条数据
//     * @param termNo
//     */
//    private void initTermStatus(String termNo, String serialNo) {
//        //  存储到  sqllite 中
//        if (!StringUtils.isEmpty(termNo)) {
//            App.getDaoSession(App.getContext()).getMcStatusBeanDao().deleteAll();
//            App.getDaoSession(App.getContext()).getMcStatusBeanDao().insertOrReplace(getInitBean(termNo, serialNo));
//        }
//
//    }
//
//    /**
//     * 初始化终端参数表
//     * @param lists
//     */
//    private void initMcParam(List<McParamsBean> lists) {
//        if (lists != null && !lists.isEmpty()) {
//            App.getDaoSession(App.getContext()).getMcParamsBeanDao().deleteAll();
//            App.getDaoSession(App.getContext()).getMcParamsBeanDao().insertInTx(lists);
//        }
//
//    }
//
//    /**
//     * 初始化商品表
//     * @param goods
//     */
//    private void initGoods(List<GoodsBean> goods) {
//        if (goods != null && !goods.isEmpty()) {
//            App.getDaoSession(App.getContext()).getGoodsBeanDao().deleteAll();
//            App.getDaoSession(App.getContext()).getGoodsBeanDao().insertInTx(goods);
//        }
//
//    }
//
//    /**
//     * 初始化 终端库存表
//     * @param mcgoods
//     */
//    private void initMcGoods(List<McGoodsBean> mcgoods) {
//        if (mcgoods != null && !mcgoods.isEmpty()) {
//            App.getDaoSession(App.getContext()).getMcGoodsBeanDao().deleteAll();
//            App.getDaoSession(App.getContext()).getMcGoodsBeanDao().insertInTx(mcgoods);
//        }
//
//    }
//
//    public void initAdmin(List<McAdminBean> admins){
//
//        if (admins != null && !admins.isEmpty()) {
//            App.getDaoSession(App.getContext()).getMcAdminBeanDao().deleteAll();
//            App.getDaoSession(App.getContext()).getMcAdminBeanDao().insertOrReplaceInTx(admins);
//        }
//    }

}
