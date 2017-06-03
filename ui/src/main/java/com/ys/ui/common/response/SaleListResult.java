package com.ys.ui.common.response;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangtao on 2016/4/17.
 */
public class SaleListResult implements Serializable {

    private String sl_no;
    private String sl_batch_no;

    private String mc_no;
    private String sl_gd_no;
    private String sl_gd_name;
    //private String sl_status;
    private String sl_pay_status;
    private String sl_out_status;

    public String getSl_no() {
        return sl_no;
    }

    public void setSl_no(String sl_no) {
        this.sl_no = sl_no;
    }

    public String getSl_batch_no() {
        return sl_batch_no;
    }

    public void setSl_batch_no(String sl_batch_no) {
        this.sl_batch_no = sl_batch_no;
    }


    public String getMc_no() {
        return mc_no;
    }

    public void setMc_no(String mc_no) {
        this.mc_no = mc_no;
    }

    public String getSl_gd_no() {
        return sl_gd_no;
    }

    public void setSl_gd_no(String sl_gd_no) {
        this.sl_gd_no = sl_gd_no;
    }

    public String getSl_gd_name() {
        return sl_gd_name;
    }

    public void setSl_gd_name(String sl_gd_name) {
        this.sl_gd_name = sl_gd_name;
    }


    public String getSl_pay_status() {
        return sl_pay_status;
    }

    public void setSl_pay_status(String sl_pay_status) {
        this.sl_pay_status = sl_pay_status;
    }

    public String getSl_out_status() {
        return sl_out_status;
    }

    public void setSl_out_status(String sl_out_status) {
        this.sl_out_status = sl_out_status;
    }

    @Override
    public String toString() {
        return "SaleListResult{" +
                "sl_no='" + sl_no + '\'' +
                ", sl_batch_no='" + sl_batch_no + '\'' +
                ", mc_no='" + mc_no + '\'' +
                ", sl_gd_no='" + sl_gd_no + '\'' +
                ", sl_gd_name='" + sl_gd_name + '\'' +
                ", sl_pay_status='" + sl_pay_status + '\'' +
                ", sl_out_status='" + sl_out_status + '\'' +
                '}';
    }
}
