package com.ys.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ys.data.bean.SaleListBean;
import com.ys.ui.R;
import com.ys.ui.common.constants.SlOutStatusEnum;
import com.ys.ui.common.constants.SlPayStatusEnum;
import com.ys.ui.common.constants.SlSendStatusEnum;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangtao on 2016/4/10.
 */
public class SaleListAdapter extends RecyclerView.Adapter<SaleListAdapter.Holder> {
    private List<SaleListBean> data;
    private Context context;

    public SaleListAdapter(Context context, List<SaleListBean> lists) {
        this.context = context;
        data = lists;

    }


    public void setData(List<SaleListBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public SaleListAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.salelist_item, parent, false);
        return new SaleListAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(SaleListAdapter.Holder holder, int position) {
        final SaleListBean saleListView = data.get(position);
        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.slNo.setText(saleListView.getSl_no());
        holder.channo.setText(saleListView.getSl_chann());
        holder.gdName.setText(saleListView.getSl_gd_name());
        holder.payStatus.setText(SlPayStatusEnum.getPayStatusByIndex(getIndex(saleListView.getSl_pay_status())).getDesc());
        holder.outStatus.setText(SlOutStatusEnum.getOutStatusByIndex(getIndex(saleListView.getSl_out_status())).getDesc());
        holder.sendStatus.setText(SlSendStatusEnum.getSendStatusByIndex(getIndex(saleListView.getSl_send_status())).getDesc());

        holder.transTime.setText(saleListView.getSl_time());

    }

    private int getIndex(String status) {
        if (TextUtils.isEmpty(status)) {
            return 0;
        } else {
            return Integer.valueOf(status);
        }
    }
    private int getIndex(Long status) {
        if (status == null) {
            return 0;
        } else {
            return status.intValue();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_sl_no)
        TextView slNo;
        @Bind(R.id.tv_channo)
        TextView channo;

        @Bind(R.id.tv_gd_name)
        TextView gdName;

        @Bind(R.id.tv_pay_status)
        TextView payStatus;

        @Bind(R.id.tv_out_status)
        TextView outStatus;

        @Bind(R.id.tv_send_status)
        TextView sendStatus;

        @Bind(R.id.tv_trans_time)
        TextView transTime;



        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
