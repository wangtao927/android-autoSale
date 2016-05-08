package com.ys.data.bean;

import java.io.Serializable;

/**
 * Created by wangtao on 2016/4/29.
 */
public class McStoreUpdateVO implements Serializable {

    private String mg_channo;

    private Long mg_gnum;

    private Long mg_chann_status;// 默认不卡货   1 正常  2 卡货


    public McStoreUpdateVO() {
    }

    public McStoreUpdateVO(String mg_channo, Long mg_gnum, Long chanStatus) {
        this.mg_channo = mg_channo;
        this.mg_chann_status = chanStatus;
        this.mg_gnum = mg_gnum;
    }

    public String getMg_channo() {
        return mg_channo;
    }

    public void setMg_channo(String mg_channo) {
        this.mg_channo = mg_channo;
    }


    public Long getMg_gnum() {
        return mg_gnum;
    }

    public void setMg_gnum(Long mg_gnum) {
        this.mg_gnum = mg_gnum;
    }

    public Long getMg_chann_status() {
        return mg_chann_status;
    }

    public void setMg_chann_status(Long mg_chann_status) {
        this.mg_chann_status = mg_chann_status;
    }
}
