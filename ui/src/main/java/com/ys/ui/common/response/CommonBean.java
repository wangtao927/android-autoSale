package com.ys.ui.common.response;

import com.ys.data.bean.GoodsBean;
import com.ys.data.bean.McGoodsBean;
import com.ys.data.bean.McParamsBean;
import com.ys.data.bean.McStatusBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangtao on 2016/4/5.
 */
public class CommonBean implements Serializable {

    List<McParamsBean>  mcparam;

    List<McGoodsBean> mcgoods;

    List<GoodsBean> goods;

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
}
