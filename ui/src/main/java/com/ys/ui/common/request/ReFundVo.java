package com.ys.ui.common.request;

import java.io.Serializable;

/**
 * Created by wangtao on 2016/5/8.
 */
public class ReFundVo implements Serializable {

    long time;

    String sign;

    String sl_no;


    public ReFundVo() {
    }

    public ReFundVo(long time, String sign, String sl_no) {
        this.time = time;
        this.sign = sign;
        this.sl_no = sl_no;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSl_no() {
        return sl_no;
    }

    public void setSl_no(String sl_no) {
        this.sl_no = sl_no;
    }
}
