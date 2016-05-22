package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ys.data.bean.AdvBean;
import com.ys.data.bean.McGoodsBean;
import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.utils.ToastUtils;


import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.logo)
    ImageView logo;
    @Bind(R.id.tel)
    ImageView tel;
    @Bind(R.id.btn_buy)
    ImageButton btnBuy;
    @Bind(R.id.btn_get_drug)
    ImageButton btnGetDrug;
    @Bind(R.id.btn_smart)
    ImageButton btnSmart;
    @Bind(R.id.btn_shop)
    ImageButton btnShop;
    @Bind(R.id.btn_about)
    ImageButton btnAbout;
    @Bind(R.id.btn_member)
    ImageButton btnMember;
    @Bind(R.id.btn_statement)
    ImageButton btnStatement;
    @Bind(R.id.btn_help)
    ImageButton btnHelp;

    @Bind(R.id.ad)
    ImageView ad;

    Handler mhandler = new Handler();

    public int adIndex;

    //int[] ads = {R.mipmap.ad1,R.mipmap.adv_1, R.mipmap.adv_2};
    List<AdvBean> list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
        btnGetDrug.setOnClickListener(this);
        btnBuy.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
        ad.setOnClickListener(this);
        // 如果终端号不存在， 则跳转到设置终端信息界面
        if (!isInit()) {
            // 弹出层，输入终端号，初始化机器
            finish();
            startActivity(new Intent(this, TermInitActivity.class));

            return;
        } else {
            // 启动service
//            Intent intent = new Intent(this, TimerService.class);
//            startService(intent);
        }

        list = App.getDaoSession(App.getContext()).getAdvBeanDao().loadAll();

        adsStart();
    }

    private void adsStart() {
        mhandler.postDelayed(new Runnable() {

            @Override
            public void run() {
//                if (adIndex >= list.size()) {
//                    adIndex = 0;
//                }
                Glide.with(HomeActivity.this)
                        .load(list.get(adIndex).getFileUrl())
                        .centerCrop()
                        .into(ad);

                adIndex++;
                if (adIndex >= list.size()) {
                    adIndex = 0;
                }
                adsStart();
            }
        }, 3000);
    }

    private boolean isInit() {
       // DaoSession session = App.getDaoSession(App.getContext());
        if(App.mcNo == null){
            return false;
        } else {
            // 展示 终端号
            //ToastUtils.showShortMessage(App.mcNo);
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_drug:
                finish();
                startActivity(new Intent(HomeActivity.this, GetProductActivity.class));
                break;
            case R.id.btn_buy:
                finish();
                startActivity(new Intent(HomeActivity.this, ProductActivity.class));
                break;
            case R.id.btn_about:
                finish();
                startActivity(new Intent(HomeActivity.this, AdminActivity.class));
                break;
            case R.id.ad:

                    String gdNo = list.get(adIndex).getAi_gd_no();
                    if (!TextUtils.isEmpty(gdNo)) {
                        // 跳转到明细界面
                        // 判断下 是否有该商品编号
                        McGoodsBean bean = DbManagerHelper.getOutGoods(gdNo);
                        if (bean != null) {
                            Intent intent = new Intent(this, ProductDetailActivity.class);
                            intent.putExtra("gdNo", gdNo);
                            finish();
                            startActivity(intent);
                        }
                    }



                break;
            default:
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (mhandler != null) {
            mhandler.removeCallbacksAndMessages(null);
        }
    }

}
