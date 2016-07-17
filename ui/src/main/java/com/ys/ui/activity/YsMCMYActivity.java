package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

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
 * 名厂名药
 * Created by wangtao on 2016/7/8.
 */
public class YsMCMYActivity extends BaseTimerActivity implements LMRecyclerView.LoadMoreListener, View.OnClickListener  {

    @Bind(R.id.iv_mcmy1)
    ImageView im_mcmy1;
    @Bind(R.id.iv_mcmy2)
    ImageView im_mcmy2;
    @Bind(R.id.iv_mcmy3)
    ImageView im_mcmy3;
    @Bind(R.id.iv_mcmy4)
    ImageView im_mcmy4;
    @Bind(R.id.iv_mcmy5)
    ImageView im_mcmy5;
    @Bind(R.id.iv_mcmy6)
    ImageView im_mcmy6;
    @Bind(R.id.iv_mcmy7)
    ImageView im_mcmy7;

    @Bind(R.id.iv_mcmy8)
    ImageView im_mcmy8;

    @Bind(R.id.recycler_view)
    LMRecyclerView mRecyclerView;

    @Bind(R.id.ib_pre_page)
    ImageButton mPrePageButton;

    @Bind(R.id.ib_next_page)
    ImageButton mNextPageButton;

    private List<GoodsBean> mProducts = new ArrayList();

    private YsDetailAdapter detailtApter;

    private Long mTotalCount;

    private int PAGE_SIZE = 4;

    private int mPageIndex = 1;
    private CirclePageView mPageView;
    private int mTotalPageCount;


    private String keywords;

    @Override
    protected int getLayoutId() {
        return R.layout.level2_mcmy;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
        initView();
        loadData();
        detailtApter = new YsDetailAdapter(YsMCMYActivity.this, mProducts);
        mRecyclerView.setAdapter(detailtApter);
       im_mcmy1.setOnClickListener(this);
       im_mcmy2.setOnClickListener(this);
       im_mcmy3.setOnClickListener(this);
       im_mcmy4.setOnClickListener(this);
       im_mcmy5.setOnClickListener(this);
       im_mcmy6.setOnClickListener(this);
       im_mcmy7.setOnClickListener(this);
       im_mcmy8.setOnClickListener(this);
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

    protected void loadData() {

        QueryBuilder<GoodsBean> queryBuilder = App.getDaoSession(App.getContext()).getGoodsBeanDao().queryBuilder();
        int offset = mPageIndex == 1 ? 0 : ((mPageIndex-1) * PAGE_SIZE);
        if (!TextUtils.isEmpty(keywords)) {
            queryBuilder = queryBuilder.where(GoodsBeanDao.Properties.Gd_keyword.like("%"+keywords+"%"));
        }
        mProducts = queryBuilder.offset(offset).limit(PAGE_SIZE).orderAsc(GoodsBeanDao.Properties.Gd_code).list();


    }

    protected void refresh() {
        detailtApter.setData(mProducts);
    }



    private void reLoadData() {
        mPageView.setSelectedPage(mPageIndex - 1);
        loadData();
        refresh();
    }
    private void reLoadDataWithNoPage() {
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
    @Override
    protected void backHome() {
        if(findViewById(R.id.btn_back_home)!=null){
            findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(YsMCMYActivity.this, YsActivity.class));
                }
            });
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_mcmy1:
                 //
                keywords = "惠氏";
                reLoadDataWithNoPage();
                 break;
            case R.id.iv_mcmy2:
                keywords = "BAYER";
                reLoadDataWithNoPage();
                break;
            case R.id.iv_mcmy3:
                keywords = "太极";
                reLoadDataWithNoPage();
                break;
            case R.id.iv_mcmy4:
                keywords = "阿房宫";
                reLoadDataWithNoPage();
                break;
            case R.id.iv_mcmy5:
                keywords = "云南白药";
                reLoadDataWithNoPage();
                break;
            case R.id.iv_mcmy6:
                keywords = "白云山";
                reLoadDataWithNoPage();
                break;
            case R.id.iv_mcmy7:
                keywords = "盘龙云海";
                reLoadDataWithNoPage();
                break;
            case R.id.iv_mcmy8:
                keywords = "双飞人";
                reLoadDataWithNoPage();
                break;
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
            default:
                break;
        }



    }


    @Override
    public void loadMore() {

    }
}
