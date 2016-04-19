package com.ys.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ys.ui.R;
import com.ys.ui.view.GoodsGridViewItem;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wangtao on 2016/4/19.
 */
public class GoodsGridViewAdapter extends BaseAdapter {

    private List<HashMap<String, GoodsGridViewItem>> list;
    private GoodsGridViewItem tempGridViewItem;
    private LayoutInflater layoutInflater;

    public GoodsGridViewAdapter(Context context,
                     List<HashMap<String, GoodsGridViewItem>> list) {

        this.list = list;
        layoutInflater = LayoutInflater.from(context);

    }

    /**
     * 数据总数
     */
    @Override
    public int getCount() {

        return list.size();
    }

    /**
     * 获取当前数据
     */
    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        if (layoutInflater != null) {

            view = layoutInflater
                    .inflate(R.layout.goods_item, null);
            ImageView imageView = (ImageView) view
                    .findViewById(R.id.image);
            TextView textView = (TextView) view.findViewById(R.id.text);
            //获取自定义的类实例
            //tempGridViewItem = list.get(position).get("");
//            imageView.setImageBitmap(tempGridViewItem.bitmap);
//            textView.setText(tempGridViewItem.gdName);

        }
        return view;
    }
}
