package com.ys.ui.common.constants;

import com.ys.ui.R;

/**
 * Created by wangtao on 2016/7/10.
 */
public enum RtbwEnum {

    TOU(11, "头"),
    JIN(12, "颈"),

    XIONG(21, "胸"),
    FU(22, "腹"),

    SIZHI(31, "四肢"),
    SZQ(32, "生殖器"),

    BEI(41, "背"),
    YAOTUN(42, "腰臀部"),

    XINZANG(51, "心脏"),
    FEI(52, "肺"),

    SHENGZANG(61, "肾脏"),
    DACHANG(62, "大肠"),

    XIAOCHANG(71, "小肠"),
    PANGGUANG(72, "膀胱"),

    PIZANG(81, "脾脏"),
    GANZANG(82, "肝脏"),

    DAN(91, "胆"),
    WEI(92, "胃"),


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

    public RtbwEnum findParamById(int id) {
        for (RtbwEnum param: RtbwEnum.values()) {
            if (param.getId() == id) {
                return param;
            }
        }
        return null;
    }
}
