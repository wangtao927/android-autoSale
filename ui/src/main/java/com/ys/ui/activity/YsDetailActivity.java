package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.ys.data.bean.GoodsBean;
import com.ys.data.dao.GoodsBeanDao;
import com.ys.ui.R;
import com.ys.ui.adapter.YsDetailAdapter;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseTimerActivity;
import com.ys.ui.common.constants.YsConstants;
import com.ys.ui.view.CirclePageView;
import com.ys.ui.view.LMRecyclerView;
import com.ys.ui.view.WordWrapView;
import com.ys.ui.view.YsDetailView;

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

    protected List<GoodsBean> mProducts = new ArrayList();

    protected YsDetailAdapter detailtApter;

    protected Long mTotalCount;

    protected int PAGE_SIZE = 9;

    protected int LINE_NUM = 3;

    protected int mPageIndex = 1;
    protected CirclePageView mPageView;
    protected int mTotalPageCount;

    @Bind(R.id.view_wordwrap)
    WordWrapView wordWrapView;

    private String keyword;

    private int index;
    @Override
    protected int getLayoutId() {
        return R.layout.level3_buy;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
        // 初始化  label 标签

        Bundle datas = getIntent().getExtras();
        index = datas.getInt("index");
        //String desc = datas.getString("desc");
        List<YsDetailView> list =  YsConstants.getListView(index);

        if (list.size() > 20) {
            PAGE_SIZE = 6;
        }
       // drawable.setColor(getResources().getColor(R.color.bg)); // 边框内部颜色
        Button button ;
        for (YsDetailView detailView : list) {
            button = new Button(this);
            button.setText(detailView.getDesc());
            button.setTextSize(30);
            button.setBackgroundResource(R.drawable.label);
            button.setGravity(Gravity.CENTER_HORIZONTAL);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button)v;
                    // 其他所有的都变成  原始背景色
                    for (int i = 0; i < wordWrapView.getChildCount(); i++) {
//
                        wordWrapView.getChildAt(i).setBackgroundResource(R.drawable.label);
                    }
                    b.setBackgroundResource(R.drawable.label_1);
                    keyword = b.getText().toString();
                    reLoadDataWithNoPage();

                }
            });
            wordWrapView.addView(button);
        }
        initView();
        loadData();
        detailtApter = new YsDetailAdapter(YsDetailActivity.this, mProducts);
        mRecyclerView.setAdapter(detailtApter);

    }

    protected void initView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(App.ctx, LINE_NUM));
        mRecyclerView.setLoadMoreListener(this);

        mPrePageButton.setOnClickListener(this);
        mNextPageButton.setOnClickListener(this);
        initPages();


    }



    /**
     * 初始化分页
     */
    protected void initPages() {
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
        if (!TextUtils.isEmpty(keyword)) {
            queryBuilder = queryBuilder.where(GoodsBeanDao.Properties.Gd_keyword.like("%"+keyword+"%"));
        }
        mProducts = queryBuilder.offset(offset).limit(PAGE_SIZE).orderAsc(GoodsBeanDao.Properties.Gd_code).list();


    }


    protected void refresh() {
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

    protected void reLoadData() {
        mPageView.setSelectedPage(mPageIndex - 1);
        loadData();
        refresh();
    }
    private void reLoadDataWithNoPage() {
        loadData();
        refresh();
    }
    @Override
    protected void backHome() {
        if(findViewById(R.id.btn_back_home)!=null){
            findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(YsDetailActivity.this, YsActivity.class));
                }
            });
        }
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
