package com.ys.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.ys.data.bean.GoodsBean;
import com.ys.data.bean.McGoodsBean;
import com.ys.data.bean.McStatusBean;
import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.common.constants.SlPayStatusEnum;
import com.ys.ui.common.constants.SlTypeEnum;
import com.ys.ui.common.http.RetrofitManager;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.common.request.SaleListVo;
import com.ys.ui.common.response.CommonResponse;
import com.ys.ui.common.response.CreateOrderResult;
import com.ys.ui.common.response.SaleListResult;
import com.ys.ui.serial.print.activity.PrintHelper;
import com.ys.ui.utils.ImageUtils;
import com.ys.ui.utils.PropertyUtils;
import com.ys.ui.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by river on 2016/4/19.
 */
public class PayActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tv_gd_name)
    TextView tvGdName;

    @Bind(R.id.tv_price)
    TextView tvPrice;

    @Bind(R.id.tv_vip_price)
    TextView tvVipPrice;

    @Bind(R.id.iv_gd_detail_image)
    ImageView gdDetailImage;
    @Bind(R.id.tv_timer)
    TextView tvTimer;


    @Bind(R.id.btn_dir_buy)
    Button btnDirBuy;

    @Bind(R.id.btn_vip_buy)
    Button btnVipBuy;
    private GoodsBean goodsBean;
    private McGoodsBean mcGoodsBean;

    private SlTypeEnum slType;

    String gdNameValue = "商品名：%s";
    String gdPrice = "价格：%s 元";
    String gdVipPrice = "会员价: %s 元";



    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_phone;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
        //调用接口获取地址
        init();
        btnDirBuy.setOnClickListener(this);
        btnVipBuy.setOnClickListener(this);
        initTimer();

    }
    private void init () {
        //调用接口获取地址
        Bundle datas = getIntent().getExtras();

        goodsBean = (GoodsBean)getIntent().getSerializableExtra("goods");
        mcGoodsBean = (McGoodsBean)getIntent().getSerializableExtra("mcGoods");

        int type = datas.getInt("type");
        slType = SlTypeEnum.findByIndex(type);
        //
        Glide.with(App.getContext())
                .load(PropertyUtils.getInstance().getFastDfsUrl() + ImageUtils.getImageUrl(goodsBean.getGd_img_s()))
                .into(gdDetailImage);

        tvGdName.setText(String.format(gdNameValue, goodsBean.getGd_short_name()));
        tvPrice.setText(String.format(gdPrice, getPrice(goodsBean.getGd_disc_price())));
        tvVipPrice.setText(String.format(gdVipPrice, getPrice(goodsBean.getGd_vip_price())));
    }
    private void initTimer() {
        int timeout = PropertyUtils.getInstance().getTransTimeout();
        minute = timeout/60;
        second = timeout%60;

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
       // timer.schedule(timerTask, 0, 1000);
    }
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            String timer = getTime();
            if (TextUtils.isEmpty(timer)) {
                finish();
                startActivity(new Intent(PayActivity.this, HomeActivity.class));
            }
            tvTimer.setText(timer);
        }
    };


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_dir_buy:

                Intent intent = new Intent(PayActivity.this, QRcodeActivity.class);

                intent.putExtra("goods", goodsBean);
                intent.putExtra("mcGoods", mcGoodsBean);
                intent.putExtra("type", slType.getIndex());
                startActivity(intent);
                finish();
                // createOrder(String.valueOf(slType.getIndex()));
                break;

            case R.id.btn_vip_buy:
                // 登录框  输入手机号 密码

                //createOrder(String.valueOf(slType.getIndex()));

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        handler.removeCallbacksAndMessages(null);
    }
}
