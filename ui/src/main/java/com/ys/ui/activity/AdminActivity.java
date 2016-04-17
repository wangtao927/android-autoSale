package com.ys.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.ys.data.bean.McGoodsBean;
import com.ys.data.bean.McStatusBean;
import com.ys.data.bean.SaleListBean;
import com.ys.ui.R;
import com.ys.ui.adapter.McGoodsListAdapter;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.common.http.RetrofitManager;
import com.ys.ui.common.request.CommonRequest;
import com.ys.ui.common.request.McDataVo;
import com.ys.ui.common.response.CommonResponse;
import com.ys.ui.common.response.McDataResult;
import com.ys.ui.common.response.TermInitResult;
import com.ys.ui.utils.StringUtils;
import com.ys.ui.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    @Bind(R.id.list_view)
    ListView listView;

    @Bind(R.id.btn_reset)
    Button btnReset;

    @Bind(R.id.btn_buhuo)
    Button btnBuHuo;

   @Bind(R.id.btn_clear)
    Button btnClear;
    private List<McGoodsBean> lists;

    @Bind(R.id.pb_loading)
    ContentLoadingProgressBar mPbLoading;

    private McGoodsListAdapter adapter;
    @Override
    protected void create(Bundle savedInstanceState) {

        btnBuHuo.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        initData();

        adapter = new McGoodsListAdapter(AdminActivity.this, R.layout.mcgoods_item, lists);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView lView = (ListView) parent;
                final McGoodsBean goodsBean = (McGoodsBean) lView.getItemAtPosition(position);

                // 弹出一个输入框， 修改库存
                final EditText inputServer = new EditText(AdminActivity.this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                builder.setTitle("修改库存").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
                        .setNegativeButton("返回", null);
                builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Long kucun = null;
                        try {
                            kucun = Long.valueOf(inputServer.getText().toString());
                        } catch (NumberFormatException e) {
                            ToastUtils.showError("库存数量必须是整数", AdminActivity.this);
                            return;
                        }
                        if (kucun.intValue() > goodsBean.getMg_gvol()) {
                            ToastUtils.showError("库存数量不能大于容量", AdminActivity.this);
                            return;
                        }

                        goodsBean.setMg_gnum(Long.valueOf(inputServer.getText().toString()));
                        App.getDaoSession(App.getContext()).getMcGoodsBeanDao().updateGnumByPK(goodsBean);

                        adapter.notifyDataSetChanged();
                    }
                });
                builder.show();

            }
        });


    }

    private void initData() {
        lists = new ArrayList<>();
        lists =  App.getDaoSession(App.getContext()).getMcGoodsBeanDao().loadAll();
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
            case R.id.btn_reset:
                startActivity(new Intent(AdminActivity.this, TermInitActivity.class));
                break;
            case R.id.btn_buhuo:

                App.getDaoSession(App.getContext()).getMcGoodsBeanDao().updateAll(lists);
                adapter.notifyDataSetChanged();

            case R.id.btn_clear:
                // 清除卡货
                App.getDaoSession(App.getContext()).getMcGoodsBeanDao().clearChanStatus(lists);
                adapter.notifyDataSetChanged();

                break;

        }

    }

    public void showProgress() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        mPbLoading.setVisibility(View.GONE);
    }
}
