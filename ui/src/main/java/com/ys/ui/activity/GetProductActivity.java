package com.ys.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ys.ui.R;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.common.http.RetrofitManager;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class GetProductActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.et_product_code)
    EditText etProductCode;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;
    @Bind(R.id.pb_loading)
    ContentLoadingProgressBar mPbLoading;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_get_product;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                getProductByCode();
                break;
        }
    }

    private void getProductByCode() {
        final String code = etProductCode.getText().toString();
        RetrofitManager.builder().queryProductByCode(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object newsList) {
                        Toast.makeText(GetProductActivity.this, "取货成功, code:" + code, Toast.LENGTH_SHORT).show();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(GetProductActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                        hideProgress();
                    }
                });

    }

    public void showProgress() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        mPbLoading.setVisibility(View.GONE);
    }
}
