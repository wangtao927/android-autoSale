package com.ys.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.ys.data.bean.GoodsBean;
import com.ys.data.bean.McStatusBean;
import com.ys.ui.R;
import com.ys.ui.adapter.GoodsGridViewAdapter;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.common.constants.SlTypeEnum;
import com.ys.ui.common.http.RetrofitManager;
import com.ys.ui.common.request.SaleListVo;
import com.ys.ui.common.response.CommonResponse;
import com.ys.ui.common.response.CreateOrderResult;
import com.ys.ui.utils.ToastUtils;
import com.ys.ui.view.GoodsGridViewItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 商品列表
 * Created by wangtao on 2016/4/18.
 */
public class GoodsListActivity extends BaseActivity {

    private String TAG = "GoodsListActivity";
    @Bind(R.id.gv_list)
    GridView gridView;

    private List<HashMap<String, GoodsGridViewItem>> data_list;

    private McStatusBean bean;
    @Override
    protected int getLayoutId() {
        return R.layout.goods_list;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
          List<McStatusBean> list = App.getDaoSession(App.getContext()).getMcStatusBeanDao().loadAll();

        // 生成微信订单
        bean = list.get(0);
        initData();

        GoodsGridViewAdapter myAdapter = new GoodsGridViewAdapter(GoodsListActivity.this, data_list);

        gridView.setAdapter(myAdapter);
    }


    private void createOrder(SaleListVo saleListVo) {
        RetrofitManager.builder().createOrder(saleListVo.getMcNo(), saleListVo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //showProgress();
                    }
                })
                .subscribe(new Action1<CommonResponse<CreateOrderResult>>() {
                    @Override
                    public void call(CommonResponse<CreateOrderResult> response) {
                        //hideProgress();
                        if (response.isSuccess()) {
                            // 调用出货

                        }
                        ToastUtils.showError("提货码无效", GoodsListActivity.this);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        //hideProgress();
                        Toast.makeText(GoodsListActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void initData() {
        data_list = new ArrayList<>();
        List<GoodsBean> lists = App.getDaoSession(App.getContext()).getGoodsBeanDao().loadAll();


    }
}
