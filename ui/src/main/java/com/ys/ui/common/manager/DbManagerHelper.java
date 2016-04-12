package com.ys.ui.common.manager;

import com.ys.data.bean.GoodsBean;
import com.ys.data.bean.McAdminBean;
import com.ys.data.bean.McGoodsBean;
import com.ys.data.bean.McParamsBean;
import com.ys.data.bean.McStatusBean;
import com.ys.ui.base.App;
import com.ys.ui.utils.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by wangtao on 2016/4/13.
 */
public class DbManagerHelper {


    /**
     *  保存终端号   保证这张表就一条数据
     * @param termNo
     */
    public static void initTermStatus(String termNo, String serialNo) {
        //  存储到  sqllite 中
        if (!StringUtils.isEmpty(termNo)) {
            App.getDaoSession(App.getContext()).getMcStatusBeanDao().deleteAll();
            App.getDaoSession(App.getContext()).getMcStatusBeanDao().insertOrReplace(getInitBean(termNo, serialNo));
        }

    }

    private static McStatusBean getInitBean(String termno, String serialNo) {
        McStatusBean bean = new McStatusBean();
        bean.setMc_id("1");
        bean.setMc_no(termno);
        bean.setMc_serial_no(serialNo);
        bean.setAddtime(new Date());
        return bean;
    }
    /**
     * 初始化终端参数表
     * @param lists
     */
    public static void initMcParam(List<McParamsBean> lists) {
        if (lists != null && !lists.isEmpty()) {
            App.getDaoSession(App.getContext()).getMcParamsBeanDao().deleteAll();
            App.getDaoSession(App.getContext()).getMcParamsBeanDao().insertInTx(lists);
        }

    }

    /**
     * 初始化商品表
     * @param goods
     */
    public static void initGoods(List<GoodsBean> goods) {
        if (goods != null && !goods.isEmpty()) {
            App.getDaoSession(App.getContext()).getGoodsBeanDao().deleteAll();
            App.getDaoSession(App.getContext()).getGoodsBeanDao().insertInTx(goods);
        }

    }

    /**
     * 初始化 终端库存表
     * @param mcgoods
     */
    public static void initMcGoods(List<McGoodsBean> mcgoods) {
        if (mcgoods != null && !mcgoods.isEmpty()) {
            App.getDaoSession(App.getContext()).getMcGoodsBeanDao().deleteAll();
            App.getDaoSession(App.getContext()).getMcGoodsBeanDao().insertInTx(mcgoods);
        }

    }

    public static void initAdmin(List<McAdminBean> admins){

        if (admins != null && !admins.isEmpty()) {
            App.getDaoSession(App.getContext()).getMcAdminBeanDao().deleteAll();
            App.getDaoSession(App.getContext()).getMcAdminBeanDao().insertOrReplaceInTx(admins);
        }
    }

    public static void updateMcGoods(List<McGoodsBean> lists) {
        if (lists != null && !lists.isEmpty()){
            App.getDaoSession(App.getContext()).getMcGoodsBeanDao().updateInTx(lists);
        }
    }
}
