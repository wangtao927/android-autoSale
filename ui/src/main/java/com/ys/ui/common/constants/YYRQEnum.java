package com.ys.ui.common.constants;

/**
 * Created by wangtao on 2016/7/10.
 */
public enum YYRQEnum {

    LR(201, "老人用药"),
    ET(202, "儿童用药"),
    NR(203, "男性用药"),
    NX(204, "女性用药"),



    ;

    YYRQEnum(int id, String desc) {
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

    public YYRQEnum findParamById(int id) {
        for (YYRQEnum param: YYRQEnum.values()) {
            if (param.getId() == id) {
                return param;
            }
        }
        return null;
    }
}
