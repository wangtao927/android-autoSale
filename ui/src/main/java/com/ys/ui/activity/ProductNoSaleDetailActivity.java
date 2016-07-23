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
import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseTimerActivity;
import com.ys.ui.common.constants.SlTypeEnum;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.utils.ImageUtils;
import com.ys.ui.utils.PropertyUtils;

import butterknife.Bind;

public class ProductNoSaleDetailActivity extends BaseTimerActivity {

    @Bind(R.id.iv_gd_detail_image)
    ImageView gdDetailImage;

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void create(Bundle savedInstanceState) {

        init();

     }

    private void init() {
        //调用接口获取地址
        Bundle datas = getIntent().getExtras();
        gdNo = datas.getString("gdNo");

        goodsBean = DbManagerHelper.getGoodsInfo(gdNo);

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



}
