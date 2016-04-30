package com.ys.ui.common.constants;

/**
 * Created by wangtao on 2016/4/30.
 */
public enum ChanStatusEnum {
    NORMAL(1, "正常"),
    ERROR(2, "卡货"),

;

    ChanStatusEnum(int index, String desc) {
        this.index = index;
        this.desc = desc;
    }
    private int index;
    private String desc;
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
