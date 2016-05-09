package com.ys.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.ys.data.bean.GoodsBean;
import com.ys.ui.R;
import com.ys.ui.adapter.ProductAdapter;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.utils.ToastUtils;
import com.ys.ui.view.LMRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.dao.query.QueryBuilder;

public class ProductActivity extends BaseActivity implements LMRecyclerView.LoadMoreListener {

    @Bind(R.id.recycler_view)
    LMRecyclerView mRecyclerView;

    private List<GoodsBean> mProducts = new ArrayList();

    private ProductAdapter mProductApter;

    private long mTotalCount;

    private static final int mPageSize = 10;

    private int mPageIndex = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy;
    }


    @Override
    protected void create(Bundle savedInstanceState) {
        initView();
        loadData();
        mProductApter = new ProductAdapter(ProductActivity.this, mProducts);
        mRecyclerView.setAdapter(mProductApter);
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(App.ctx, 4));
        mRecyclerView.setLoadMoreListener(this);
    }

    private void loadData() {
        mTotalCount = App.getDaoSession(App.getContext()).getGoodsBeanDao().count();

        QueryBuilder<GoodsBean> queryBuilder = App.getDaoSession(App.getContext()).getGoodsBeanDao().queryBuilder();
        int offset = mPageIndex == 1 ? 0 : (mPageIndex * mPageSize);
        List pageList = queryBuilder.offset(offset).limit(mPageSize).list();
        mProducts.addAll(pageList);

    }

    private void refresh() {
        mProductApter.setData(mProducts);
    }


    @Override
    public void loadMore() {
        if (mProducts.size() < mTotalCount) {
            mPageIndex++;
            loadData();
            refresh();
        } else {
            ToastUtils.showError("所有数据已经全部加载了", App.getContext());
        }
    }
}
