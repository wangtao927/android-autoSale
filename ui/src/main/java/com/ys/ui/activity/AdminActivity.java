package com.ys.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ys.data.bean.McGoodsBean;
import com.ys.data.bean.McStatusBean;
import com.ys.data.bean.SaleListBean;
import com.ys.ui.R;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.common.http.RetrofitManager;
import com.ys.ui.common.request.CommonRequest;
import com.ys.ui.common.request.McDataVo;
import com.ys.ui.common.response.CommonResponse;
import com.ys.ui.common.response.McDataResult;
import com.ys.ui.common.response.TermInitResult;
import com.ys.ui.utils.StringUtils;

import java.util.ArrayList;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wangtao on 2016/4/9.
 */
public class AdminActivity extends BaseActivity implements View.OnClickListener {
    private String TAG = "AdminActivity";

    @Bind(R.id.btn_admin_timer)
    Button btnAdmin;

    @Override
    protected void create(Bundle savedInstanceState) {
           btnAdmin.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.admin_main;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_admin_timer:
                startTimer();
                break;
        }

    }

    private void startTimer() {
        McDataVo vo = new McDataVo();
        vo.setMcStatus(new McStatusBean());
        vo.setMcGoodsList(new ArrayList<McGoodsBean>());
        vo.setMcSaleList(new ArrayList<SaleListBean>());
        CommonRequest<McDataVo> request = new CommonRequest<>(
                "88888888", System.currentTimeMillis(), vo
        );
        RetrofitManager.builder().postMcData(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //showProgress();
                        int  i = 0;
                    }
                })
                .subscribe(new Action1<CommonResponse<McDataResult>>() {
                    @Override
                    public void call(CommonResponse<McDataResult> response) {
                        Log.d("result", response.toString());
                        if (response.isSuccess()) {
                            // 生成成功  同步数据
                            // 判断数据，并更新
                            updateInfo(response.getExt_data().getOprcode(), response.getExt_data().getOprdata());

                            Toast.makeText(AdminActivity.this, "终端号", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(TermInitActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(AdminActivity.this, response.getMsg() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("error", throwable.toString());
                        Toast.makeText(AdminActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                        //hideProgress();
                    }
                });
    }

    private void  updateInfo(String oprCodes, TermInitResult result) {
        if (!StringUtils.isEmpty(oprCodes)) {
            String[]  codes = oprCodes.split(",");
            for (String code : codes) {

            }
        }



    }
}
