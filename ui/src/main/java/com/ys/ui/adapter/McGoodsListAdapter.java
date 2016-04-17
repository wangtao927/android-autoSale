package com.ys.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ys.data.bean.GoodsBean;
import com.ys.data.bean.McGoodsBean;
import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.view.McGoodsView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangtao on 2016/4/10.
 */
public class McGoodsListAdapter extends ArrayAdapter<McGoodsBean> {

    private int resouceId;

    private Map<String, GoodsBean> goodsMap;
    public McGoodsListAdapter(Context context, int textViewResourceId, List<McGoodsBean> objects) {
        super(context, textViewResourceId, objects);
        this.resouceId = textViewResourceId;
        initGoodsMap();
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parant) {


        final McGoodsBean goodsView = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resouceId, null);
        } else {
            view = convertView;
        }
        final TextView channo = (TextView)view.findViewById(R.id.channo);
        TextView gdNo = (TextView)view.findViewById(R.id.gdNo);
        TextView gdName = (TextView)view.findViewById(R.id.gdName);
        TextView gvol = (TextView)view.findViewById(R.id.gvol);
        TextView gnum = (TextView)view.findViewById(R.id.gnum);

        CheckBox chanStatus = (CheckBox)view.findViewById(R.id.chanStatus);
        channo.setText(goodsView.getMg_channo());
        gdNo.setText(goodsView.getGd_no());

        gdName.setText(goodsMap.get(goodsView.getGd_no()).getGd_name());
        if (goodsView.getMg_gvol() != null) {
            gvol.setText(String.valueOf(goodsView.getMg_gvol()));

        }
        if (goodsView.getMg_gnum() != null) {
            gnum.setText(String.valueOf(goodsView.getMg_gnum()));
        }
        if (goodsView.getChanStatus() != null) {

            boolean isError = false;
            if (2 == goodsView.getChanStatus().intValue()) {

                isError = true;
            }
            chanStatus.setChecked(isError);

        } else {

            chanStatus.setChecked(false);
        }

        chanStatus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                CheckBox checkBox = (CheckBox)view;

             // 去修改数据库
                McGoodsBean bean = goodsView;
                bean.setChanStatus(checkBox.isChecked() ? 2L : 1L);
//                bean.setMg_gvol(goodsView.getMg_gvol());
//                bean.setMg_gnum(goodsView.getMg_gnum());
                App.getDaoSession(App.getContext()).getMcGoodsBeanDao().updateChannelStatusByPK(bean);
                checkBox.setChecked(checkBox.isChecked());
                McGoodsListAdapter.this.notifyDataSetChanged();

            }
        });


        return view;

    }

    private void initGoodsMap() {
        List<GoodsBean> goodsBeanList = App.getDaoSession(App.getContext()).getGoodsBeanDao().loadAll();
        goodsMap = new HashMap<>();
        if (goodsBeanList != null && !goodsBeanList.isEmpty()) {
            for(GoodsBean bean : goodsBeanList) {
                goodsMap.put(bean.getGd_no(), bean);
            }
        }
    }

}
