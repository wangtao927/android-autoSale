package com.ys.data.bean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import java.io.Serializable;

/**
 * Entity mapped to table mcgoods.
 */
public class McGoodsBean implements Serializable {

    private String mc_no;
    private String mg_channo;
    private String gd_no;
    private String gd_type;
    private String gd_approve_code;
    private String gd_batch_no;
    private String gd_des_code;
    private String gd_mf_date;
    private String gd_exp_date;
    private Long mg_gvol;
    private Long mg_gnum= 0L;
    private Long mg_pre_price;
    private Long mg_score_price;
    private Long mg_vip_price;
    private Long mg_disc_price;
    private Long mg_price;
    private Long mg_chann_status = 1L;
    private java.util.Date addtime;
    private java.util.Date updatetime;

    public McGoodsBean() {
    }

    public McGoodsBean(String mg_channo) {
        this.mg_channo = mg_channo;
    }


    public McGoodsBean(String mc_no, String mg_channo, String gd_no, String gd_type, String gd_approve_code, String gd_batch_no, String gd_des_code, String gd_mf_date, String gd_exp_date, Long mg_gvol, Long mg_gnum, Long mg_pre_price, Long mg_score_price, Long mg_vip_price, Long mg_disc_price, Long mg_price, Long mg_chann_status, java.util.Date addtime, java.util.Date updatetime) {
        this.mc_no = mc_no;
        this.mg_channo = mg_channo;
        this.gd_no = gd_no;
        this.gd_type = gd_type;
        this.gd_approve_code = gd_approve_code;
        this.gd_batch_no = gd_batch_no;
        this.gd_des_code = gd_des_code;
        this.gd_mf_date = gd_mf_date;
        this.gd_exp_date = gd_exp_date;
        this.mg_gvol = mg_gvol;
        this.mg_gnum = mg_gnum;
        this.mg_pre_price = mg_pre_price;
        this.mg_score_price = mg_score_price;
        this.mg_vip_price = mg_vip_price;
        this.mg_disc_price = mg_disc_price;
        this.mg_price = mg_price;
        this.mg_chann_status = mg_chann_status;
        this.addtime = addtime;
        this.updatetime = updatetime;
    }

    public String getMc_no() {
        return mc_no;
    }

    public void setMc_no(String mc_no) {
        this.mc_no = mc_no;
    }

    public String getMg_channo() {
        return mg_channo;
    }

    public void setMg_channo(String mg_channo) {
        this.mg_channo = mg_channo;
    }

    public String getGd_no() {
        return gd_no;
    }

    public void setGd_no(String gd_no) {
        this.gd_no = gd_no;
    }

    public String getGd_type() {
        return gd_type;
    }

    public void setGd_type(String gd_type) {
        this.gd_type = gd_type;
    }

    public String getGd_approve_code() {
        return gd_approve_code;
    }

    public void setGd_approve_code(String gd_approve_code) {
        this.gd_approve_code = gd_approve_code;
    }

    public String getGd_batch_no() {
        return gd_batch_no;
    }

    public void setGd_batch_no(String gd_batch_no) {
        this.gd_batch_no = gd_batch_no;
    }

    public String getGd_des_code() {
        return gd_des_code;
    }

    public void setGd_des_code(String gd_des_code) {
        this.gd_des_code = gd_des_code;
    }

    public String getGd_mf_date() {
        return gd_mf_date;
    }

    public void setGd_mf_date(String gd_mf_date) {
        this.gd_mf_date = gd_mf_date;
    }

    public String getGd_exp_date() {
        return gd_exp_date;
    }

    public void setGd_exp_date(String gd_exp_date) {
        this.gd_exp_date = gd_exp_date;
    }

    public Long getMg_gvol() {
        return mg_gvol;
    }

    public void setMg_gvol(Long mg_gvol) {
        this.mg_gvol = mg_gvol;
    }

    public Long getMg_gnum() {
        return mg_gnum;
    }

    public void setMg_gnum(Long mg_gnum) {
        this.mg_gnum = mg_gnum;
    }

    public Long getMg_pre_price() {
        return mg_pre_price;
    }

    public void setMg_pre_price(Long mg_pre_price) {
        this.mg_pre_price = mg_pre_price;
    }

    public Long getMg_score_price() {
        return mg_score_price;
    }

    public void setMg_score_price(Long mg_score_price) {
        this.mg_score_price = mg_score_price;
    }

    public Long getMg_vip_price() {
        return mg_vip_price;
    }

    public void setMg_vip_price(Long mg_vip_price) {
        this.mg_vip_price = mg_vip_price;
    }

    public Long getMg_disc_price() {
        return mg_disc_price;
    }

    public void setMg_disc_price(Long mg_disc_price) {
        this.mg_disc_price = mg_disc_price;
    }

    public Long getMg_price() {
        return mg_price;
    }

    public void setMg_price(Long mg_price) {
        this.mg_price = mg_price;
    }

    public Long getMg_chann_status() {
        return mg_chann_status;
    }

    public void setMg_chann_status(Long mg_chann_status) {
        this.mg_chann_status = mg_chann_status;
    }

    public java.util.Date getAddtime() {
        return addtime;
    }

    public void setAddtime(java.util.Date addtime) {
        this.addtime = addtime;
    }

    public java.util.Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(java.util.Date updatetime) {
        this.updatetime = updatetime;
    }

}
