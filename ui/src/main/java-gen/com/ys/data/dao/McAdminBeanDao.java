package com.ys.data.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.ys.data.bean.McAdminBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table mcadmin.
*/
public class McAdminBeanDao extends AbstractDao<McAdminBean, String> {

    public static final String TABLENAME = "mcadmin";

    /**
     * Properties of entity McAdminBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property U_no = new Property(0, String.class, "u_no", true, "U_NO");
        public final static Property U_pwd = new Property(1, String.class, "u_pwd", false, "U_PWD");
    };


    public McAdminBeanDao(DaoConfig config) {
        super(config);
    }
    
    public McAdminBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'mcadmin' (" + //
                "'U_NO' TEXT PRIMARY KEY NOT NULL ," + // 0: u_no
                "'U_PWD' TEXT);"); // 1: u_pwd
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'mcadmin'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, McAdminBean entity) {
        stmt.clearBindings();
 
        String u_no = entity.getU_no();
        if (u_no != null) {
            stmt.bindString(1, u_no);
        }
 
        String u_pwd = entity.getU_pwd();
        if (u_pwd != null) {
            stmt.bindString(2, u_pwd);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public McAdminBean readEntity(Cursor cursor, int offset) {
        McAdminBean entity = new McAdminBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // u_no
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // u_pwd
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, McAdminBean entity, int offset) {
        entity.setU_no(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setU_pwd(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(McAdminBean entity, long rowId) {
        return entity.getU_no();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(McAdminBean entity) {
        if(entity != null) {
            return entity.getU_no();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
