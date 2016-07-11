package com.ys.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tencent.bugly.crashreport.CrashReport;
import com.ys.data.bean.GoodsBean;
import com.ys.data.bean.McGoodsBean;
import com.ys.ui.R;
import com.ys.ui.activity.ProductDetailActivity;
import com.ys.ui.common.constants.ChanStatusEnum;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.utils.ImageUtils;
import com.ys.ui.utils.NumberUtils;
import com.ys.ui.utils.PropertyUtils;
import com.ys.ui.utils.ToastUtils;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangtao on 2016/7/10.
 */
public class GngxGridAdaper extends RecyclerView.Adapter<GngxGridAdaper.Holder>  {

    List<Map<String, Object>> datas;
    private Context context;

    public GngxGridAdaper(Context context, List<Map<String, Object>> images) {
        this.context = context;
        datas = images;
    }


    public void setData(List<Map<String, Object>> images) {
        this.datas = images;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.level2_gngx_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        //Map<String, Object> data = datas.get(position);


     }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_gngx)
        ImageView ivGngx;



        @OnClick(R.id.iv_gngx)
        void itemClick() {
            ToastUtils.showShortMessage("id=" + ivGngx.getId());

        }

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
