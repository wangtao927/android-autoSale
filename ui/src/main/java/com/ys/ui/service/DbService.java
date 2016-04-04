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

import java.util.Collection;
import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by wangtao on 2016/4/1.
 */
public interface DbService<V, K>{

    boolean insert(V v);

    boolean delete(V v);

    boolean deleteByKey(K key);

    boolean deleteList(List<K> list);

    boolean deleteByKeyIn(K... keys);

    boolean deleteAll();

    boolean insertOrReplate(V v);

    boolean update(V v);

    boolean updateIn(V... v);

    boolean updateList(List<V> lists);

    V selectByPrimaryKey(K key);

    List<V> loadAll();

    boolean refresh(V v);

    boolean dropDatabase();

    void runIn(Runnable runnable);

    AbstractDao<V, K> getAbstractDao();

    boolean insertList(List<V> lists);

    boolean insertOrReplaceList(List<V> lists);

    QueryBuilder<V> getQueryBuilder();

    List<V> queryRaw(String where, String... selectionArg);

    Query<V> queryRawCreate(String where, Object... selectArg);

    Query<V> queryRawCreateListArgs(String where, Collection<Object> selectionsArg);

    

}
