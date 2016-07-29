package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ys.ui.R;
import com.ys.ui.adapter.GridViewAdapter;
import com.ys.ui.base.BaseTimerActivity;
import com.ys.ui.common.constants.YsConstants;
import com.ys.ui.view.CirclePageView;
import com.ys.ui.view.GridDataSet;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *  人体部位
 * Created by wangtao on 2016/7/8.
 */
public class YsRTBWActivity extends BaseTimerActivity implements View.OnClickListener {

    @Bind(R.id.gv_rtbw)
    GridView gridView;


    @Bind(R.id.tv_rtbw_desc)
    TextView tvRtbw;
    List<GridDataSet> dataList;

    GridViewAdapter gridViewAdapter;

    @Bind(R.id.ll_pages)
    LinearLayout mPagesLayout;
    @Bind(R.id.ib_pre_page)
    ImageButton mPrePageButton;

    @Bind(R.id.ib_next_page)
    ImageButton mNextPageButton;


    private int mPageIndex = 1;
    private CirclePageView mPageView;
    private int mTotalPageCount =2;

    @Override
    protected int getLayoutId() {
        return R.layout.level2_rtbw;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
        initView();
        loadData();

        gridViewAdapter = new GridViewAdapter(this, dataList);

        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridDataSet gridDataSet = dataList.get(position);
                finish();
                Intent intent = new Intent(YsRTBWActivity.this, YsDetailActivity.class);
                intent.putExtra("index", gridDataSet.getSubId());
                intent.putExtra("desc", gridDataSet.getDesc());
                startActivity(intent);
            }
        });

    }

    private void initView() {
        if (mPageIndex == 0 || mPageIndex == 1) {
            tvRtbw.setText("人体部位分类(一)");
        } else {
            tvRtbw.setText("人体部位分类(二)");
        }
        mNextPageButton.setOnClickListener(this);
        mPrePageButton.setOnClickListener(this);
        mPageView = new CirclePageView(this);
        mPageView.init(mTotalPageCount);
        mPagesLayout.addView(mPageView);

    }

    private void loadData() {
        dataList = YsConstants.getList(mPageIndex);
        if (mPageIndex == 0 || mPageIndex == 1) {
            tvRtbw.setText("人体部位分类(一)");
        } else {
            tvRtbw.setText("人体部位分类(二)");
        }
    }

    private void reLoadData() {
        mPageView.setSelectedPage(mPageIndex - 1);
        loadData();
        refresh();
    }

    private void refresh() {
        gridViewAdapter.setData(dataList);
    }
    @Override
    protected void backHome() {
        if(findViewById(R.id.btn_back_home)!=null){
            findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(YsRTBWActivity.this, YsActivity.class));
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_pre_page:
                if (mPageIndex > 1) {
                    mPageIndex--;
//                    finish();
//                    Intent intent = new Intent(YsRTBWActivity.this, YsRTBWActivity.class);
//                    intent.putExtra("index", 1);
//                    startActivity(intent);

                    reLoadData();
                }
                break;
            case R.id.ib_next_page:
                if (mPageIndex < mTotalPageCount) {
                    mPageIndex++;
                    reLoadData();
//                    finish();
//                    Intent intent = new Intent(YsRTBWActivity.this, YsRTBWActivity.class);
//                    intent.putExtra("index", 2);
//                    startActivity(intent);
                }
                break;
        }
    }
}
