package com.ys;

/**
 * Created by wangtao on 2016/3/13.
 */
public class RobotEventArg {

    private int iCode; // 0보드, 1코인/지폐, 2RACK
    private int iMsgCode;
    private String paramObject;

    private int[] iMoney = { 0, 0, 0 };

    public RobotEventArg(int iCode, int iMsgCode, String paramObject) {
        this.iCode = iCode;
        this.iMsgCode = iMsgCode;
        this.paramObject = paramObject;
    }

    public RobotEventArg(int iCode, int iMsgCode, int[] iMoney) {
        this.iCode = iCode;
        this.iMsgCode = iMsgCode;
        this.iMoney = iMoney;
    }


    public int getiCode() {
        return iCode;
    }

    public void setiCode(int iCode) {
        this.iCode = iCode;
    }

    public int getiMsgCode() {
        return iMsgCode;
    }

    public void setiMsgCode(int iMsgCode) {
        this.iMsgCode = iMsgCode;
    }

    public String getParamObject() {
        return paramObject;
    }

    public void setParamObject(String paramObject) {
        this.paramObject = paramObject;
    }

    public int[] getiMoney() {
        return iMoney;
    }

    public void setiMoney(int[] iMoney) {
        this.iMoney = iMoney;
    }
}
