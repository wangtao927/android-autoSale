package com.ys.ui.common.request;

import com.google.gson.Gson;
import com.ys.ui.common.sign.Signature;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangtao on 2016/4/9.
 */
public class CommonRequest<T> implements Serializable {

    String mc_no;

    long time;

    String sign;

    T data;

    public CommonRequest(String mc_no, long time, T data) {
        this.mc_no = mc_no;
        this.time = time;
        this.data = data;
        Map map = new HashMap();
        map.put("mc_no", mc_no);
        map.put("time", time);

        this.sign = Signature.getSign(map);
    }

    public String getMc_no() {
        return mc_no;
    }

    public void setMc_no(String mc_no) {
        this.mc_no = mc_no;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
    @Override
    public String toString() {
        return "CommonRequest{" +
                "mc_no='" + mc_no + '\'' +
                ", time=" + time +
                ", sign='" + sign + '\'' +
                ", data=" + data +
                '}';
    }
}
