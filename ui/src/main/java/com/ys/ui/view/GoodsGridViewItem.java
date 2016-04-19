package com.ys.ui.view;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by wangtao on 2016/4/19.
 */
public class GoodsGridViewItem implements Serializable {

    public String imageUrl;// 图片
    public String gdName;// 题标


    public GoodsGridViewItem() {
    }

    public GoodsGridViewItem(String imageUrl, String gdName) {
        this.imageUrl = imageUrl;
        this.gdName = gdName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGdName() {
        return gdName;
    }

    public void setGdName(String gdName) {
        this.gdName = gdName;
    }
}
