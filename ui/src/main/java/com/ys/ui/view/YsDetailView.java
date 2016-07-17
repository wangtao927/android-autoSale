package com.ys.ui.view;

import android.text.TextUtils;

/**
 * Created by wangtao on 2016/7/17.
 */
public class YsDetailView {

    // 自己的ID
    private int id;

    // 级别
    private int level;

    // 中文描述
    private String desc;



    // 父ID
    private int parentId;

    // 子ID
    private String keywords;


    public YsDetailView(int level, String desc, int id, int parentId, String keywords) {
        this.level = level;
        this.desc = desc;
        this.id = id;
        this.parentId = parentId;
        if (TextUtils.isEmpty(keywords)) {
            this.keywords = desc;
        } else {
            this.keywords = keywords;
        }
    }

    public YsDetailView() {
    }

    public int getLevel() {

        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
