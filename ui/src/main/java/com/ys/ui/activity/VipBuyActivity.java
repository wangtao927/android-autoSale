package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ys.data.bean.McGoodsBean;
import com.ys.data.dao.McGoodsBeanDao;
import com.ys.ui.R;
import com.ys.ui.adapter.VipBuyAdapter;
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

public class VipBuyActivity extends BaseTimerActivity implements LMRecyclerView.LoadMoreListener, View.OnClickListener {

    @Bind(R.id.recycler_view)
    LMRecyclerView mRecyclerView;

    @Bind(R.id.ll_pages)
    LinearLayout mPagesLayout;

    @Bind(R.id.ib_pre_page)
    ImageButton mPrePageButton;

    @Bind(R.id.ib_next_page)
    ImageButton mNextPageButton;

    @Bind(R.id.tv_vip_type)
    TextView mVipType;

    private List<McGoodsBean> mProducts = new ArrayList();

    private VipBuyAdapter mVipBuyAdapter;

    private Long mTotalCount;

    private static final int PAGE_SIZE = 9;

    private int mPageIndex = 1;
    private CirclePageView mPageView;
    private int mTotalPageCount;

    int origin = 0; // 1 积分换购  2 免费尊享  3 会员特惠
    @Override
    protected int getLayoutId() {
        return R.layout.vip_buy;
    }


    @Override
    protected void create(Bundle savedInstanceState) {

        Bundle datas = getIntent().getExtras();
        origin = datas.getInt("index");
        if (origin == 1) {
            mVipType.setText("积分换购");

        } else if (origin == 2) {
            mVipType.setText("免费尊享");

        } else if (origin == 3) {
            mVipType.setText("会员特惠");
        }
        initView();
        loadData();

        mVipBuyAdapter = new VipBuyAdapter(VipBuyActivity.this, mProducts, origin);
        mRecyclerView.setAdapter(mVipBuyAdapter);
    }

    @Override
    protected void backHome() {
        if(findViewById(R.id.btn_back_home)!=null){
            findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(VipBuyActivity.this, VipActivity.class));
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
        QueryBuilder<McGoodsBean> queryBuilder = App.getDaoSession(App.getContext()).getMcGoodsBeanDao().queryBuilder();
        if (origin == 1) {
            mTotalCount =  queryBuilder.where(McGoodsBeanDao.Properties.Mg_score_price.gt(0))
                    .where(McGoodsBeanDao.Properties.Mg_chann_status.eq(ChanStatusEnum.NORMAL.getIndex()))
                    .where(McGoodsBeanDao.Properties.Mg_gnum.gt(0)).count();
        } else {
            mTotalCount = queryBuilder.where(McGoodsBeanDao.Properties.Mg_chann_status.eq(ChanStatusEnum.NORMAL.getIndex()))
                    .where(McGoodsBeanDao.Properties.Mg_gnum.gt(0)).orderAsc(McGoodsBeanDao.Properties.Mg_channo).count();
        }
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
        if (origin == 1) {
            mProducts =  queryBuilder.where(McGoodsBeanDao.Properties.Mg_score_price.gt(0))
                    .where(McGoodsBeanDao.Properties.Mg_chann_status.eq(ChanStatusEnum.NORMAL.getIndex()))
                    .where(McGoodsBeanDao.Properties.Mg_gnum.gt(0)).offset(offset).limit(PAGE_SIZE).orderAsc(McGoodsBeanDao.Properties.Mg_channo).list();
        } else {
            mProducts = queryBuilder.offset(offset).limit(PAGE_SIZE).where(McGoodsBeanDao.Properties.Mg_chann_status.eq(ChanStatusEnum.NORMAL.getIndex()))
                    .where(McGoodsBeanDao.Properties.Mg_gnum.gt(0)).orderAsc(McGoodsBeanDao.Properties.Mg_channo).list();

        }
    }


    private void refresh() {
        mVipBuyAdapter.setData(mProducts);
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
