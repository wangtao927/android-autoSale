package com.ys.ui.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * 机器状态表
 * Created by wangtao on 2016/3/27.
 */
public class McStatusDo implements Serializable {

    /**
     *
     */
    @SerializedName("mr_id")
    private int id;
    /**
     *终端号
     */
    @SerializedName("mc_no")
    private String no;
    /**
     *'硬币器状态：''0''-正常，''1''-异常。'
     */
    @SerializedName("mr_coin_status")
    private String coinStatus;
    /**
     *'硬币预警',   硬币余额低于5元
     */
    @SerializedName("mr_coin_short")
    private String coinShort;
    /**
     *纸币器状态：''0''-正常，''1''-异常。',
     */
    @SerializedName("mr_bill_status")
    private String billStatus;
    /**
     *纸币预警
     */
    @SerializedName("mr_bill_short")
    private String billShort;
    /**
     *银联POS状态：''0''-正常，''1''-异常。
     */
    @SerializedName("mr_uppos_status")
    private String upposStatus;
    /**
     *校园POS状态：''0''-正常，''1''-异常。
     */
    @SerializedName("mr_scpos_status")
    private String scposStatus;
    /**
     *网络连接状态：''0''-正常，''1''-异常。
     */
    @SerializedName("mr_net_status")
    private String netStatus;
    /**
     *机器温度
     */
    @SerializedName("mr_temp")
    private String temp;
    /**
     *
     */
    @SerializedName("mr_door_isfault")
    private String doorIsDefault;
    /**
     *'门状态：''1''-关，''0''-开
     */
    @SerializedName("mr_door_status")
    private String doorStatus;
    /**
     *故障货道数
     */
    @SerializedName("mr_chann_fault_num")
    private int channFaultNum;
    /**
     *故障货道编号，以''|''分割。',
     */
    @SerializedName("mr_chann_fault_nos")
    private String channFaultNos;
    /**
     * 缺货货道数量
     */
    @SerializedName("mr_nogd_chnum")
    private int nogdChnum;
    /**
     *缺货货道
     */
    @SerializedName("mr_nogd_chann")
    private String nogdChann;
    /**
     *齿轮盒货道故障数量
     */
    @SerializedName("mr_gear_fault_num")
    private int gearFaultNum;
    /**
     *齿轮盒货道
     */
    @SerializedName("mr_gear_fault_nos")
    private String gearFaultNos;
    /**
     *
     */
    @SerializedName("mr_data_fault")
    private int dataFault;
    /**
     *
     */
    @SerializedName("mr_door_date")
    private Date doorDate;
    /**
     *
     */
    @SerializedName("addtime")
    private Date addTime;
    /**
     *
     */
    @SerializedName("updatetime")
    private Date updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getCoinStatus() {
        return coinStatus;
    }

    public void setCoinStatus(String coinStatus) {
        this.coinStatus = coinStatus;
    }

    public String getCoinShort() {
        return coinShort;
    }

    public void setCoinShort(String coinShort) {
        this.coinShort = coinShort;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public String getBillShort() {
        return billShort;
    }

    public void setBillShort(String billShort) {
        this.billShort = billShort;
    }

    public String getUpposStatus() {
        return upposStatus;
    }

    public void setUpposStatus(String upposStatus) {
        this.upposStatus = upposStatus;
    }

    public String getScposStatus() {
        return scposStatus;
    }

    public void setScposStatus(String scposStatus) {
        this.scposStatus = scposStatus;
    }

    public String getNetStatus() {
        return netStatus;
    }

    public void setNetStatus(String netStatus) {
        this.netStatus = netStatus;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getDoorIsDefault() {
        return doorIsDefault;
    }

    public void setDoorIsDefault(String doorIsDefault) {
        this.doorIsDefault = doorIsDefault;
    }

    public String getDoorStatus() {
        return doorStatus;
    }

    public void setDoorStatus(String doorStatus) {
        this.doorStatus = doorStatus;
    }

    public int getChannFaultNum() {
        return channFaultNum;
    }

    public void setChannFaultNum(int channFaultNum) {
        this.channFaultNum = channFaultNum;
    }

    public String getChannFaultNos() {
        return channFaultNos;
    }

    public void setChannFaultNos(String channFaultNos) {
        this.channFaultNos = channFaultNos;
    }

    public int getNogdChnum() {
        return nogdChnum;
    }

    public void setNogdChnum(int nogdChnum) {
        this.nogdChnum = nogdChnum;
    }

    public String getNogdChann() {
        return nogdChann;
    }

    public void setNogdChann(String nogdChann) {
        this.nogdChann = nogdChann;
    }

    public int getGearFaultNum() {
        return gearFaultNum;
    }

    public void setGearFaultNum(int gearFaultNum) {
        this.gearFaultNum = gearFaultNum;
    }

    public String getGearFaultNos() {
        return gearFaultNos;
    }

    public void setGearFaultNos(String gearFaultNos) {
        this.gearFaultNos = gearFaultNos;
    }

    public int getDataFault() {
        return dataFault;
    }

    public void setDataFault(int dataFault) {
        this.dataFault = dataFault;
    }

    public Date getDoorDate() {
        return doorDate;
    }

    public void setDoorDate(Date doorDate) {
        this.doorDate = doorDate;
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
        return "McStatusDo{" +
                "id=" + id +
                ", no='" + no + '\'' +
                ", coinStatus='" + coinStatus + '\'' +
                ", coinShort='" + coinShort + '\'' +
                ", billStatus='" + billStatus + '\'' +
                ", billShort='" + billShort + '\'' +
                ", upposStatus='" + upposStatus + '\'' +
                ", scposStatus='" + scposStatus + '\'' +
                ", netStatus='" + netStatus + '\'' +
                ", temp='" + temp + '\'' +
                ", doorIsDefault='" + doorIsDefault + '\'' +
                ", doorStatus='" + doorStatus + '\'' +
                ", channFaultNum=" + channFaultNum +
                ", channFaultNos='" + channFaultNos + '\'' +
                ", nogdChnum=" + nogdChnum +
                ", nogdChann='" + nogdChann + '\'' +
                ", gearFaultNum=" + gearFaultNum +
                ", gearFaultNos='" + gearFaultNos + '\'' +
                ", dataFault=" + dataFault +
                ", doorDate=" + doorDate +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
