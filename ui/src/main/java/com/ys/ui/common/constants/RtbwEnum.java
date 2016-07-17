package com.ys.ui.common.constants;

import com.ys.ui.R;

/**
 * Created by wangtao on 2016/7/10.
 */
public enum RtbwEnum {

    TOU(111, "头"),
    JIN(112, "颈"),

    XIONG(121, "胸"),
    FU(122, "腹"),

    SIZHI(131, "四肢"),
    SZQ(132, "生殖器"),

    BEI(141, "背"),
    YAOTUN(142, "腰臀部"),

    XINZANG(151, "心脏"),
    FEI(152, "肺"),

    SHENGZANG(161, "肾脏"),
    DACHANG(162, "大肠"),

    XIAOCHANG(171, "小肠"),
    PANGGUANG(172, "膀胱"),

    PIZANG(181, "脾脏"),
    GANZANG(182, "肝脏"),

    DAN(191, "胆"),
    WEI(192, "胃"),


    ;

    RtbwEnum(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    int id;

    String desc;


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

    public static RtbwEnum findParamById(int id) {
        for (RtbwEnum param: RtbwEnum.values()) {
            if (param.getId() == id) {
                return param;
            }
        }
        return null;
    }
}
