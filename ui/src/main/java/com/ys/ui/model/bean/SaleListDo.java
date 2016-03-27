package com.ys.ui.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangtao on 2016/3/27.
 */
public class SaleListDo implements Serializable {

    @SerializedName("sl_id")
    private int id;
    @SerializedName("sl_no")
    private String orderSn;
    @SerializedName("sl_batch_no")
    private String batchNo;
    @SerializedName("sl_time")
    private Date time;
    @SerializedName("mc_no")
    private String mcNo;
    @SerializedName("sl_gd_no")
    private String gdNo;
    @SerializedName("sl_pre_price")
    private Long prePrice;
    @SerializedName("sl_amt")
    private Long amt;
    @SerializedName("sl_score")
    private Long score;
    @SerializedName("sl_cash_in")
    private Long cashIn;
    @SerializedName("sl_cash_out")
    private Long cashOut;
    @SerializedName("sl_coin_in")
    private Long coinIn;
    @SerializedName("sl_coin_out")
    private Long coinOut;

    @SerializedName("sl_chann")
    private String chann;
    @SerializedName("sl_num")
    private int num;
    @SerializedName("sl_type")
    private String type;
    @SerializedName("sl_isvip")
    private String isVip;
    @SerializedName("sl_status")
    private String status;
    @SerializedName("sl_err_msg")
    private String errMsg;
    @SerializedName("sl_acc_no")
    private String accNo;
    @SerializedName("sl_bf_amt")
    private Long bfAmt;
    @SerializedName("sl_af_amt")
    private Long afAmt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMcNo() {
        return mcNo;
    }

    public void setMcNo(String mcNo) {
        this.mcNo = mcNo;
    }

    public String getGdNo() {
        return gdNo;
    }

    public void setGdNo(String gdNo) {
        this.gdNo = gdNo;
    }

    public Long getPrePrice() {
        return prePrice;
    }

    public void setPrePrice(Long prePrice) {
        this.prePrice = prePrice;
    }

    public Long getAmt() {
        return amt;
    }

    public void setAmt(Long amt) {
        this.amt = amt;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getCashIn() {
        return cashIn;
    }

    public void setCashIn(Long cashIn) {
        this.cashIn = cashIn;
    }

    public Long getCashOut() {
        return cashOut;
    }

    public void setCashOut(Long cashOut) {
        this.cashOut = cashOut;
    }

    public Long getCoinIn() {
        return coinIn;
    }

    public void setCoinIn(Long coinIn) {
        this.coinIn = coinIn;
    }

    public Long getCoinOut() {
        return coinOut;
    }

    public void setCoinOut(Long coinOut) {
        this.coinOut = coinOut;
    }

    public String getChann() {
        return chann;
    }

    public void setChann(String chann) {
        this.chann = chann;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsVip() {
        return isVip;
    }

    public void setIsVip(String isVip) {
        this.isVip = isVip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public Long getBfAmt() {
        return bfAmt;
    }

    public void setBfAmt(Long bfAmt) {
        this.bfAmt = bfAmt;
    }

    public Long getAfAmt() {
        return afAmt;
    }

    public void setAfAmt(Long afAmt) {
        this.afAmt = afAmt;
    }
}
