package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ys.data.bean.GoodsBean;
import com.ys.data.bean.McGoodsBean;
import com.ys.data.bean.McStatusBean;
import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.common.constants.SlTypeEnum;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.utils.ImageUtils;
import com.ys.ui.utils.PropertyUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

public class ProductDetailActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.iv_gd_detail_image)
    ImageView gdDetailImage;

    @Bind(R.id.btn_buy_wx)
    ImageButton btnWx;

    @Bind(R.id.btn_buy_zfb)
    ImageButton btnZfb;

    @Bind(R.id.btn_yl)
    ImageButton btnYl;

    @Bind(R.id.btn_sf)
    ImageButton btnSf;

    @Bind(R.id.tv_gd_name)
    TextView tvGdName;

    @Bind(R.id.tv_price)
    TextView tvPrice;

    @Bind(R.id.tv_vip_price)
    TextView tvVipPrice;

    @Bind(R.id.tv_desc)
    TextView tvDesc;

    @Bind(R.id.btn_back_home)
    ImageButton btnBackHome;

    private GoodsBean goodsBean;
    private McGoodsBean mcGoodsBean;

    String gdNameValue = "商品名：%s";
    String gdPrice = "价格：%s 元";
    String gdVipPrice = "会员价: %s 元";
    String desc = "%s \n[规格] %s \n [配方] %s \n [功能主治] %s \n [用法用量] %s";

    private String gdNo = "";

    @Bind(R.id.tv_timer)
    TextView tvTimer;
    Timer timer;
    TimerTask timerTask;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void create(Bundle savedInstanceState) {

        init();
        btnWx.setOnClickListener(this);
        btnZfb.setOnClickListener(this);
        btnYl.setOnClickListener(this);
        btnSf.setOnClickListener(this);
        btnBackHome.setOnClickListener(this);
        initTimer();
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
            if (!isFinishing()) {
                String timer = getTime();
                if (TextUtils.isEmpty(timer)) {
                    startActivity(new Intent(ProductDetailActivity.this, HomeActivity.class));
                    finish();
                    return;
                }
                tvTimer.setText(timer);
            }
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
    private void init() {
        //调用接口获取地址
        Bundle datas = getIntent().getExtras();
        gdNo = datas.getString("gdNo");

        goodsBean = DbManagerHelper.getGoodsInfo(gdNo);
        mcGoodsBean = DbManagerHelper.getOutGoods(gdNo);

        Glide.with(App.getContext())
                .load(PropertyUtils.getInstance().getFastDfsUrl() + ImageUtils.getImageUrl(goodsBean.getGd_img_s()))
                .into(gdDetailImage);

        tvGdName.setText(String.format(gdNameValue, goodsBean.getGd_short_name()));
        tvPrice.setText(String.format(gdPrice, getPrice(goodsBean.getGd_disc_price())));
        tvVipPrice.setText(String.format(gdVipPrice, getPrice(goodsBean.getGd_vip_price())));
        tvDesc.setText(String.format(desc, goodsBean.getGd_desc(),
                goodsBean.getGd_spec(), goodsBean.getGd_desc1(), goodsBean.getGd_desc2(), goodsBean.getGd_desc3()));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_buy_wx:

                startPay(SlTypeEnum.WX);
                break;
            case R.id.btn_buy_zfb:
                startPay(SlTypeEnum.ALIPAY);

                break;
            case R.id.btn_yl:
                startPay(SlTypeEnum.CARD);
                break;
            case R.id.btn_sf:
                startPay(SlTypeEnum.CARD);

                break;
            case R.id.btn_back_home:
//                finish();
//                startActivity(new Intent(ProductDetailActivity.this, HomeActivity.class));
                backHome(v);
                break;
            default:
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }

        if (timerTask != null) {
            timerTask.cancel();
        }

        if (timer != null) {
            timer.cancel();
        }

    }

    private void startPay(SlTypeEnum slTypeEnum) {

        Intent intent = new Intent(ProductDetailActivity.this, PayActivity.class);

        intent.putExtra("goods", goodsBean);
        intent.putExtra("mcGoods", mcGoodsBean);
        intent.putExtra("type", slTypeEnum.getIndex());
        startActivity(intent);
    }
}
