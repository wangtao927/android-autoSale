package com.ys.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageButton;

import com.ys.data.bean.GoodsBean;
import com.ys.data.dao.GoodsBeanDao;
import com.ys.ui.R;
import com.ys.ui.adapter.YsDetailAdapter;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseTimerActivity;
import com.ys.ui.view.CirclePageView;
import com.ys.ui.view.LMRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by wangtao on 2016/7/10.
 */
public class YsDetailActivity extends BaseTimerActivity implements LMRecyclerView.LoadMoreListener, View.OnClickListener {

    @Bind(R.id.recycler_view)
    LMRecyclerView mRecyclerView;

    @Bind(R.id.ib_pre_page)
    ImageButton mPrePageButton;

    @Bind(R.id.ib_next_page)
    ImageButton mNextPageButton;

    private List<GoodsBean> mProducts = new ArrayList();

    private YsDetailAdapter detailtApter;

    private Long mTotalCount;

    private static final int PAGE_SIZE = 6;

    private int mPageIndex = 1;
    private CirclePageView mPageView;
    private int mTotalPageCount;

    @Override
    protected int getLayoutId() {
        return R.layout.level3_buy;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
        initView();
        loadData();
        detailtApter = new YsDetailAdapter(YsDetailActivity.this, mProducts);
        mRecyclerView.setAdapter(detailtApter);
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(App.ctx, 2));
        mRecyclerView.setLoadMoreListener(this);

        mPrePageButton.setOnClickListener(this);
        mNextPageButton.setOnClickListener(this);
        initPages();


    }



    /**
     * 初始化分页
     */
    private void initPages() {
        mTotalCount = App.getDaoSession(App.getContext()).getMcGoodsBeanDao().count();

        if (mTotalCount != 0) {
            mPageView = new CirclePageView(this);
            mTotalPageCount = (mTotalCount.intValue() + PAGE_SIZE - 1) / PAGE_SIZE;
            mPageView.init(mTotalPageCount);
            //mPagesLayout.addView(mPageView);
        }
    }

    private void loadData() {

        QueryBuilder<GoodsBean> queryBuilder = App.getDaoSession(App.getContext()).getGoodsBeanDao().queryBuilder();
        int offset = mPageIndex == 1 ? 0 : ((mPageIndex-1) * PAGE_SIZE);
        mProducts = queryBuilder.offset(offset).limit(PAGE_SIZE).orderAsc(GoodsBeanDao.Properties.Gd_code).list();


    }


    private void refresh() {
        detailtApter.setData(mProducts);
    }


    @Override
    public void loadMore() {
//        if (mProducts.size() < mTotalCount) {.
//            mPageIndex++;
//            loadData();
//            refresh();
//        } else {
//            //ToastUtils.showError("所有数据已经全部加载了", App.getContext());
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_pre_page:
                if (mPageIndex > 1) {
                    mPageIndex--;
                    reLoadData();

                }
                break;
            case R.id.ib_next_page:
                if (mPageIndex < mTotalPageCount) {
                    mPageIndex++;
                    reLoadData();
                }
                break;

        }
    }

    private void reLoadData() {
        mPageView.setSelectedPage(mPageIndex - 1);
        loadData();
        refresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

    }


    @Override
    protected void onResume() {
        minute = 0;
        second = 0;
        reLoadData();refresh();
        super.onResume();
    }
}
