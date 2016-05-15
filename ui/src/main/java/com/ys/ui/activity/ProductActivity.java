package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ys.data.bean.McGoodsBean;
import com.ys.data.dao.McGoodsBeanDao;
import com.ys.ui.R;
import com.ys.ui.adapter.ProductAdapter;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.utils.PropertyUtils;
import com.ys.ui.view.LMRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import de.greenrobot.dao.query.QueryBuilder;

public class ProductActivity extends BaseActivity implements LMRecyclerView.LoadMoreListener {

    @Bind(R.id.recycler_view)
    LMRecyclerView mRecyclerView;

    @Bind(R.id.btn_back_home)
    ImageButton btnBackHome;


    @Bind(R.id.tv_timer)
    TextView tvTimer;

    private List<McGoodsBean> mProducts = new ArrayList();

    private ProductAdapter mProductApter;

    private long mTotalCount;

    private static final int mPageSize = 12;

    private int mPageIndex = 1;

    Timer timer;
    TimerTask timerTask;
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
        initTimer();
    }

    private void initView() {
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.btn_back_home) {
                    finish();
                    startActivity(new Intent(ProductActivity.this, HomeActivity.class));
                }


            }
        });

        mRecyclerView.setLayoutManager(new GridLayoutManager(App.ctx, 4));
        mRecyclerView.setLoadMoreListener(this);

    }

    private void loadData() {
        mTotalCount = App.getDaoSession(App.getContext()).getGoodsBeanDao().count();

        QueryBuilder<McGoodsBean> queryBuilder = App.getDaoSession(App.getContext()).getMcGoodsBeanDao().queryBuilder();
        int offset = mPageIndex == 1 ? 0 : (mPageIndex * mPageSize);
        List pageList = queryBuilder.offset(offset).limit(mPageSize).orderAsc(McGoodsBeanDao.Properties.Mg_channo).list();
        mProducts.addAll(pageList);

    }

    private void refresh() {
        mProductApter.setData(mProducts);
    }

    private void initTimer() {
        int timeout = PropertyUtils.getInstance().getTransTimeout();
        minute = timeout / 60;
        second = timeout % 60;

        tvTimer.setText(getTime());

        timerTask = new TimerTask() {

            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 0;
                handler.sendMessage(msg);
            }
        };

        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            String timer = getTime();
            if (TextUtils.isEmpty(timer)) {
                finish();
                startActivity(new Intent(ProductActivity.this, HomeActivity.class));

                return;
            }
            tvTimer.setText(timer);
        }
    };
    String getTime() {
        if (minute == 0) {
            if (second == 0) {
                return "";
            } else {
                second--;
                if (second >= 10) {
                    return "0" + minute + ":" + second;
                } else {
                    return "0" + minute + ":0" + second;
                }
            }
        } else {
            if (second == 0) {

                second = 59;
                minute--;
                if (minute >= 10) {
                    return minute + ":" + second;
                } else {
                    return "0" + minute + ":" + second;
                }
            } else {
                second--;
                if (second >= 10) {
                    if (minute >= 10) {
                        return minute + ":" + second;
                    } else {
                        return "0" + minute + ":" + second;
                    }
                } else {
                    if (minute >= 10) {
                        return minute + ":0" + second;
                    } else {
                        return "0" + minute + ":0" + second;
                    }
                }
            }
        }

    }
    @Override
    public void loadMore() {
        if (mProducts.size() < mTotalCount) {
            mPageIndex++;
            loadData();
            refresh();
        } else {
            //ToastUtils.showError("所有数据已经全部加载了", App.getContext());
        }
    }

}
