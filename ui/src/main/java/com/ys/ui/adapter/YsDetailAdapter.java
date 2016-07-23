package com.ys.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tencent.bugly.crashreport.CrashReport;
import com.ys.data.bean.GoodsBean;
import com.ys.data.bean.McGoodsBean;
import com.ys.ui.R;
import com.ys.ui.activity.ProductDetailActivity;
import com.ys.ui.activity.ProductNoSaleDetailActivity;
import com.ys.ui.common.constants.ChanStatusEnum;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.utils.ImageUtils;
import com.ys.ui.utils.NumberUtils;
import com.ys.ui.utils.PropertyUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by river on 2016/4/22.
 */
public class YsDetailAdapter extends RecyclerView.Adapter<YsDetailAdapter.Holder> {

    private List<GoodsBean> data;
    private Context context;

    public YsDetailAdapter(Context context, List<GoodsBean> lists) {
        this.context = context;
        data = lists;
    }


    public void setData(List<GoodsBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ys_list_goods_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final GoodsBean goodsBean = data.get(position);

            holder.productName.setTag(goodsBean);
            String url = PropertyUtils.getInstance().getFastDfsUrl() + ImageUtils.getImageUrl(goodsBean.getGd_img_s());

            Glide.with(context)
                    .load(url).fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(holder.productIcon);
         holder.productName.setText(goodsBean.getGd_short_name());

        if (goodsBean.getGd_desc2()!= null && goodsBean.getGd_desc2().length() > 30) {
            goodsBean.setGd_desc2(goodsBean.getGd_desc2().substring(30));
        }

            holder.tvPrice.setText("￥"+NumberUtils.m2(goodsBean.getGd_sale_price() * 0.01));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_picon)
        ImageView productIcon;

        @Bind(R.id.tv_pname)
        TextView productName;

        @Bind(R.id.tv_price)
        TextView tvPrice;

        @OnClick(R.id.iv_picon)
        void itemClick() {
            GoodsBean goodsBean = (GoodsBean) productName.getTag();

            Intent intent;
            McGoodsBean mcGoodsBean = DbManagerHelper.getOutGoods(goodsBean.getGd_no());
            if (mcGoodsBean == null) {
                intent = new Intent(context, ProductNoSaleDetailActivity.class);
                intent.putExtra("gdNo", goodsBean.getGd_no());
            } else {
                intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("gdNo", goodsBean.getGd_no());
                intent.putExtra("channo", mcGoodsBean.getMg_channo());

            }
            context.startActivity(intent);
        }

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
