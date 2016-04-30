package com.ys.data.bean;

import java.io.Serializable;

/**
 * Created by wangtao on 2016/4/29.
 */
public class McStoreUpdateVO implements Serializable {

    private String mg_channo;

    private Long chanStatus;// 默认不卡货   1 正常  2 卡货


    public McStoreUpdateVO() {
    }

    public McStoreUpdateVO(String mg_channo, Long chanStatus) {
        this.mg_channo = mg_channo;
        this.chanStatus = chanStatus;
    }

    public String getMg_channo() {
        return mg_channo;
    }

    public void setMg_channo(String mg_channo) {
        this.mg_channo = mg_channo;
    }

    public Long getChanStatus() {
        return chanStatus;
    }

    public void setChanStatus(Long chanStatus) {
        this.chanStatus = chanStatus;
    }
}
