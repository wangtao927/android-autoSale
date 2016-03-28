package com.ys.ui.analysis;

/**
 * Created by thinkpad on 2016/2/1.
 */
public class PayQueryAnalysisRst {
    private _common_rst rst = null;
    private Object FlagClass = null;
    public PayQueryAnalysisRst(){
    }
    public PayQueryAnalysisRst(_common_rst rst, Object FlagClass){
        this.rst = rst;
        this.FlagClass = FlagClass;
    }
    public void setRst(_common_rst rst) {
        this.rst = rst;
    }

    public void setFlagClass(Object flagClass) {
        FlagClass = flagClass;
    }

    public _common_rst getRst() {
        return rst;
    }

    public Object getFlagClass() {
        return FlagClass;
    }
}
