package com.ys.ui.common.response;

import java.io.Serializable;

/**
 * Created by wangtao on 2016/4/9.
 */
public class CommonResponse<T> implements Serializable {

    protected int code;

    protected String msg;

    protected T ext_data;

    public T getExt_data() {
        return ext_data;
    }

    public void setExt_data(T ext_data) {
        this.ext_data = ext_data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return code == 0;
    }

    @Override
    public String toString() {
        return "CommonResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", ext_data=" + ext_data +
                '}';
    }
}
