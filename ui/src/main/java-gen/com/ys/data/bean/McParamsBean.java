package com.ys.data.bean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table mcparams.
 */
public class McParamsBean {

    private String pcode;
    private String pvalue;

    public McParamsBean() {
    }

    public McParamsBean(String pcode) {
        this.pcode = pcode;
    }

    public McParamsBean(String pcode, String pvalue) {
        this.pcode = pcode;
        this.pvalue = pvalue;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getPvalue() {
        return pvalue;
    }

    public void setPvalue(String pvalue) {
        this.pvalue = pvalue;
    }

}
