package com.ys.ui.common.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by wangtao on 2016/4/17.
 */
public class CreateOrderResult implements Serializable {

    @SerializedName("sl_no")
    private String slNo;

    private String qrcode;

    @SerializedName("qrcode_url")
    private String qrcodeUrl;


    /** 提货码参数 , 提货码的不需要再查下状态*/
    @SerializedName("sl_status")
    private String slStatus;

    @SerializedName("sl_gd_no")
    private String slGdNo;

    @SerializedName("sl_gd_name")
    private String slGdName;

    public String getSlNo() {
        return slNo;
    }

    public void setSlNo(String slNo) {
        this.slNo = slNo;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public String getSlStatus() {
        return slStatus;
    }

    public void setSlStatus(String slStatus) {
        this.slStatus = slStatus;
    }

    public String getSlGdNo() {
        return slGdNo;
    }

    public void setSlGdNo(String slGdNo) {
        this.slGdNo = slGdNo;
    }

    public String getSlGdName() {
        return slGdName;
    }

    public void setSlGdName(String slGdName) {
        this.slGdName = slGdName;
    }

    @Override
    public String toString() {
        return "CreateOrderResult{" +
                "slNo='" + slNo + '\'' +
                ", qrcode='" + qrcode + '\'' +
                ", qrcodeUrl='" + qrcodeUrl + '\'' +
                ", slStatus='" + slStatus + '\'' +
                ", slGdNo='" + slGdNo + '\'' +
                ", slGdName='" + slGdName + '\'' +
                '}';
    }
}
