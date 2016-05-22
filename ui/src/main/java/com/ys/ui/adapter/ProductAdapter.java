package com.ys.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ys.data.bean.GoodsBean;
import com.ys.data.bean.McGoodsBean;
import com.ys.ui.R;
import com.ys.ui.activity.ProductDetailActivity;
import com.ys.ui.base.App;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.utils.ImageUtils;
import com.ys.ui.utils.NumberUtils;
import com.ys.ui.utils.PropertyUtils;
import com.ys.ui.utils.ToastUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by river on 2016/4/22.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Holder> {

    private List<McGoodsBean> data;
    private Context context;

    public ProductAdapter(Context context, List<McGoodsBean> lists) {
        this.context = context;
        data = lists;
    }


    public void setData(List<McGoodsBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final McGoodsBean mcGoodsBean = data.get(position);
        GoodsBean goodsBean = DbManagerHelper.getGoodsInfo(mcGoodsBean.getGd_no());
        holder.no.setTag(mcGoodsBean);
        holder.productName.setText(goodsBean.getGd_name());
        String cno = mcGoodsBean.getMg_channo();
        cno = cno.substring(cno.length() - 2);
        holder.no.setText(cno);

        if (mcGoodsBean != null) {
            holder.tvPrice.setText("￥"+NumberUtils.m2(mcGoodsBean.getMg_pre_price() * 0.01));
            boolean isShowPriceCut = mcGoodsBean.getMg_disc_price() != mcGoodsBean.getMg_pre_price();
            holder.ivPriceCut.setVisibility(isShowPriceCut ? View.VISIBLE : View.GONE);
        } else {
            holder.tvPrice.setText("无货");
            holder.ivPriceCut.setVisibility(View.GONE);
        }

        String url = PropertyUtils.getInstance().getFastDfsUrl() + ImageUtils.getImageUrl(goodsBean.getGd_img_s());

        Glide.with(context)
                .load(url)
                .centerCrop()
                .into(holder.productIcon);

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

        @Bind(R.id.no)
        TextView no;

        @Bind(R.id.tv_price)
        TextView tvPrice;

        @Bind(R.id.iv_price_cut)
        ImageView ivPriceCut;

        @OnClick(R.id.iv_picon)
        void itemClick() {
            final McGoodsBean mcGoodsBean = (McGoodsBean) no.getTag();
            if (mcGoodsBean.getMg_gnum() <= 0) {
                // 无货
                ToastUtils.showError("该药品暂时无货，请选择其他药品购买", App.getContext());
                return;
            }

            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("gdNo", mcGoodsBean.getGd_no());
            context.startActivity(intent);
        }

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
