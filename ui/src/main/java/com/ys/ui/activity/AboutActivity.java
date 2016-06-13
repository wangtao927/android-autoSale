package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ys.ui.R;
import com.ys.ui.adapter.GalleryAdapter;
import com.ys.ui.base.BaseTimerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangtao on 2016/6/9.
 */
public class AboutActivity extends BaseTimerActivity implements View.OnClickListener {

    @Bind(R.id.logo)
    ImageView logo;

    Gallery main_gallery;
    LinearLayout main_lin;

    List<Object> li;

    Integer[] res = {R.mipmap.zj1, R.mipmap.zj2, R.mipmap.zj3};

    GalleryAdapter gallery_adapter;

    int current_circle = 0;

    Runnable timeadv;
    int count;

    long[] mHits = new long[5];

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
        logo.setOnClickListener(this);

//        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
//        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
//
//        viewlist.add(BitmapFactory.decodeResource(getResources(), (R.mipmap.zj1)));
//        viewlist.add(BitmapFactory.decodeResource(getResources(), (R.mipmap.zj2)));
//        viewlist.add(BitmapFactory.decodeResource(getResources(), (R.mipmap.zj3)));
//
//        viewpager.setAdapter(new PagerAdapter() {
//
//
//            //viewpager中的组件数量
//            @Override
//            public int getCount() {
//                return viewlist.size();
//            }
//
//
//            @Override
//            public boolean isViewFromObject(View arg0, Object arg1) {
//                return arg0 == arg1;
//            }
//
//
//        });
//
//        indicator.setViewPager(viewpager);

        initview();
        li = new ArrayList<Object>();
        for (int i = 0; i < res.length; i++) {
            li.add(res[i]);
        }

        gallery_adapter = new GalleryAdapter(this);

        main_gallery.setAdapter(gallery_adapter);
        gallery_adapter.setList(li);
        setCircle();

        //设置滚动图片的时候，对应小圆点的图片切换
        main_gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                View v = main_lin.getChildAt(position);
                View cuview = main_lin.getChildAt(current_circle);

                if (v != null && cuview != null) {
                    ImageView pointView = (ImageView) v;
                    ImageView curpointView = (ImageView) cuview;
                    curpointView
                            .setBackgroundResource(R.drawable.circle_page);
                    pointView
                            .setBackgroundResource(R.drawable.circle_page_selected);
                    current_circle = position;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initview() {
        main_gallery = (Gallery) this.findViewById(R.id.main_gallery);
        main_lin = (LinearLayout) this.findViewById(R.id.main_lin);

    }

    //设置滚动图片的小圆点
    private void setCircle() {
        for (int i = 0; i < li.size(); i++) {
            ImageView iv = new ImageView(this);
            //循环创建小圆点，判断第一个小圆点为白色的，其他的都是透明的
            if (i == 0) {
                iv.setBackgroundResource(R.drawable.circle_page_selected);
            } else {
                iv.setBackgroundResource(R.drawable.circle_page);
            }
            main_lin.addView(iv);

            //设置小圆点的margin值
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(20, 20, 1);
            lp.setMargins(10, 20, 10, 20);
            iv.setLayoutParams(lp);
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.logo:

                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
                //实现左移，然后最后一个位置更新距离开机的时间，如果最后一个时间和最开始时间小于3000，即双击
                mHits[mHits.length - 1] = SystemClock.uptimeMillis();
                if (mHits[0] >= (SystemClock.uptimeMillis() - 3000)) {

                    Intent detailIntent = new Intent(this, AdminLoginActivity.class);
                    finish();
                    startActivity(detailIntent);
                }

                 break;

            default:

                break;
        }

    }

    protected void backHome() {
        if (findViewById(R.id.btn_back_home) != null) {
            findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(AboutActivity.this, HomeActivity.class));
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
