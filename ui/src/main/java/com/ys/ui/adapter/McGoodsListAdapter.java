package com.ys.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ys.ui.view.McGoodsView;

import java.util.List;

/**
 * Created by wangtao on 2016/4/10.
 */
public class McGoodsListAdapter extends ArrayAdapter<McGoodsView> {

    private int resouceId;

    public McGoodsListAdapter(Context context, int textViewResourceId, List<McGoodsView> objects) {
        super(context, textViewResourceId, objects);
        this.resouceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parant) {

        McGoodsView goodsView = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resouceId, null);

        return view;

    }

}
