package com.ys.ui.service;

import java.util.List;

/**
 * Created by wangtao on 2016/4/4.
 */
public interface THDaoHelperInterface<T> {

    public <T> void addData(T t);
    public void deleteData(String id);
    public <T> T getDataById(String id);
    public List getAllData();
    public boolean hasKey(String id);
    public long getTotalCount();
    public void deleteAll();
}
