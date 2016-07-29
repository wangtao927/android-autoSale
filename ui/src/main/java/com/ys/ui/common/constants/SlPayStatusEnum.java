package com.ys.ui.common.constants;

/**
 * Created by wangtao on 2016/4/30.
 * 支付状态：1-待支付，2-支付中，3-支付成功，4-支付失败，5-已退款，6-订单撤销
 */
public enum SlPayStatusEnum {


    INIT(1, "待支付"),
    PAYING(2, "支付中"),
    FINISH(3, "支付成功"),
    FAIL(4, "支付失败"),
    REFUNDED(5, "已退款"),
    CANCELD(6, "订单撤销"),
        ;


    SlPayStatusEnum(int index, String desc) {
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

    public static SlPayStatusEnum getPayStatusByIndex(int index) {
        for (SlPayStatusEnum payStatusEnum : SlPayStatusEnum.values()) {
            if (payStatusEnum.getIndex() == index) {
                return payStatusEnum;
            }
        }
        return SlPayStatusEnum.INIT;
    }
}
