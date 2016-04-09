package com.ys.ui.common.response;

import com.ys.data.bean.GoodsBean;
import com.ys.data.bean.McAdminBean;
import com.ys.data.bean.McGoodsBean;
import com.ys.data.bean.McParamsBean;
import com.ys.data.bean.McStatusBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangtao on 2016/4/9.
 */
public class TermInitResult implements Serializable {

    List<McParamsBean> mcparam;

    List<McGoodsBean> mcgoods;

    List<GoodsBean> goods;

    List<McAdminBean> mcadmin;

    McStatusBean machine;

    // 广告数据 TODO:


    public TermInitResult() {
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

    public List<McAdminBean> getMcadmin() {
        return mcadmin;
    }

    public void setMcadmin(List<McAdminBean> mcadmin) {
        this.mcadmin = mcadmin;
    }

    public McStatusBean getMachine() {
        return machine;
    }

    public void setMachine(McStatusBean machine) {
        this.machine = machine;
    }
}
