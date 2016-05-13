package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;

import com.ys.data.bean.McGoodsBean;
import com.ys.ui.R;
import com.ys.ui.adapter.McGoodsListAdapter;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.utils.ToastUtils;
import com.ys.ui.view.LMRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by wangtao on 2016/4/9.
 */
public class AdminActivity extends BaseActivity implements View.OnClickListener, LMRecyclerView.LoadMoreListener {
    private final static String TAG = AdminActivity.class.getSimpleName();

    @Bind(R.id.btn_reset)
    Button btnReset;

    @Bind(R.id.btn_buhuo)
    Button btnBuHuo;

    @Bind(R.id.btn_clear)
    Button btnClear;

    @Bind(R.id.recycler_view)
    LMRecyclerView recyclerView;

    private List<McGoodsBean> lists = new ArrayList();

    private McGoodsListAdapter adapter;

    private long mTotalCount;

    private static final int mPageSize = 10;

    private int mPageIndex = 1;


    @Override
    protected void create(Bundle savedInstanceState) {
        initView();

        btnBuHuo.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        loadData();
        adapter = new McGoodsListAdapter(AdminActivity.this, lists);
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(App.ctx));
        recyclerView.setLoadMoreListener(this);
    }

    private void loadData() {
        mTotalCount = App.getDaoSession(App.getContext()).getMcGoodsBeanDao().count();

        QueryBuilder<McGoodsBean> queryBuilder = App.getDaoSession(App.getContext()).getMcGoodsBeanDao().queryBuilder();
        int offset = mPageIndex == 1 ? 0 : (mPageIndex * mPageSize);
        List pageList = queryBuilder.offset(offset).limit(mPageSize).list();
        lists.addAll(pageList);

    }

    private void refresh() {
        adapter.setData(lists);
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
                finish();
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
