package com.ys.ui.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 机器参数表
 * Created by wangtao on 2016/3/27.
 */
public class McParamsDo implements Serializable {

    @SerializedName("mp_id")
    private int id;
    @SerializedName("p_code")
    private String code;
    @SerializedName("mcp_pvalue")
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "McParamsDo{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
