package com.ys.ui.service;

import android.database.sqlite.SQLiteDatabase;

import com.ys.data.dao.DaoMaster;
import com.ys.data.dao.DaoSession;
import com.ys.ui.base.App;

/**
 * Created by wangtao on 2016/4/4.
 */
public class THDatabaseLoader {

    private static final String DATABASE_NAME = "PacPhotos-db";
    private static DaoSession daoSession;
    // 虚方法，可以无实体内容
    public static void init() {
        THDevOpenHelper helper = new THDevOpenHelper(App.getContext(), DATABASE_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
