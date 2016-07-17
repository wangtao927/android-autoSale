package com.ys.ui.common.constants;

/**
 * Created by wangtao on 2016/7/10.
 */
public enum GngxEnum {

    ZIBUYANGSHENG(11, "滋补养生"),
    YDFS(12, "运动风湿"),
    YYBJ(13, "营养保健"),
    YLQX(14, "医疗器械"),



    WGYY(21, "五官"),
    QQSH(22, "情趣生活"),
    CWYY(23, "肠胃"),
    GMYY(24, "感冒"),

    HXXT(31, "呼吸"),
    MZHL(32, "美妆护理"),
    QRJD(33, "清热解毒"),
    PFYY(34, "皮肤"),


    ;

    GngxEnum(int id, String desc) {
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

    public static GngxEnum findParamById(int id) {
        for (GngxEnum param: GngxEnum.values()) {
            if (param.getId() == id) {
                return param;
            }
        }
        return null;
    }
}
