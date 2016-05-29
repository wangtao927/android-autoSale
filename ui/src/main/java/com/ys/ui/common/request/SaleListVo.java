package com.ys.ui.common.request;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by wangtao on 2016/4/16.
 */
public class SaleListVo implements Serializable {

    /**
     * sl_no,sl_type,sl_gd_name,sl_gd_no,sl_amt,mc_no,sl_score,sl_isvip,sl_acc_no
     sl_th_cardno,sl_th_pwd，
     */
    @SerializedName("sl_no")
    private String slNo;

    @SerializedName("sl_type")
    private String slType;

    @SerializedName("sl_gd_name")
    private String slGdName;

    @SerializedName("sl_gd_no")
    private String slGdNo;

    @SerializedName("sl_chann")
    private String slChann;
    @SerializedName("sl_amt")
    private Long slAmt;
    @SerializedName("mc_no")
    private String mcNo;
    @SerializedName("sl_score")
    private long slScore;
    @SerializedName("sl_isvip")
    private String slIsvip;
    @SerializedName("sl_acc_no")
    private String slAccNo;
    @SerializedName("ec_no")
    private String slThCardno;
    @SerializedName("ec_pwd")
    private String slThPwd;

    public String getSlChann() {
        return slChann;
    }

    public void setSlChann(String slChann) {
        this.slChann = slChann;
    }

    public String getSlNo() {
        return slNo;
    }

    public void setSlNo(String slNo) {
        this.slNo = slNo;
    }

    public String getSlType() {
        return slType;
    }

    public void setSlType(String slType) {
        this.slType = slType;
    }

    public String getSlGdName() {
        return slGdName;
    }

    public void setSlGdName(String slGdName) {
        this.slGdName = slGdName;
    }

    public String getSlGdNo() {
        return slGdNo;
    }

    public void setSlGdNo(String slGdNo) {
        this.slGdNo = slGdNo;
    }

    public Long getSlAmt() {
        return slAmt;
    }

    public void setSlAmt(Long slAmt) {
        this.slAmt = slAmt;
    }

    public String getMcNo() {
        return mcNo;
    }

    public void setMcNo(String mcNo) {
        this.mcNo = mcNo;
    }

    public long getSlScore() {
        return slScore;
    }

    public void setSlScore(long slScore) {
        this.slScore = slScore;
    }

    public String getSlIsvip() {
        return slIsvip;
    }

    public void setSlIsvip(String slIsvip) {
        this.slIsvip = slIsvip;
    }

    public String getSlAccNo() {
        return slAccNo;
    }

    public void setSlAccNo(String slAccNo) {
        this.slAccNo = slAccNo;
    }

    public String getSlThCardno() {
        return slThCardno;
    }

    public void setSlThCardno(String slThCardno) {
        this.slThCardno = slThCardno;
    }

    public String getSlThPwd() {
        return slThPwd;
    }

    public void setSlThPwd(String slThPwd) {
        this.slThPwd = slThPwd;
    }

    @Override
    public String toString() {
        return "SaleListVo{" +
                "slNo='" + slNo + '\'' +
                ", slType='" + slType + '\'' +
                ", slGdName='" + slGdName + '\'' +
                ", slGdNo='" + slGdNo + '\'' +
                ", slChann='" + slChann + '\'' +
                ", slAmt=" + slAmt +
                ", mcNo='" + mcNo + '\'' +
                ", slScore=" + slScore +
                ", slIsvip='" + slIsvip + '\'' +
                ", slAccNo='" + slAccNo + '\'' +
                ", slThCardno='" + slThCardno + '\'' +
                ", slThPwd='" + slThPwd + '\'' +
                '}';
    }
}
