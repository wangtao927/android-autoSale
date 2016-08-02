package com.ys.ui.common.constants;

/**
 * Created by wangtao on 2016/4/17.
 */
public enum SlTypeEnum {

    //付款方式：1-现金、2-银行卡、3-微信支付，4-支付宝，5-积分兑换，6-提货码

    CASH(1, "现金"),
    CARD(2, "银行卡"),
    WX(3, "微信支付"),
    ALIPAY(4, "支付宝"),
    SCORE(5, "积分兑换"),
    CODE(6, "提货码"),
    VIPFREE(7, "会员尊享"),

    ;


    SlTypeEnum(int index, String desc) {
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

    public static SlTypeEnum findByIndex(int index) {

        for (SlTypeEnum slTypeEnum : SlTypeEnum.values()) {
            if (slTypeEnum.getIndex() == index) {
                return slTypeEnum;
            }
        }
        return null;
    }
}
