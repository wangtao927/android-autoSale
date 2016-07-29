package com.ys.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ys.data.bean.McGoodsBean;
import com.ys.ui.R;
import com.ys.ui.view.GridDataSet;

import java.util.List;

/**
 * Created by wangtao on 2016/4/19.
 */
public class GridViewAdapter extends BaseAdapter {

    private List<GridDataSet> list;
    private LayoutInflater layoutInflater;

    public GridViewAdapter(Context context,
                           List<GridDataSet> list) {

        this.list = list;
        layoutInflater = LayoutInflater.from(context);

    }

    public void setData(List<GridDataSet> data) {
        this.list = data;
        notifyDataSetChanged();
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

            view = layoutInflater.inflate(R.layout.gridview_item, null);
            ImageView imageView = (ImageView) view
                    .findViewById(R.id.iv_gridview);
            imageView.setImageResource(list.get(position).getIamgeId());
            //获取自定义的类实例

        }
        return view;
    }
}
