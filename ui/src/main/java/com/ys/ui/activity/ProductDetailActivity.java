package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tencent.bugly.crashreport.CrashReport;
import com.ys.data.bean.GoodsBean;
import com.ys.data.bean.McGoodsBean;
import com.ys.data.bean.McStatusBean;
import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseTimerActivity;
import com.ys.ui.common.constants.SlTypeEnum;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.utils.ImageUtils;
import com.ys.ui.utils.PropertyUtils;

import butterknife.Bind;

public class ProductDetailActivity extends BaseTimerActivity implements View.OnClickListener {

    @Bind(R.id.iv_gd_detail_image)
    ImageView gdDetailImage;

    @Bind(R.id.btn_buy_wx)
    ImageButton btnWx;

    @Bind(R.id.btn_buy_zfb)
    ImageButton btnZfb;

    @Bind(R.id.btn_yl)
    ImageButton btnYl;
//
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

    private GoodsBean goodsBean;
    private McGoodsBean mcGoodsBean;

    String gdNameValue = "商品名：%s";
    String gdPrice = "价    格：%s 元";
    String gdVipPrice = "会员价：%s 元";
    String desc = "%s \n[规格] %s \n [配方] %s \n [功能主治] %s \n [用法用量] %s";

    private String gdNo = "";
    private String channo = "";

    private int origin;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void create(Bundle savedInstanceState) {

        init();
        if (!isSupportCash) {
            btnSf.setVisibility(View.GONE);
        } else {
            btnSf.setOnClickListener(this);
        }
        if (!isSupportAlipay) {
            btnZfb.setVisibility(View.GONE);
        } else {
            btnZfb.setOnClickListener(this);
        }
        if (!isSupportWx) {
            btnWx.setVisibility(View.GONE);
        } else {
            btnWx.setOnClickListener(this);
        }
        if (!isSupportPos) {
            btnYl.setVisibility(View.GONE);
        } else {
            btnYl.setOnClickListener(this);

        }
     }
    public  boolean isSupportWx = true;
    public  boolean isSupportPos = true;
    public  boolean isSupportAlipay = true;
    public  boolean isSupportCash = false;

    private void init() {
        try {
            McStatusBean mcStatusBean = DbManagerHelper.getMcStatusBean();
            isSupportWx = mcStatusBean.getMc_iswxpay().intValue() == 1;
            isSupportPos = mcStatusBean.getMc_isuppos().intValue() == 1;
            isSupportCash = mcStatusBean.getMc_isbiller().intValue() == 1;
            isSupportAlipay = mcStatusBean.getMc_isalipay().intValue() == 1;
        } catch (Exception e) {
            CrashReport.postCatchedException(e);
        }

        //调用接口获取地址
        Bundle datas = getIntent().getExtras();
        gdNo = datas.getString("gdNo");
        channo = datas.getString("channo");
        origin = datas.getInt("origin");
        goodsBean = DbManagerHelper.getGoodsInfo(gdNo);
        mcGoodsBean = DbManagerHelper.getOutGoodsByChanno(channo);

        if (mcGoodsBean == null) {
            CrashReport.postCatchedException(new Exception(App.mcNo + "mcGoodsBean is null, channo=" + channo + "gdNo=" + gdNo));

            return;
        }
        Glide.with(App.getContext())
                .load(PropertyUtils.getInstance().getFastDfsUrl() + ImageUtils.getImageUrl(goodsBean.getGd_img_s()))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(gdDetailImage);

        tvGdName.setText(String.format(gdNameValue, goodsBean.getGd_short_name()));

        tvPrice.setText(String.format(gdPrice, getPrice(mcGoodsBean.getMg_disc_price())));
        tvVipPrice.setText(String.format(gdVipPrice, getPrice(mcGoodsBean.getMg_vip_price())));
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
//            case R.id.btn_sf:
//
//                ToastUtils.showShortMessage("该售货机暂不支持现金支付");
//                startPay(SlTypeEnum.CASH);
//
//                break;
            default:
                break;

        }
    }

    @Override
    protected void backHome() {
        if(findViewById(R.id.btn_back_home)!=null){
            findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    if (origin == 0) {
                        startActivity(new Intent(ProductDetailActivity.this, ProductActivity.class));

                    } else if (origin == 11) {
                        // 会员特惠
                        finish();



                    } else  {
                        startActivity(new Intent(ProductDetailActivity.this, YsActivity.class));

                    }
                }
            });
        }
    }

    private void startPay(SlTypeEnum slTypeEnum) {

        finish();
        Intent intent = null;
        if (slTypeEnum == SlTypeEnum.CASH) {
            intent = new Intent(ProductDetailActivity.this, PayByCoinActivity.class);

         } else {
            intent = new Intent(ProductDetailActivity.this, PayActivity.class);

        }


        intent.putExtra("goods", goodsBean);
        intent.putExtra("mcGoods", mcGoodsBean);
        intent.putExtra("type", slTypeEnum.getIndex());

        startActivity(intent);
    }
}
