package com.ys.ui.common.response;

import com.ys.data.bean.GoodsBean;
import com.ys.data.bean.McGoodsBean;
import com.ys.data.bean.McParamsBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by wangtao on 2016/4/5.
 */
public class TermInitResponse implements Serializable {

    int code;

    String msg;

    List<McParamsBean>  mcparam;

    List<McGoodsBean> mcgoods;

    List<GoodsBean> goods;


    public TermInitResponse() {
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

    public List<McParamsBean> getMcparam() {
        return mcparam;
    }

    public void setMcparam(List<McParamsBean> mcparam) {
        this.mcparam = mcparam;
    }

    public List<McGoodsBean> getMcgoods() {
        return mcgoods;
    }

    public void setMcgoods(List<McGoodsBean> mcgoods) {
        this.mcgoods = mcgoods;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "TermInitResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", mcparam=" + mcparam +
                ", mcgoods=" + mcgoods +
                ", goods=" + goods +
                '}';
    }
}
