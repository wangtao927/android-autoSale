package com.ys.ui.view;

/**
 * Created by wangtao on 2016/4/10.
 */
public class McGoodsView {

    private String mc_no;

    private String gd_no;

    private int mg_gvol;

    private int mg_gnum;

    private int chanStatus;

    public String getMc_no() {
        return mc_no;
    }

    public void setMc_no(String mc_no) {
        this.mc_no = mc_no;
    }

    public String getGd_no() {
        return gd_no;
    }

    public void setGd_no(String gd_no) {
        this.gd_no = gd_no;
    }

    public int getMg_gvol() {
        return mg_gvol;
    }

    public void setMg_gvol(int mg_gvol) {
        this.mg_gvol = mg_gvol;
    }

    public int getMg_gnum() {
        return mg_gnum;
    }

    public void setMg_gnum(int mg_gnum) {
        this.mg_gnum = mg_gnum;
    }

    public int getChanStatus() {
        return chanStatus;
    }

    public void setChanStatus(int chanStatus) {
        this.chanStatus = chanStatus;
    }
}
