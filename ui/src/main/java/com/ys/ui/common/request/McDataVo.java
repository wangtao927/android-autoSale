package com.ys.ui.common.request;

import com.ys.data.bean.McGoodsBean;
import com.ys.data.bean.McStatusBean;
import com.ys.data.bean.SaleListBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangtao on 2016/4/9.
 */
public class McDataVo implements Serializable {

    McStatusBean mcStatus;

    List<McGoodsBean> mcGoodsList;

    List<SaleListBean> mcSaleList;

    public McDataVo() {
    }

    public McStatusBean getMcStatus() {
        return mcStatus;
    }

    public void setMcStatus(McStatusBean mcStatus) {
        this.mcStatus = mcStatus;
    }

    public List<McGoodsBean> getMcGoodsList() {
        return mcGoodsList;
    }

    public void setMcGoodsList(List<McGoodsBean> mcGoodsList) {
        this.mcGoodsList = mcGoodsList;
    }

    public List<SaleListBean> getMcSaleList() {
        return mcSaleList;
    }

    public void setMcSaleList(List<SaleListBean> mcSaleList) {
        this.mcSaleList = mcSaleList;
    }

    @Override
    public String toString() {
        return "McDataVo{" +
                "mcStatus=" + mcStatus +
                //", mcGoodsList=" + mcGoodsList +
                ", mcSaleList=" + mcSaleList +
                '}';
    }
}
