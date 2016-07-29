package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;

import com.ys.data.bean.SaleListBean;
import com.ys.data.dao.SaleListBeanDao;
import com.ys.ui.R;
import com.ys.ui.adapter.SaleListAdapter;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.view.LMRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by wangtao on 2016/4/9.
 */
public class AdminSaleLitActivity extends BaseActivity implements View.OnClickListener, LMRecyclerView.LoadMoreListener {
    private final static String TAG = AdminSaleLitActivity.class.getSimpleName();

    @Bind(R.id.btn_sys_out)
    Button btnSysOut;
    @Bind(R.id.btn_clear_saleList)
    Button btnCleanSaleList;


    @Bind(R.id.btn_back)
    Button btnBack;
    @Bind(R.id.btn_back_home)
    Button btnBackHome;
    @Bind(R.id.recycler_view)
    LMRecyclerView recyclerView;

    private List<SaleListBean> lists = new ArrayList();

    private SaleListAdapter adapter;

    private long mTotalCount;

    private static final int mPageSize = 100;

    private int mPageIndex = 1;


    @Override
    protected void create(Bundle savedInstanceState) {
        initView();

        btnSysOut.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnBackHome.setOnClickListener(this);
        btnCleanSaleList.setOnClickListener(this);
        loadData();
        adapter = new SaleListAdapter(AdminSaleLitActivity.this, lists);
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(App.ctx));
        recyclerView.setLoadMoreListener(this);
    }

    private void loadData() {
        mTotalCount = App.getDaoSession(App.getContext()).getMcGoodsBeanDao().count();

        QueryBuilder<SaleListBean> queryBuilder = App.getDaoSession(App.getContext()).getSaleListBeanDao().queryBuilder()
                .orderDesc(SaleListBeanDao.Properties.Sl_no);
        int offset = mPageIndex == 1 ? 0 : (mPageIndex * mPageSize);
        List pageList = queryBuilder.offset(offset).limit(mPageSize).list();
        lists.addAll(pageList);

    }

    private void refresh() {
        adapter.setData(lists);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.admin_main_salelist;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_back:

                // 返回
                finish();
                startActivity(new Intent(this, AdminActivity.class));

                break;
            case R.id.btn_back_home:

                // 返回首页
                finish();
                startActivity(new Intent(this, HomeActivity.class));

                break;
            case R.id.btn_sys_out:
                // 退出程序
                finish();
                // 退出没效果， 抛异常
                throw new RuntimeException("admin exit system");
                //break;
            case R.id.btn_clear_saleList:
                // 跳转到流水界面
                App.getDaoSession(App.getContext()).getSaleListBeanDao().deleteSaleListBySendStatus();

                break;

            default:
                break;
        }

    }

    @Override
    public void loadMore() {
        if (lists.size() < mTotalCount) {
            mPageIndex++;
            loadData();
            refresh();
        } else {
            //ToastUtils.showError("所有数据已经全部加载了", App.getContext());
        }
    }

}
