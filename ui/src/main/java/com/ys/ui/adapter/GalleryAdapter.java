package com.ys.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ys.ui.R;

public class GalleryAdapter extends BaseAdapter {

    Context context;

    List<Object> list;

    public GalleryAdapter(Context context) {
        this.context = context;
        list = new ArrayList<Object>();
    }

    public void setList(List<Object> list) {
        this.list = list;
        this.notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.gallery_item, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.gallery_iv = (ImageView) convertView.findViewById(R.id.gallery_iv);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.gallery_iv.setImageResource(Integer.parseInt(list.get(position).toString()));
        return convertView;
    }

    class ViewHolder {
        ImageView gallery_iv;
    }

}
