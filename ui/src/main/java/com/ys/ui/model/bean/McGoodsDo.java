package com.ys.ui.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * 库存表
 * Created by wangtao on 2016/3/27.
 */
public class McGoodsDo implements Serializable {

    /**
     *
     */
    @SerializedName("mg_id")
    private String id;
    /**
     *
     */
    @SerializedName("mc_no")
    private String no;
    /**
     *货道编号 四位货道编码 0011
     */
    @SerializedName("mg_channo")
    private String channo;

    /**
     * 商品ID
     */
    @SerializedName("gd_id")
    private int goodsId;
    /**
     * 商品编码
     */
    @SerializedName("gd_no")
    private String goodsNo;

    /**
     *  商品类型
     */
    @SerializedName("gd_type")
    private String goodsType;
    /**
     *准字号
     */
    @SerializedName("gd_approve_code")
    private String approveCode;
    /**
     *批号
     */
    @SerializedName("gd_batch_no")
    private String batchNo;
    /**
     *电子监管码
     */
    @SerializedName("gd_des_code")
    private String desCode;
    /**
     *生产日期
     */
    @SerializedName("gd_mf_date")
    private Date mfDate;
    /**
     *有效日期
     */
    @SerializedName("gd_exp_date")
    private Date expDate;
    /**
     *货道容量
     */
    @SerializedName("mg_gvol")
    private int gvol;
    /**
     *商品存量
     */
    @SerializedName("mg_gnum")
    private int gnum;
    /**
     *  原价
     */
    @SerializedName("mg_pre_price")
    private int prePrice;
    /**
     *积分价
     */
    @SerializedName("mg_score_price")
    private int scorePrice;
    /**
     *会员价
     */
    @SerializedName("mg_vip_price")
    private int vipPrice;
    /**
     *销售单价
     */
    @SerializedName("mg_price")
    private int price;
    /**
     *货道状态 0 正常  1 卡货
     */
    @SerializedName("mg_chan_status")
    private int chanStatus;

    @SerializedName("addtime")
    private Date addTime;
    @SerializedName("updatetime")
    private Date updateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getChanno() {
        return channo;
    }

    public void setChanno(String channo) {
        this.channo = channo;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getApproveCode() {
        return approveCode;
    }

    public void setApproveCode(String approveCode) {
        this.approveCode = approveCode;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getDesCode() {
        return desCode;
    }

    public void setDesCode(String desCode) {
        this.desCode = desCode;
    }

    public Date getMfDate() {
        return mfDate;
    }

    public void setMfDate(Date mfDate) {
        this.mfDate = mfDate;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public int getGvol() {
        return gvol;
    }

    public void setGvol(int gvol) {
        this.gvol = gvol;
    }

    public int getGnum() {
        return gnum;
    }

    public void setGnum(int gnum) {
        this.gnum = gnum;
    }

    public int getPrePrice() {
        return prePrice;
    }

    public void setPrePrice(int prePrice) {
        this.prePrice = prePrice;
    }

    public int getScorePrice() {
        return scorePrice;
    }

    public void setScorePrice(int scorePrice) {
        this.scorePrice = scorePrice;
    }

    public int getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(int vipPrice) {
        this.vipPrice = vipPrice;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getChanStatus() {
        return chanStatus;
    }

    public void setChanStatus(int chanStatus) {
        this.chanStatus = chanStatus;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "McGoodsDo{" +
                "id='" + id + '\'' +
                ", no='" + no + '\'' +
                ", channo='" + channo + '\'' +
                ", goodsId=" + goodsId +
                ", goodsNo='" + goodsNo + '\'' +
                ", goodsType='" + goodsType + '\'' +
                ", approveCode='" + approveCode + '\'' +
                ", batchNo='" + batchNo + '\'' +
                ", desCode='" + desCode + '\'' +
                ", mfDate=" + mfDate +
                ", expDate=" + expDate +
                ", gvol=" + gvol +
                ", gnum=" + gnum +
                ", prePrice=" + prePrice +
                ", scorePrice=" + scorePrice +
                ", vipPrice=" + vipPrice +
                ", price=" + price +
                ", chanStatus=" + chanStatus +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
