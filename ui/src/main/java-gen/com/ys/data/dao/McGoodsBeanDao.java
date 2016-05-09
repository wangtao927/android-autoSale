package com.ys.data.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.ys.data.bean.McGoodsBean;

import java.util.List;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table mcgoods.
*/
public class McGoodsBeanDao extends AbstractDao<McGoodsBean, String> {

    public static final String TABLENAME = "mcgoods";

    /**
     * Properties of entity McGoodsBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Mc_no = new Property(0, String.class, "mc_no", false, "MC_NO");
        public final static Property Mg_channo = new Property(1, String.class, "mg_channo", true, "MG_CHANNO");
        public final static Property Gd_no = new Property(2, String.class, "gd_no", false, "GD_NO");
        public final static Property Gd_type = new Property(3, String.class, "gd_type", false, "GD_TYPE");
        public final static Property Gd_approve_code = new Property(4, String.class, "gd_approve_code", false, "GD_APPROVE_CODE");
        public final static Property Gd_batch_no = new Property(5, String.class, "gd_batch_no", false, "GD_BATCH_NO");
        public final static Property Gd_des_code = new Property(6, String.class, "gd_des_code", false, "GD_DES_CODE");
        public final static Property Gd_mf_date = new Property(7, String.class, "gd_mf_date", false, "GD_MF_DATE");
        public final static Property Gd_exp_date = new Property(8, String.class, "gd_exp_date", false, "GD_EXP_DATE");
        public final static Property Mg_gvol = new Property(9, Long.class, "mg_gvol", false, "MG_GVOL");
        public final static Property Mg_gnum = new Property(10, Long.class, "mg_gnum", false, "MG_GNUM");
        public final static Property Mg_pre_price = new Property(11, Long.class, "mg_pre_price", false, "MG_PRE_PRICE");
        public final static Property Mg_score_price = new Property(12, Long.class, "mg_score_price", false, "MG_SCORE_PRICE");
        public final static Property Mg_vip_price = new Property(13, Long.class, "mg_vip_price", false, "MG_VIP_PRICE");
        public final static Property Mg_disc_price = new Property(14, Long.class, "mg_disc_price", false, "MG_DISC_PRICE");
        public final static Property Mg_price = new Property(15, Long.class, "mg_price", false, "MG_PRICE");
        public final static Property Mg_chann_status = new Property(16, Long.class, "mg_chann_status", false, "MG_CHANN_STATUS");
        public final static Property Addtime = new Property(17, java.util.Date.class, "addtime", false, "ADDTIME");
        public final static Property Updatetime = new Property(18, java.util.Date.class, "updatetime", false, "UPDATETIME");
    };


    public McGoodsBeanDao(DaoConfig config) {
        super(config);
    }
    
    public McGoodsBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'mcgoods' (" + //
                "'MC_NO' TEXT," + // 0: mc_no
                "'MG_CHANNO' TEXT PRIMARY KEY NOT NULL ," + // 1: mg_channo
                "'GD_NO' TEXT," + // 2: gd_no
                "'GD_TYPE' TEXT," + // 3: gd_type
                "'GD_APPROVE_CODE' TEXT," + // 4: gd_approve_code
                "'GD_BATCH_NO' TEXT," + // 5: gd_batch_no
                "'GD_DES_CODE' TEXT," + // 6: gd_des_code
                "'GD_MF_DATE' TEXT," + // 7: gd_mf_date
                "'GD_EXP_DATE' TEXT," + // 8: gd_exp_date
                "'MG_GVOL' INTEGER," + // 9: mg_gvol
                "'MG_GNUM' INTEGER," + // 10: mg_gnum
                "'MG_PRE_PRICE' INTEGER," + // 11: mg_pre_price
                "'MG_SCORE_PRICE' INTEGER," + // 12: mg_score_price
                "'MG_VIP_PRICE' INTEGER," + // 13: mg_vip_price
                "'MG_DISC_PRICE' INTEGER," + // 14: mg_disc_price
                "'MG_PRICE' INTEGER," + // 15: mg_price
                "'MG_CHANN_STATUS' INTEGER," + // 16: mg_chann_status
                "'ADDTIME' INTEGER," + // 17: addtime
                "'UPDATETIME' INTEGER);"); // 18: updatetime
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'mcgoods'";
        db.execSQL(sql);
    }


    public void clearChanStatus(List<McGoodsBean> lists) {
        String sql = "UPDATE mcgoods SET MG_CHANN_STATUS=1";
        SQLiteStatement stmt = db.compileStatement(sql);
        if (db.isDbLockedByCurrentThread()) {
            synchronized (stmt) {
                stmt.execute();
                for (McGoodsBean bean : lists) {
                    bean.setMg_chann_status(1L);
                    attachEntity(bean.getMg_channo(), bean, true);
                }
            }
        } else {
            // Do TX to acquire a connection before locking the stmt to avoid deadlocks
            db.beginTransaction();
            try {
                synchronized (stmt) {
                    stmt.execute();
                    for (McGoodsBean bean : lists) {
                        bean.setMg_chann_status(1L);
                        attachEntity(bean.getMg_channo(), bean, true);
                    }
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }

    }

    public void reduceMcStore(String channo) {
        String sql = "UPDATE mcgoods SET mg_gnum=mg_gnum-1 WHERE mg_channo=? ";
        SQLiteStatement stmt = db.compileStatement(sql);
        if (db.isDbLockedByCurrentThread()) {
            synchronized (stmt) {
                stmt.bindString(1, channo);
                stmt.execute();
            }
        } else {
            // Do TX to acquire a connection before locking the stmt to avoid deadlocks
            db.beginTransaction();
            try {
                synchronized (stmt) {
                    stmt.bindString(1, channo);
                    stmt.execute();
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }

    }


    public void updateAll(List<McGoodsBean> lists) {
        String sql = "UPDATE mcgoods SET mg_gnum=mg_gvol ";
        SQLiteStatement stmt = db.compileStatement(sql);
        if (db.isDbLockedByCurrentThread()) {
            synchronized (stmt) {
                stmt.execute();
                for (McGoodsBean bean : lists) {
                    bean.setMg_gnum(bean.getMg_gvol());
                    attachEntity(bean.getMg_channo(), bean, true);
                }
            }
        } else {
            // Do TX to acquire a connection before locking the stmt to avoid deadlocks
            db.beginTransaction();
            try {
                synchronized (stmt) {
                    stmt.execute();
                    for (McGoodsBean bean : lists) {
                        bean.setMg_gnum(bean.getMg_gvol());
                        attachEntity(bean.getMg_channo(), bean, true);
                    }
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
    }

    public void updateChanStatusByChanno(String channo, Long mg_gnum, Long chanStatus) {
        String sql = "UPDATE mcgoods SET MG_CHANN_STATUS = ?, MG_GNUM =? WHERE mg_channo=? ";
        SQLiteStatement stmt = db.compileStatement(sql);
        if (db.isDbLockedByCurrentThread()) {
            synchronized (stmt) {
                stmt.bindLong(1, chanStatus);
                stmt.bindLong(2, mg_gnum);
                stmt.bindString(3, channo);
                stmt.execute();
            }
        } else {
            // Do TX to acquire a connection before locking the stmt to avoid deadlocks
            db.beginTransaction();
            try {
                synchronized (stmt) {
                    stmt.bindLong(1, chanStatus);
                    stmt.bindLong(2, mg_gnum);
                    stmt.bindString(3, channo);
                    stmt.execute();
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
    }

    public void updateChannelStatusByPK(McGoodsBean bean) {
        String sql = "UPDATE mcgoods SET MG_CHANN_STATUS = ? WHERE mg_channo=?";
        SQLiteStatement stmt = db.compileStatement(sql);
        if (db.isDbLockedByCurrentThread()) {
            synchronized (stmt) {
                stmt.bindLong(1, bean.getMg_chann_status());
                stmt.bindString(2, bean.getMg_channo());
                stmt.execute();
                attachEntity(bean.getMg_channo(), bean, true);
            }
        } else {
            // Do TX to acquire a connection before locking the stmt to avoid deadlocks
            db.beginTransaction();
            try {
                synchronized (stmt) {
                    stmt.bindLong(1, bean.getMg_chann_status());
                    stmt.bindString(2, bean.getMg_channo());
                    stmt.execute();
                    attachEntity(bean.getMg_channo(), bean, true);

                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }

    }

    public void updateGnumByPK(McGoodsBean bean) {
        String sql = "UPDATE mcgoods SET mg_gnum = ? WHERE mg_channo=?";
        SQLiteStatement stmt = db.compileStatement(sql);
        if (db.isDbLockedByCurrentThread()) {
            synchronized (stmt) {
                stmt.bindLong(1, bean.getMg_gnum());
                stmt.bindString(2, bean.getMg_channo());
                stmt.execute();
                attachEntity(bean.getMg_channo(), bean, true);

            }
        } else {
            // Do TX to acquire a connection before locking the stmt to avoid deadlocks
            db.beginTransaction();
            try {
                synchronized (stmt) {
                    stmt.bindLong(1, bean.getMg_gnum());
                    stmt.bindString(2, bean.getMg_channo());
                    stmt.execute();
                    attachEntity(bean.getMg_channo(), bean, true);

                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }

    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, McGoodsBean entity) {
        stmt.clearBindings();

        String mc_no = entity.getMc_no();
        if (mc_no != null) {
            stmt.bindString(1, mc_no);
        }

        String mg_channo = entity.getMg_channo();
        if (mg_channo != null) {
            stmt.bindString(2, mg_channo);
        }

        String gd_no = entity.getGd_no();
        if (gd_no != null) {
            stmt.bindString(3, gd_no);
        }

        String gd_type = entity.getGd_type();
        if (gd_type != null) {
            stmt.bindString(4, gd_type);
        }

        String gd_approve_code = entity.getGd_approve_code();
        if (gd_approve_code != null) {
            stmt.bindString(5, gd_approve_code);
        }

        String gd_batch_no = entity.getGd_batch_no();
        if (gd_batch_no != null) {
            stmt.bindString(6, gd_batch_no);
        }

        String gd_des_code = entity.getGd_des_code();
        if (gd_des_code != null) {
            stmt.bindString(7, gd_des_code);
        }

        String gd_mf_date = entity.getGd_mf_date();
        if (gd_mf_date != null) {
            stmt.bindString(8, gd_mf_date);
        }

        String gd_exp_date = entity.getGd_exp_date();
        if (gd_exp_date != null) {
            stmt.bindString(9, gd_exp_date);
        }

        Long mg_gvol = entity.getMg_gvol();
        if (mg_gvol != null) {
            stmt.bindLong(10, mg_gvol);
        }

        Long mg_gnum = entity.getMg_gnum();
        if (mg_gnum != null) {
            stmt.bindLong(11, mg_gnum);
        }

        Long mg_pre_price = entity.getMg_pre_price();
        if (mg_pre_price != null) {
            stmt.bindLong(12, mg_pre_price);
        }

        Long mg_score_price = entity.getMg_score_price();
        if (mg_score_price != null) {
            stmt.bindLong(13, mg_score_price);
        }

        Long mg_vip_price = entity.getMg_vip_price();
        if (mg_vip_price != null) {
            stmt.bindLong(14, mg_vip_price);
        }

        Long mg_disc_price = entity.getMg_disc_price();
        if (mg_disc_price != null) {
            stmt.bindLong(15, mg_disc_price);
        }

        Long mg_price = entity.getMg_price();
        if (mg_price != null) {
            stmt.bindLong(16, mg_price);
        }

        Long mg_chann_status = entity.getMg_chann_status();
        if (mg_chann_status != null) {
            stmt.bindLong(17, mg_chann_status);
        }

        java.util.Date addtime = entity.getAddtime();
        if (addtime != null) {
            stmt.bindLong(18, addtime.getTime());
        }

        java.util.Date updatetime = entity.getUpdatetime();
        if (updatetime != null) {
            stmt.bindLong(19, updatetime.getTime());
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1);
    }

    /** @inheritdoc */
    @Override
    public McGoodsBean readEntity(Cursor cursor, int offset) {
        McGoodsBean entity = new McGoodsBean( //
                cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // mc_no
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // mg_channo
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // gd_no
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // gd_type
                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // gd_approve_code
                cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // gd_batch_no
                cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // gd_des_code
                cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // gd_mf_date
                cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // gd_exp_date
                cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9), // mg_gvol
                cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10), // mg_gnum
                cursor.isNull(offset + 11) ? null : cursor.getLong(offset + 11), // mg_pre_price
                cursor.isNull(offset + 12) ? null : cursor.getLong(offset + 12), // mg_score_price
                cursor.isNull(offset + 13) ? null : cursor.getLong(offset + 13), // mg_vip_price
                cursor.isNull(offset + 14) ? null : cursor.getLong(offset + 14), // mg_disc_price
                cursor.isNull(offset + 15) ? null : cursor.getLong(offset + 15), // mg_price
                cursor.isNull(offset + 16) ? null : cursor.getLong(offset + 16), // mg_chann_status
                cursor.isNull(offset + 17) ? null : new java.util.Date(cursor.getLong(offset + 17)), // addtime
                cursor.isNull(offset + 18) ? null : new java.util.Date(cursor.getLong(offset + 18)) // updatetime
        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, McGoodsBean entity, int offset) {
        entity.setMc_no(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setMg_channo(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setGd_no(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setGd_type(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setGd_approve_code(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setGd_batch_no(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setGd_des_code(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setGd_mf_date(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setGd_exp_date(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setMg_gvol(cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9));
        entity.setMg_gnum(cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10));
        entity.setMg_pre_price(cursor.isNull(offset + 11) ? null : cursor.getLong(offset + 11));
        entity.setMg_score_price(cursor.isNull(offset + 12) ? null : cursor.getLong(offset + 12));
        entity.setMg_vip_price(cursor.isNull(offset + 13) ? null : cursor.getLong(offset + 13));
        entity.setMg_disc_price(cursor.isNull(offset + 14) ? null : cursor.getLong(offset + 14));
        entity.setMg_price(cursor.isNull(offset + 15) ? null : cursor.getLong(offset + 15));
        entity.setMg_chann_status(cursor.isNull(offset + 16) ? null : cursor.getLong(offset + 16));
        entity.setAddtime(cursor.isNull(offset + 17) ? null : new java.util.Date(cursor.getLong(offset + 17)));
        entity.setUpdatetime(cursor.isNull(offset + 18) ? null : new java.util.Date(cursor.getLong(offset + 18)));
    }

    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(McGoodsBean entity, long rowId) {
        return entity.getMg_channo();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(McGoodsBean entity) {
        if(entity != null) {
            return entity.getMg_channo();
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
