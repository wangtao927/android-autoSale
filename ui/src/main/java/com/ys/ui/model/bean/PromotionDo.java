package com.ys.ui.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangtao on 2016/3/27.
 */
public class PromotionDo implements Serializable {

    @SerializedName("pt_id")
    private int id;
    @SerializedName("pt_name")
    private String name;
    @SerializedName("pt_msg")
    private String msg;
    @SerializedName("pt_desc")
    private String desc;
    @SerializedName("pt_status")
    private String status;
    @SerializedName("pt_starttime")
    private Date startTime;
    @SerializedName("pt_endtime")
    private Date endTime;
    @SerializedName("pt_range")
    private String range;
    @SerializedName("pt_rangeparam")
    private String rangeParam;
    @SerializedName("pt_goods")
    private String goods;
    @SerializedName("pt_goodsparam")
    private String goodsParam;
    @SerializedName("pt_condition")
    private String condition;
    @SerializedName("pt_conditionparam")
    private String conditionParam;
    @SerializedName("pt_type")
    private String type;
    @SerializedName("pt_typeparam")
    private String typeParam;
    @SerializedName("remark")
    private String remark;
    @SerializedName("addtime")
    private Date addTime;
    @SerializedName("updatetime")
    private String updateTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getRangeParam() {
        return rangeParam;
    }

    public void setRangeParam(String rangeParam) {
        this.rangeParam = rangeParam;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getGoodsParam() {
        return goodsParam;
    }

    public void setGoodsParam(String goodsParam) {
        this.goodsParam = goodsParam;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getConditionParam() {
        return conditionParam;
    }

    public void setConditionParam(String conditionParam) {
        this.conditionParam = conditionParam;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeParam() {
        return typeParam;
    }

    public void setTypeParam(String typeParam) {
        this.typeParam = typeParam;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "PromotionDo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", msg='" + msg + '\'' +
                ", desc='" + desc + '\'' +
                ", status='" + status + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", range='" + range + '\'' +
                ", rangeParam='" + rangeParam + '\'' +
                ", goods='" + goods + '\'' +
                ", goodsParam='" + goodsParam + '\'' +
                ", condition='" + condition + '\'' +
                ", conditionParam='" + conditionParam + '\'' +
                ", type='" + type + '\'' +
                ", typeParam='" + typeParam + '\'' +
                ", remark='" + remark + '\'' +
                ", addTime=" + addTime +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
