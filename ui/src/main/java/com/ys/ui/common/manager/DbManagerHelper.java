package com.ys.ui.common.manager;

import android.text.TextUtils;

import com.ys.data.bean.AdvBean;
import com.ys.data.bean.GoodsBean;
import com.ys.data.bean.McAdminBean;
import com.ys.data.bean.McGoodsBean;
import com.ys.data.bean.McParamsBean;
import com.ys.data.bean.McStatusBean;
import com.ys.data.bean.McStoreUpdateVO;
import com.ys.data.bean.SaleListBean;
import com.ys.data.dao.GoodsBeanDao;
import com.ys.data.dao.McGoodsBeanDao;
import com.ys.data.dao.McStatusBeanDao;
import com.ys.data.dao.SaleListBeanDao;
import com.ys.ui.base.App;
import com.ys.ui.common.constants.ChanStatusEnum;
import com.ys.ui.common.constants.SlOutStatusEnum;
import com.ys.ui.common.constants.SlPayStatusEnum;
import com.ys.ui.common.constants.SlSendStatusEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 添加卡货
     */
    public static void updateMcStatusChann(String channo) {


        McStatusBeanDao dao = App.getDaoSession(App.getContext()).getMcStatusBeanDao();
        McStatusBean mcStatusBean = dao.load(App.mcNo);
        if (TextUtils.isEmpty(mcStatusBean.getMr_chann_fault_nos())) {
            mcStatusBean.setMr_chann_fault_nos(channo);

        } else {
            mcStatusBean.setMr_chann_fault_nos(mcStatusBean.getMr_chann_fault_nos() + "|" + channo);
        }
        long faultNum = mcStatusBean.getMr_chann_fault_num() == null ? 0 : mcStatusBean.getMr_chann_fault_num();

        mcStatusBean.setMr_chann_fault_num(faultNum +1);
        dao.update(mcStatusBean);

    }

    /**
     * 批量清卡货
     */
    public static void updateMcStatusChannReduce(String chann) {
        McStatusBeanDao dao = App.getDaoSession(App.getContext()).getMcStatusBeanDao();
        McStatusBean mcStatusBean = dao.load(App.mcNo);
        String chanFaults = mcStatusBean.getMr_chann_fault_nos();
        if (!TextUtils.isEmpty(chanFaults)) {
            chanFaults =  chanFaults.replace(chann, "").replace("||", "|");
        }

        mcStatusBean.setMr_chann_fault_nos(chanFaults);
        mcStatusBean.setMr_chann_fault_num(mcStatusBean.getMr_chann_fault_num() - 1);
        dao.update(mcStatusBean);

    }

    public static void updateMcStatusChannAll() {
        McStatusBeanDao dao = App.getDaoSession(App.getContext()).getMcStatusBeanDao();
        McStatusBean mcStatusBean = dao.load(App.mcNo);
        mcStatusBean.setMr_chann_fault_num(0L);
        mcStatusBean.setMr_chann_fault_nos("");
        dao.update(mcStatusBean);

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
//            App.getDaoSession(App.getContext()).getGoodsBeanDao().deleteAll();
//            App.getDaoSession(App.getContext()).getGoodsBeanDao().insertInTx(goods);
            App.getDaoSession(App.getContext()).getGoodsBeanDao().deleteAndInsert(goods);
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

    public static void initAdv(List<AdvBean> mcAdvs) {
        if (mcAdvs != null && !mcAdvs.isEmpty()) {
            App.getDaoSession(App.getContext()).getAdvBeanDao().deleteAll();
            App.getDaoSession(App.getContext()).getAdvBeanDao().insertOrReplaceInTx(mcAdvs);
        }
    }

    public static void updateMcGoods(List<McGoodsBean> lists) {
        McGoodsBeanDao dao = App.getDaoSession(App.getContext()).getMcGoodsBeanDao();
        if (lists != null && !lists.isEmpty()){
            List<McGoodsBean> mcStore = dao.loadAll();
            Map<String, McGoodsBean> map = new HashMap<>();

            for (McGoodsBean mcGoodsBean : mcStore) {
                map.put(mcGoodsBean.getMg_channo(), mcGoodsBean);
            }

            for (int i =0 ; i < lists.size(); i++) {

                    if (lists.get(i).getMg_gnum() == null || lists.get(i).getMg_gnum().intValue() == 0) {
                        lists.get(i).setMg_gnum(map.get(lists.get(i).getMg_channo()).getMg_gnum());
                    }

            }
            App.getDaoSession(App.getContext()).getMcGoodsBeanDao().updateInTx(lists);
        }
    }


    public static void updateMcStoreChannStatus(String channo, ChanStatusEnum chanStatusEnum) {
        App.getDaoSession(App.getContext()).getMcGoodsBeanDao().updateChanStatusByChanno(
                channo, Long.valueOf(chanStatusEnum.getIndex()));
        // 更新机器库存卡货状态
        if (chanStatusEnum == ChanStatusEnum.ERROR) {
            // 添加卡货
            updateMcStatusChann(channo);

        } else if (chanStatusEnum == ChanStatusEnum.NORMAL) {
            // 清卡货

            updateMcStatusChannReduce(channo);
        }

    }

    public static void updateMcStore(List<McStoreUpdateVO> lists) {
        if (lists != null && !lists.isEmpty()) {
            McGoodsBeanDao dao = App.getDaoSession(App.getContext()).getMcGoodsBeanDao();
            for (McStoreUpdateVO storeUpdateVO : lists) {
                dao.updateChanStatusByChanno(storeUpdateVO.getMg_channo(), Long.valueOf(storeUpdateVO.getMg_chann_status()));

            }
        }
    }


    public static GoodsBean getGoodsInfo(String gdNo) {
        return App.getDaoSession(App.getContext()).getGoodsBeanDao().load(gdNo);
    }
    /**
     * 根据商户编号，获取出货信息
     * @param gdNo
     * @return
     */
    public static McGoodsBean getOutGoods(String gdNo) {

        List<McGoodsBean> mcGoodsBeanList = App.getDaoSession(App.getContext()).getMcGoodsBeanDao().queryBuilder()
                .where(McGoodsBeanDao.Properties.Gd_no.eq(gdNo)).where(McGoodsBeanDao.Properties.Mg_chann_status.eq(ChanStatusEnum.NORMAL.getIndex()))
                .where(McGoodsBeanDao.Properties.Mg_gnum.gt(0)).list();

        if (mcGoodsBeanList != null && !mcGoodsBeanList.isEmpty()) {
            return mcGoodsBeanList.get(0);
        } else {
            return null;
        }

    }
    public static McGoodsBean getOutGoodsByChanno(String channo) {

        List<McGoodsBean> mcGoodsBeanList = App.getDaoSession(App.getContext()).getMcGoodsBeanDao().queryBuilder()
                .where(McGoodsBeanDao.Properties.Mg_channo.eq(channo)).where(McGoodsBeanDao.Properties.Mg_chann_status.eq(ChanStatusEnum.NORMAL.getIndex()))
                .where(McGoodsBeanDao.Properties.Mg_gnum.gt(0)).list();

        if (mcGoodsBeanList != null && !mcGoodsBeanList.isEmpty()) {
            return mcGoodsBeanList.get(0);
        } else {
            return null;
        }

    }

    public static void reduceStore(String channo) {
        App.getDaoSession(App.getContext()).getMcGoodsBeanDao().reduceMcStore(channo);
    }

    public static void updateSendStatus(String slNo, SlSendStatusEnum slSendStatus) {
        App.getDaoSession(App.getContext()).getSaleListBeanDao().updateSendStatus(slNo, slSendStatus);
    }

    public static void updatePayStatus(String slNo, String slPayStatus) {

        App.getDaoSession(App.getContext()).getSaleListBeanDao().updatePayStatus(slNo, slPayStatus);

    }

    public static void updateOutStatus(String slNo, SlOutStatusEnum slOutStatus) {

        App.getDaoSession(App.getContext()).getSaleListBeanDao().updateOutStatus(slNo, slOutStatus);

    }
    public static void updateOutStatusFail(String slNo, SlOutStatusEnum newslOutStatus, SlOutStatusEnum oldStatus) {

        App.getDaoSession(App.getContext()).getSaleListBeanDao().updateOutStatusFail(slNo, newslOutStatus, oldStatus);

    }



    public static SaleListBean getSaleRecord(String slNo) {
        return  App.getDaoSession(App.getContext()).getSaleListBeanDao().queryBuilder().where(SaleListBeanDao.Properties.Sl_no.eq(slNo)).unique();
    }

    public static void updateSendStatus(List<SaleListBean> saleListBeans) {

        App.getDaoSession(App.getContext()).getSaleListBeanDao().updateSendStatusBatch(saleListBeans,  SlSendStatusEnum.FINISH);
//         for (SaleListBean saleListBean: saleListBeans) {
//             updateSendStatus(saleListBean.getSl_no(), SlSendStatusEnum.FINISH);
//        }
    }

    public static McStatusBean getMcStatusBean() {
        List<McStatusBean> list =  App.getDaoSession(App.getContext()).getMcStatusBeanDao().loadAll();
        if(list==null || list.size()<=0) return null;
        return list.get(0);
    }
    public static String getMcNo() {
        List<McStatusBean> list =  App.getDaoSession(App.getContext()).getMcStatusBeanDao().loadAll();
        if(list==null || list.size()<=0) return null;
        return list.get(0).getMc_no();
    }

    public static void clearAllChannStatus(List<McGoodsBean> lists) {
        App.getDaoSession(App.getContext()).getMcGoodsBeanDao().clearChanStatus(lists);

        updateMcStatusChannAll();
    }

    public static List<GoodsBean> queryGoodsByKeyword(String keyword) {

        return App.getDaoSession(App.getContext()).getGoodsBeanDao().queryBuilder().where(GoodsBeanDao.Properties.Gd_keyword.like("%"+keyword+"%")).list();
    }

}
