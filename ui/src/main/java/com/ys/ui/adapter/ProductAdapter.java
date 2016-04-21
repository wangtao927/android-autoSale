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
import com.ys.ui.R;
import com.ys.ui.activity.ProductDetailActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by river on 2016/4/22.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Holder> {
    private List<GoodsBean> data;
    private Context context;

    public ProductAdapter(Context context, List<GoodsBean> lists) {
        this.context = context;
        data = lists;
    }


    public void setData(List<GoodsBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.Holder holder, int position) {
        final GoodsBean bean = data.get(position);
        holder.productName.setTag(bean);
        holder.productName.setText(bean.getGd_name());

        Glide.with(context)
                .load(R.drawable.sky)
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

        @OnClick(R.id.iv_picon)
        void itemClick() {
            final GoodsBean goodsBean = (GoodsBean) productName.getTag();
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("gd_no", goodsBean.getGd_no());
            context.startActivity(intent);
        }

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
