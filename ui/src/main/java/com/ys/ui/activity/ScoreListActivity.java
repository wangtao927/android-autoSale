package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.ys.data.bean.McGoodsBean;
import com.ys.data.dao.McGoodsBeanDao;
import com.ys.ui.R;
import com.ys.ui.adapter.ScoreListAdapter;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseTimerActivity;
import com.ys.ui.common.constants.ChanStatusEnum;
import com.ys.ui.view.CirclePageView;
import com.ys.ui.view.LMRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.dao.query.QueryBuilder;

public class ScoreListActivity extends BaseTimerActivity implements LMRecyclerView.LoadMoreListener, View.OnClickListener {

    @Bind(R.id.recycler_view)
    LMRecyclerView mRecyclerView;

    @Bind(R.id.ll_pages)
    LinearLayout mPagesLayout;

    @Bind(R.id.ib_pre_page)
    ImageButton mPrePageButton;

    @Bind(R.id.ib_next_page)
    ImageButton mNextPageButton;


    private List<McGoodsBean> mProducts = new ArrayList();

    private ScoreListAdapter mProductApter;

    private Long mTotalCount;

    private int PAGE_SIZE = 9;

    private int mPageIndex = 1;
    private CirclePageView mPageView;
    private int mTotalPageCount;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_score;
    }


    @Override
    protected void create(Bundle savedInstanceState) {
        initView();
        loadData();
        mProductApter = new ScoreListAdapter(ScoreListActivity.this, mProducts);
        mRecyclerView.setAdapter(mProductApter);
    }

    @Override
    protected void backHome() {
        if(findViewById(R.id.btn_back_home)!=null){
            findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(ScoreListActivity.this, HomeActivity.class));
                }
            });
        }
    }
    private void initView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(App.ctx, 3));
        mRecyclerView.setLoadMoreListener(this);
        mPrePageButton.setOnClickListener(this);
        mNextPageButton.setOnClickListener(this);
        initPages();


    }



    /**
     * 初始化分页
     */
    private void initPages() {
        mTotalCount = App.getDaoSession(App.getContext()).getMcGoodsBeanDao().queryBuilder()
                .where(McGoodsBeanDao.Properties.Mg_score_price.gt(0))
                .where(McGoodsBeanDao.Properties.Mg_chann_status.eq(ChanStatusEnum.NORMAL.getIndex()))
                .where(McGoodsBeanDao.Properties.Mg_gnum.gt(0)).count();

        if (mTotalCount != 0) {
            mPageView = new CirclePageView(this);
            mTotalPageCount = (mTotalCount.intValue() + PAGE_SIZE - 1) / PAGE_SIZE;
            mPageView.init(mTotalPageCount);
            mPagesLayout.addView(mPageView);
        }
    }

    private void loadData() {
        QueryBuilder<McGoodsBean> queryBuilder = App.getDaoSession(App.getContext()).getMcGoodsBeanDao().queryBuilder();
        int offset = mPageIndex == 1 ? 0 : ((mPageIndex-1) * PAGE_SIZE);
        mProducts = queryBuilder.where(McGoodsBeanDao.Properties.Mg_score_price.gt(0))
                .where(McGoodsBeanDao.Properties.Mg_chann_status.eq(ChanStatusEnum.NORMAL.getIndex()))
                .where(McGoodsBeanDao.Properties.Mg_gnum.gt(0))
                .offset(offset).limit(PAGE_SIZE).orderAsc(McGoodsBeanDao.Properties.Mg_channo).list();
    }


    private void refresh() {
        mProductApter.setData(mProducts);
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
            case R.id.btn_search_bycode:
                refresh();
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
