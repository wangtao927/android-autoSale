package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ys.data.bean.McGoodsBean;
import com.ys.data.bean.McStatusBean;
import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseTimerActivity;
import com.ys.ui.common.constants.SlTypeEnum;
import com.ys.ui.common.http.RetrofitManager;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.common.request.SaleListVo;
import com.ys.ui.common.response.CommonResponse;
import com.ys.ui.common.response.CreateOrderResult;
import com.ys.ui.utils.ToastUtils;

import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class GetProductActivity extends BaseTimerActivity implements View.OnClickListener {

    @Bind(R.id.et_product_code)
    EditText etProductCode;

    @Bind(R.id.et_product_pwd)
    EditText etProductPwd;

    @Bind(R.id.btn_confirm)
    ImageButton btnConfirm;

    @Bind(R.id.btn_back)
    ImageButton btnBack;

    @Bind(R.id.ad)
    ImageView ad;

    @Bind(R.id.pb_loading)
    ContentLoadingProgressBar mPbLoading;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_get;
    }


    @Override
    protected void create(Bundle savedInstanceState) {
        String adimgUrl = getIntent().getStringExtra("adimg");
        if (adimgUrl != null) {
            Glide.with(this)
                    .load(adimgUrl)
                    .placeholder(R.mipmap.ad1)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ad);

        } else {
            Glide.with(this)
                    .load(R.mipmap.ad1)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ad);
        }
        btnConfirm.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    protected void backHome() {
        if (findViewById(R.id.btn_back_home) != null) {
            findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(GetProductActivity.this, HomeActivity.class));
                }
            });
        }
    }
    private void getProductByCode() {
        final String code = etProductCode.getText().toString();
        String pwd = etProductPwd.getText().toString();
        List<McStatusBean> list = App.getDaoSession(App.getContext()).getMcStatusBeanDao().loadAll();
        if (list != null && !list.isEmpty()) {
            McStatusBean bean = list.get(0);
            //
            SaleListVo saleListVo = new SaleListVo();
            saleListVo.setMcNo(bean.getMc_no());
            saleListVo.setSlType(String.valueOf(SlTypeEnum.CODE.getIndex()));

            saleListVo.setSlThCardno(code);
            saleListVo.setSlThPwd(pwd);
            createOrder(saleListVo);
        } else {
            ToastUtils.showError("终端数据异常", GetProductActivity.this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                getProductByCode();
                break;
            case R.id.btn_back:
                etProductCode.setText("");
                etProductPwd.setText("");
                break;
        }
    }

    public void showProgress() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        mPbLoading.setVisibility(View.GONE);
    }

    private void createOrder(SaleListVo saleListVo) {
        RetrofitManager.builder().createOrder(saleListVo.getMcNo(), saleListVo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .subscribe(new Action1<CommonResponse<CreateOrderResult>>() {
                    @Override
                    public void call(CommonResponse<CreateOrderResult> response) {
                        hideProgress();
                        if (response.isSuccess()) {
                            // 调用出货

                            String gdNo = response.getExt_data().getSlGdNo();
                            // 根据商品编码查询机器是否有该商品
                            McGoodsBean mcGoodsBean = DbManagerHelper.getOutGoods(gdNo);
                            if (mcGoodsBean == null) {
                                ToastUtils.showError("该终端没有对应的药品", GetProductActivity.this);

                                return;
                            } else {
                                Intent intent = new Intent(GetProductActivity.this, OutGoodsActivity.class);
                                intent.putExtra("slNo", response.getExt_data().getSlNo());

                                intent.putExtra("slType", SlTypeEnum.CODE.getIndex());
                                intent.putExtra("channo", mcGoodsBean.getMg_channo());

                                startActivity(intent);
                                finish();
                                return;
                            }
                        }
                        ToastUtils.showError("提货码无效", GetProductActivity.this);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideProgress();
                        Toast.makeText(GetProductActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();

                    }
                });
    }

}
