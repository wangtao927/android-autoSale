package com.ys.ui.common.response;

import java.io.Serializable;

/**
 * Created by wangtao on 2016/4/9.
 */
public class McDataResult implements Serializable {

    String oprcode;

    TermInitResult oprdata;

    public String getOprcode() {
        return oprcode;
    }

    public void setOprcode(String oprcode) {
        this.oprcode = oprcode;
    }

    public TermInitResult getOprdata() {
        return oprdata;
    }

    public void setOprdata(TermInitResult oprdata) {
        this.oprdata = oprdata;
    }

    @Override
    public String toString() {
        return "McDataResult{" +
                "oprcode=" + oprcode +
                ", oprdata=" + oprdata +
                '}';
    }
}
