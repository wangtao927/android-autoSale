package com.ys.ui.common.manager;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

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
     * @param mcStatusBean
     */
    public static void initTermStatus(McStatusBean mcStatusBean) {
        //  存储到  sqllite 中
        if (mcStatusBean != null) {
            App.getDaoSession(App.getContext()).getMcStatusBeanDao().deleteAll();
            App.getDaoSession(App.getContext()).getMcStatusBeanDao().insertOrReplace(mcStatusBean);
        }

    }


//
//    private String getPosition() {
//        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        double latitude = location.getLatitude();     //经度
//        double longitude = location.getLongitude(); //纬度
//        double altitude =  location.getAltitude();     //海拔
//    }
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