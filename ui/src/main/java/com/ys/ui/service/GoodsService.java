package com.ys.ui.service;

import android.content.Context;

import com.ys.data.dao.DaoSession;
import com.ys.data.dao.GoodsBeanDao;
import com.ys.data.dao.McGoodsBeanDao;
import com.ys.data.dao.McParamsBeanDao;
import com.ys.data.dao.McStatusBeanDao;
import com.ys.data.dao.PromotionBeanDao;
import com.ys.data.dao.SaleListBeanDao;
import com.ys.ui.base.App;

/**
 * Created by wangtao on 2016/4/1.
 */
public class GoodsService {

    private static final String TAG = GoodsService.class.getSimpleName();
    private static GoodsService instance;
    private static Context appContext;
    private DaoSession mDaoSession;

    private McStatusBeanDao mcStatusBeanDao;
    private McGoodsBeanDao mcGoodsBeanDao;
    private McParamsBeanDao mcParamsBeanDao;
    private SaleListBeanDao saleListBeanDao;
    private GoodsBeanDao goodsBeanDao;
    private PromotionBeanDao promotionBeanDao;


    private GoodsService() {
    }

    public static GoodsService getInstance(Context context) {
        if (instance == null) {
            instance = new GoodsService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = App.getDaoSession(context);
            instance.mcStatusBeanDao = instance.mDaoSession.getMcStatusBeanDao();
            instance.mcGoodsBeanDao = instance.mDaoSession.getMcGoodsBeanDao();
            instance.goodsBeanDao = instance.mDaoSession.getGoodsBeanDao();
            instance.promotionBeanDao = instance.mDaoSession.getPromotionBeanDao();
            instance.mcParamsBeanDao = instance.mDaoSession.getMcParamsBeanDao();
            instance.saleListBeanDao = instance.mDaoSession.getSaleListBeanDao();

        }
        return instance;
    }
}
