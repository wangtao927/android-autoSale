package com.ys.ui.common.constants;

/**
 * Created by wangtao on 2016/4/30.
 * 出货状态：1-待出货，2-出货成功，3-出货失败
 */
public enum SlOutStatusEnum {

    INIT(1, "待出货"),
    FINISH(2, "出货成功"),
    FAIL(3, "出货失败"),
    ;



    SlOutStatusEnum(int index, String desc) {
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


    public static SlOutStatusEnum getOutStatusByIndex(int index) {
        for (SlOutStatusEnum statusEnum : SlOutStatusEnum.values()) {
            if (statusEnum.getIndex() == index) {
                return statusEnum;
            }
        }
        return INIT;
    }
}
