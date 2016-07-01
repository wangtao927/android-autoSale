package com.ys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tencent.bugly.crashreport.CrashReport;
import com.ys.data.bean.AdvBean;
import com.ys.data.bean.McGoodsBean;
import com.ys.data.bean.SaleListBean;
import com.ys.data.dao.AdvBeanDao;
import com.ys.data.dao.McGoodsBeanDao;
import com.ys.data.dao.SaleListBeanDao;
import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.utils.RandomUtils;
import com.ys.ui.utils.ToastUtils;
import com.ys.ui.utils.Utils;

import java.util.List;

import butterknife.Bind;
import de.greenrobot.dao.query.Query;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

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
    List<AdvBean> adsList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
        btnGetDrug.setOnClickListener(this);
        btnBuy.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
        btnStatement.setOnClickListener(this);
        btnHelp.setOnClickListener(this);
        ad.setOnClickListener(this);
        // 如果终端号不存在， 则跳转到设置终端信息界面
        if (!isInit()) {
            // 弹出层，输入终端号，初始化机器
            finish();
            startActivity(new Intent(this, TermInitActivity.class));
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //if (adsList == null) {
            adsList = App.getDaoSession(App.getContext()).getAdvBeanDao().queryBuilder().where(AdvBeanDao.Properties.Ai_type.eq("1")).list();
       // }
        adsStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mhandler != null) {
            mhandler.removeCallbacksAndMessages(null);
        }
    }

    private void adsStart() {
        if (adsList == null || adsList.isEmpty()) {
            Glide.with(HomeActivity.this)
                    .load(R.mipmap.ad1)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ad);
        } else {
            Glide.with(HomeActivity.this)
                    .load(adsList.get(adIndex).getFileUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ad);

            mhandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    Glide.with(HomeActivity.this)
                            .load(adsList.get(adIndex).getFileUrl())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(ad);

                    adIndex++;
                    if (adIndex >= adsList.size()) {
                        adIndex = 0;
                    }
                    adsStart();
                }
            }, 10000);
        }
    }


    @Override
    public void onClick(View view) {
        if (Utils.isFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_get_drug:
                Intent intent = new Intent(HomeActivity.this, GetProductActivity.class);
                String adimgUrl = null;
                //随机一张图片
                if (!adsList.isEmpty()) {
                    int randomIndex = RandomUtils.getRandom(0, adsList.size() - 1);
                    adimgUrl = adsList.get(randomIndex).getFileUrl();
                }

                intent.putExtra("adimg", adimgUrl);
                startActivity(intent);
                break;
            case R.id.btn_buy:
                startActivity(new Intent(HomeActivity.this, ProductActivity.class));
                break;
            case R.id.btn_about:
                startActivity(new Intent(HomeActivity.this, AboutActivity.class));
                break;
            case R.id.btn_statement:
                startActivity(new Intent(HomeActivity.this, MZActivity.class));

                break;
            case R.id.btn_help:
                startActivity(new Intent(HomeActivity.this, HelpActivity.class));


                break;
            case R.id.ad:
                if (adIndex > adsList.size()) adIndex = 0;
                String gdNo = adsList.get(adIndex).getAi_gd_no();
                if (!TextUtils.isEmpty(gdNo)) {
                    // 跳转到明细界面
                    // 判断下 是否有该商品编号
                    McGoodsBean bean = DbManagerHelper.getOutGoods(gdNo);
                    if (bean != null) {
                        Intent detailIntent = new Intent(this, ProductDetailActivity.class);
                        detailIntent.putExtra("gdNo", gdNo);
                        detailIntent.putExtra("channo", bean.getMg_channo());
//                        finish();
                        startActivity(detailIntent);
                    }
                }
                break;
            default:
                break;

        }
    }

}
