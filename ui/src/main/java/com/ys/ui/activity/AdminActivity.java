package com.ys.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

    private List<McGoodsBean> lists;
    @Override
    protected void create(Bundle savedInstanceState) {

       // List<McGoodsBean> goods = App.getDaoSession(App.getContext()).getMcGoodsBeanDao().loadAll();
        initData();

        McGoodsListAdapter adapter = new McGoodsListAdapter(AdminActivity.this, R.layout.mcgoods_item, lists);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView lView = (ListView) parent;
                McGoodsBean goodsBean = (McGoodsBean) lView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), goodsBean.getMg_channo(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        lists = new ArrayList<>();
        McGoodsBean bean = new McGoodsBean();
        bean.setMc_no("8888888");
        bean.setGd_no("M11111");
        bean.setChanStatus(1L);
        bean.setMg_channo("0011");
        bean.setMg_gnum(10L);
        bean.setMg_gvol(8L);
        lists.add(bean);
        bean = new McGoodsBean();
        bean.setMc_no("8888888");
        bean.setGd_no("M11112");
        bean.setChanStatus(1L);
        bean.setMg_channo("0012");
        bean.setMg_gnum(10L);
        bean.setMg_gvol(8L);
        lists.add(bean);
        bean = new McGoodsBean();
        bean.setMc_no("8888888");
        bean.setGd_no("M11113");
        bean.setChanStatus(1L);
        bean.setMg_channo("0013");
        bean.setMg_gnum(10L);
        bean.setMg_gvol(8L);
        lists.add(bean);


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

//        switch (v.getId()) {
//            case R.id.btn_admin_timer:
//                startTimer();
//                break;
//        }

    }

}
