package com.ys.ui.service.impl;

import com.ys.data.bean.GoodsBean;
import com.ys.data.bean.McGoodsBean;
import com.ys.data.dao.GoodsBeanDao;
import com.ys.ui.service.THDaoHelperInterface;
import com.ys.ui.service.THDatabaseLoader;
import com.ys.ui.utils.StringUtils;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by wangtao on 2016/4/4.
 */
public class GoodsDaoHelper implements THDaoHelperInterface<McGoodsBean> {

    private static GoodsDaoHelper instance;
    private GoodsBeanDao goodsBeanDao;

    private GoodsDaoHelper() {
        try {
            goodsBeanDao = THDatabaseLoader.getDaoSession().getGoodsBeanDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GoodsDaoHelper getInstance() {
        if(instance == null) {
            instance = new GoodsDaoHelper();
        }

        return instance;
    }

    @Override
    public <T> void addData(T bean) {
        if(goodsBeanDao != null && bean != null) {
            goodsBeanDao.insertOrReplace((GoodsBean) bean);
        }
    }

    @Override
    public void deleteData(String id) {
        if(goodsBeanDao != null && !StringUtils.isEmpty(id)) {
            goodsBeanDao.deleteByKey(Integer.valueOf(id));
        }
    }

    @Override
    public GoodsBean getDataById(String id) {
        if(goodsBeanDao != null && !StringUtils.isEmpty(id)) {
            return goodsBeanDao.load(Integer.valueOf(id));
        }
        return null;
    }

    @Override
    public List getAllData() {
        if(goodsBeanDao != null) {
            return goodsBeanDao.loadAll();
        }
        return null;
    }

    @Override
    public boolean hasKey(String id) {
        if(goodsBeanDao == null || StringUtils.isEmpty(id)) {
            return false;
        }

        QueryBuilder<GoodsBean> qb = goodsBeanDao.queryBuilder();
        qb.where(goodsBeanDao.getPkProperty().eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if(goodsBeanDao == null) {
            return 0;
        }

        QueryBuilder<GoodsBean> qb = goodsBeanDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if(goodsBeanDao != null) {
            goodsBeanDao.deleteAll();
        }
    }
}
