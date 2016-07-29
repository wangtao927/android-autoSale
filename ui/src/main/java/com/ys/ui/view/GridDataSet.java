package com.ys.ui.view;


/**
 * Created by wangtao on 2016/7/27.
 */
public class GridDataSet {

    int id; // 跳转的activity   11  会员  其他的是药师

    int iamgeId;

    int subId; // 如果subId=1，跳转到自己 subId = 1 则要跳转到三级药师  subId = 2 跳转到会员

    String desc;


    public GridDataSet(int id, int iamgeId, int subId, String desc) {
        this.id = id;
        this.iamgeId = iamgeId;
        this.subId = subId;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIamgeId() {
        return iamgeId;
    }

    public void setIamgeId(int iamgeId) {
        this.iamgeId = iamgeId;
    }

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
