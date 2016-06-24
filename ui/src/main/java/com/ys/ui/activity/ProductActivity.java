package com.ys.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ys.data.bean.McGoodsBean;
import com.ys.data.dao.McGoodsBeanDao;
import com.ys.ui.R;
import com.ys.ui.adapter.ProductAdapter;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseTimerActivity;
import com.ys.ui.common.constants.ChanStatusEnum;
import com.ys.ui.utils.ToastUtils;
import com.ys.ui.view.CirclePageView;
import com.ys.ui.view.LMRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.dao.query.QueryBuilder;

public class ProductActivity extends BaseTimerActivity implements LMRecyclerView.LoadMoreListener, View.OnClickListener {

    @Bind(R.id.recycler_view)
    LMRecyclerView mRecyclerView;

    @Bind(R.id.ll_pages)
    LinearLayout mPagesLayout;
    @Bind(R.id.et_product_code)
    EditText mProductCode;
    @Bind(R.id.btn_search_bycode)
    Button mSearchButton;

    @Bind(R.id.ib_pre_page)
    ImageButton mPrePageButton;

    @Bind(R.id.ib_next_page)
    ImageButton mNextPageButton;


    private List<McGoodsBean> mProducts = new ArrayList();

    private ProductAdapter mProductApter;

    private Long mTotalCount;

    private static final int PAGE_SIZE = 16;

    private int mPageIndex = 1;
    private CirclePageView mPageView;
    private int mTotalPageCount;

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

    @Override
    protected void backHome() {
        if(findViewById(R.id.btn_back_home)!=null){
            findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(ProductActivity.this, HomeActivity.class));
                }
            });
        }
    }
    private void initView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(App.ctx, 4));
        mRecyclerView.setLoadMoreListener(this);
        mSearchButton.setOnClickListener(this);
        mPrePageButton.setOnClickListener(this);
        mNextPageButton.setOnClickListener(this);
        initPages();

        mProductCode.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    loadByCode();
                    refresh();
                    return true;
                }
                return false;
            }

        });
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
            mPagesLayout.addView(mPageView);
        }
    }

    private void loadData() {
        QueryBuilder<McGoodsBean> queryBuilder = App.getDaoSession(App.getContext()).getMcGoodsBeanDao().queryBuilder();
        int offset = mPageIndex == 1 ? 0 : ((mPageIndex-1) * PAGE_SIZE);
        mProducts = queryBuilder.offset(offset).limit(PAGE_SIZE).orderAsc(McGoodsBeanDao.Properties.Mg_channo).list();
    }

    private void loadByCode() {
        if (!TextUtils.isEmpty(mProductCode.getText())) {
            QueryBuilder<McGoodsBean> queryBuilder = App.getDaoSession(App.getContext()).getMcGoodsBeanDao().queryBuilder();

            McGoodsBean result = queryBuilder
                    .where(McGoodsBeanDao.Properties.Mg_channo.eq("00" + mProductCode.getText()))
                    .unique();

            if (result != null && result.getMg_gnum().intValue() > 0
                    && result.getMg_chann_status().intValue() == ChanStatusEnum.NORMAL.getIndex()) {
                //go product detail
                finish();
                Intent intent = new Intent(this, ProductDetailActivity.class);
                intent.putExtra("gdNo", result.getGd_no());
                startActivity(intent);

            } else {
                Log.d("ProductActivity", mProductCode.getText() + "goods salesOut");
                ToastUtils.showShortMessage("该商品已售完！");
            }

        }
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
                loadByCode();
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

        super.onResume();
    }
}
