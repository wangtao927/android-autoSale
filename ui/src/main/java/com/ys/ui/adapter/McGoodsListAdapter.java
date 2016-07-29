package com.ys.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ys.data.bean.GoodsBean;
import com.ys.data.bean.McGoodsBean;
import com.ys.data.dao.GoodsBeanDao;
import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.common.constants.ChanStatusEnum;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangtao on 2016/4/10.
 */
public class McGoodsListAdapter extends RecyclerView.Adapter<McGoodsListAdapter.Holder> {
     private List<McGoodsBean> data;
    private Context context;

    public McGoodsListAdapter(Context context, List<McGoodsBean> lists) {
        this.context = context;
        data = lists;

    }


    public void setData(List<McGoodsBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public McGoodsListAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mcgoods_item, parent, false);
        return new McGoodsListAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(McGoodsListAdapter.Holder holder, int position) {
        final McGoodsBean goodsView = data.get(position);
        GoodsBean goodsBean = DbManagerHelper.getGoodsInfo(goodsView.getGd_no());
        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.channo.setTag(goodsView);
        holder.channo.setText(goodsView.getMg_channo());
        holder.gdNo.setText(goodsView.getGd_no());

        holder.gdName.setText(goodsBean.getGd_name());
        if (goodsView.getMg_gvol() != null) {
            holder.gvol.setText(String.valueOf(goodsView.getMg_gvol()));

        }
        if (goodsView.getMg_gnum() != null) {
            holder.gnum.setText(String.valueOf(goodsView.getMg_gnum()));
        }
        if (goodsView.getMg_chann_status() != null) {

            boolean isError = false;
            if (2 == goodsView.getMg_chann_status().intValue()) {

                isError = true;
            }
            holder.chanStatus.setChecked(isError);

        } else {
            holder.chanStatus.setChecked(false);
        }

        holder.chanStatus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                CheckBox checkBox = (CheckBox) view;

                // 去修改数据库
                McGoodsBean bean = goodsView;
                bean.setMg_chann_status(checkBox.isChecked() ? Long.valueOf(ChanStatusEnum.ERROR.getIndex()) : ChanStatusEnum.NORMAL.getIndex());
//                bean.setMg_gvol(goodsView.getMg_gvol());
//                bean.setMg_gnum(goodsView.getMg_gnum());
                App.getDaoSession(App.getContext()).getMcGoodsBeanDao().updateChannelStatusByPK(bean);
                checkBox.setChecked(checkBox.isChecked());
                McGoodsListAdapter.this.notifyDataSetChanged();

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        @Bind(R.id.channo)
        TextView channo;

        @Bind(R.id.gdNo)
        TextView gdNo;

        @Bind(R.id.gdName)
        TextView gdName;

        @Bind(R.id.gvol)
        TextView gvol;

        @Bind(R.id.gnum)
        TextView gnum;

        @Bind(R.id.mg_chann_status)
        CheckBox chanStatus;

        @OnClick(R.id.ll_item)
        void itemClick() {
            final McGoodsBean goodsBean = (McGoodsBean) channo.getTag();

            // 弹出一个输入框， 修改库存
            final EditText inputServer = new EditText(context);
            inputServer.setInputType(InputType.TYPE_CLASS_NUMBER);
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("修改货道：" +  goodsBean.getMg_channo() + "库存").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
                    .setNegativeButton("返回", null);
            builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    Long kucun = null;
                    try {
                        kucun = Long.valueOf(inputServer.getText().toString());
                    } catch (NumberFormatException e) {
                        ToastUtils.showError("库存数量必须是整数", context);
                        return;
                    }
                    if (kucun.intValue() > goodsBean.getMg_gvol()) {
                        ToastUtils.showError("库存数量不能大于容量", context);
                        return;
                    }

                    goodsBean.setMg_gnum(Long.valueOf(inputServer.getText().toString()));
                    App.getDaoSession(App.getContext()).getMcGoodsBeanDao().updateGnumByPK(goodsBean);

                    notifyDataSetChanged();
                }
            });
            builder.show();
        }

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
