package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ys.data.bean.GoodsBean;
import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseTimerActivity;
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

    String gdNameValue = "商品名：%s";
    String gdPrice = "价    格：%s 元";
    String gdVipPrice = "会员价：%s 元";
    String desc = "%s \n[规格] %s \n [配方] %s \n [功能主治] %s \n [用法用量] %s";

    private String gdNo = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nosale_detail;
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

        tvPrice.setText(String.format(gdPrice, getPrice(goodsBean.getGd_disc_price())));
        tvVipPrice.setText(String.format(gdVipPrice, getPrice(goodsBean.getGd_vip_price())));
        tvDesc.setText(String.format(desc, goodsBean.getGd_desc(),
                goodsBean.getGd_spec(), goodsBean.getGd_desc1(), goodsBean.getGd_desc2(), goodsBean.getGd_desc3()));
    }

    @Override
    protected void backHome() {
        if(findViewById(R.id.btn_back_home)!=null){
            findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(ProductNoSaleDetailActivity.this, YsActivity.class));
                }
            });
        }
    }

}
