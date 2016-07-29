package com.ys.ui.common.constants;

/**
 * Created by wangtao on 2016/4/30.
 */
public enum SlSendStatusEnum {
    // 0 未上报 1 已上报  2 上报失败
    INIT(0, "未上报"),
    FINISH(1, "已上报"),
    FAIL(2, "上报失败"),
    ;



    SlSendStatusEnum(int index, String desc) {
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


    public static SlSendStatusEnum getSendStatusByIndex(int index) {
        for (SlSendStatusEnum statusEnum : SlSendStatusEnum.values()) {
            if (statusEnum.getIndex() == index) {
                return statusEnum;
            }
        }
        return INIT;
    }
}
