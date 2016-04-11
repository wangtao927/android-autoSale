package com.ys.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ys.data.bean.McGoodsBean;
import com.ys.ui.R;
import com.ys.ui.view.McGoodsView;

import java.util.List;

/**
 * Created by wangtao on 2016/4/10.
 */
public class McGoodsListAdapter extends ArrayAdapter<McGoodsBean> {

    private int resouceId;

    public McGoodsListAdapter(Context context, int textViewResourceId, List<McGoodsBean> objects) {
        super(context, textViewResourceId, objects);
        this.resouceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parant) {

        McGoodsBean goodsView = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resouceId, null);
        } else {
            view = convertView;
        }
        TextView channo = (TextView)view.findViewById(R.id.channo);
        TextView gdNo = (TextView)view.findViewById(R.id.gdNo);
        TextView gvol = (TextView)view.findViewById(R.id.gvol);
        TextView gnum = (TextView)view.findViewById(R.id.gnum);
        TextView chanStatus = (TextView)view.findViewById(R.id.chanStatus);
        channo.setText(goodsView.getMg_channo());
        gdNo.setText(goodsView.getGd_no());
        gvol.setText(goodsView.getMg_gvol().toString());
        gnum.setText(goodsView.getMg_gnum().toString());
        chanStatus.setText(goodsView.getChanStatus().toString());


        return view;

    }

}
