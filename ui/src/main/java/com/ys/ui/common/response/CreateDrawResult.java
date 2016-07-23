package com.ys.ui.common.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by wangtao on 2016/7/20.
 */
public class CreateDrawResult implements Serializable {

    @SerializedName("dw_time")
    private String dwTime;
    @SerializedName("sl_no")
    private String slNo;
    @SerializedName("acc_no")
    private String accNo;
    @SerializedName("dp_id")
    private String dpId;
    @SerializedName("dw_result")
    private String dwResult;
    @SerializedName("dw_level")
    private int dwLevel;
    @SerializedName("add_time")
    private String addTime;

    public String getDwTime() {
        return dwTime;
    }

    public void setDwTime(String dwTime) {
        this.dwTime = dwTime;
    }

    public String getSlNo() {
        return slNo;
    }

    public void setSlNo(String slNo) {
        this.slNo = slNo;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getDpId() {
        return dpId;
    }

    public void setDpId(String dpId) {
        this.dpId = dpId;
    }

    public String getDwResult() {
        return dwResult;
    }

    public void setDwResult(String dwResult) {
        this.dwResult = dwResult;
    }

    public int getDwLevel() {
        return dwLevel;
    }

    public void setDwLevel(int dwLevel) {
        this.dwLevel = dwLevel;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "CreateDrawResult{" +
                "dwTime='" + dwTime + '\'' +
                ", slNo='" + slNo + '\'' +
                ", accNo='" + accNo + '\'' +
                ", dpId='" + dpId + '\'' +
                ", dwResult='" + dwResult + '\'' +
                ", dwLevel=" + dwLevel +
                ", addTime='" + addTime + '\'' +
                '}';
    }
}
