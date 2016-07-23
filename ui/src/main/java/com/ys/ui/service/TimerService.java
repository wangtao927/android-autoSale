package com.ys.ui.service;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.bugly.crashreport.CrashReport;
import com.ys.data.bean.McGoodsBean;
import com.ys.data.bean.McParamsBean;
import com.ys.data.bean.McStatusBean;
import com.ys.data.bean.SaleListBean;
import com.ys.data.dao.McParamsBeanDao;
import com.ys.data.dao.SaleListBeanDao;
import com.ys.ui.base.App;
import com.ys.ui.common.constants.SlSendStatusEnum;
import com.ys.ui.common.http.RetrofitManager;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.common.request.CommonRequest;
import com.ys.ui.common.request.McDataVo;
import com.ys.ui.common.response.CommonResponse;
import com.ys.ui.common.response.McDataResult;
import com.ys.ui.common.response.TermInitResult;
import com.ys.ui.utils.PropertyUtils;
import com.ys.ui.utils.StringUtils;
import com.ys.ui.utils.ToastUtils;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.dao.query.Query;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wangtao on 2016/4/9.
 */
public class TimerService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                  startTimer();
            }
        }).start();
        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        //获取循环的时间
        long triggerTime = SystemClock.elapsedRealtime() +
                PropertyUtils.getInstance().getOnlineSendSplit() * 1000;

        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pi);

        // repeat 时间有点乱
//        manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,  SystemClock.elapsedRealtime() + 1000,
//        10 * 1000, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    private List<McStatusBean> mcStatusBeanList;
    private List<McGoodsBean> mcGoodsBeanList;
    private List<SaleListBean> saleListBeans;

    private String mcNo;//终端号

    private void startTimer() {
        McDataVo vo = new McDataVo();
        getLocation();
        // 获取终端状态， 只有一条记录
        if (TextUtils.isEmpty(App.mcNo )) {
            return ;
        }
        mcStatusBeanList =
                App.getDaoSession(App.getContext()).getMcStatusBeanDao().loadAll();
        McStatusBean bean;
        if (mcStatusBeanList != null  && !mcStatusBeanList.isEmpty()) {
            bean = mcStatusBeanList.get(0);
            mcNo = bean.getMc_no();
            try {

                if (TextUtils.isEmpty(bean.getMr_mc_position())) {

                    bean.setMr_mc_position(location.getLongitude() + "," + location.getLatitude());//经纬度

                }
            } catch (Exception e) {
                Log.e("TimerService", "mcNo:"+App.mcNo + e.getMessage());

                CrashReport.postCatchedException(e);
            }



        } else {
            return;
        }

        vo.setMcStatus(bean);

        // 获取库存信息
        mcGoodsBeanList = App.getDaoSession(App.getContext()).getMcGoodsBeanDao().loadAll();
        vo.setMcGoodsList(mcGoodsBeanList);

        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, -5);// 5分钟之前的时间

        Date beforeD = beforeTime.getTime();
        Query<SaleListBean> query =  App.getDaoSession(App.getContext()).getSaleListBeanDao()
                .queryBuilder().where(SaleListBeanDao.Properties.Sl_send_status.notEq(SlSendStatusEnum.FINISH.getIndex()))
                .where(SaleListBeanDao.Properties.Sl_time.lt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(beforeD))).build();

        saleListBeans = query.list();

        vo.setMcSaleList(saleListBeans);

        CrashReport.postCatchedException(new RuntimeException("TimerService upload:" + vo.toString()));

        CommonRequest<McDataVo> request = new CommonRequest<>(
                bean.getMc_no(), System.currentTimeMillis(), vo);
        RetrofitManager.builder().postMcData(request)
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        //showProgress();
//
//                    }
//                })
                .subscribe(new Action1<CommonResponse<McDataResult>>() {
                    @Override
                    public void call(CommonResponse<McDataResult> response) {
                        Log.d("result", response.toString());

                        if (response.isSuccess()) {
                            // 生成成功  同步数据
                            // 判断数据，并更新
                            try {
                                updateInfo(response.getExt_data().getOprcode(), response.getExt_data().getOprdata());
                            } catch (Exception e) {
                                Log.e("TimerService", "mcNo:" + mcNo + e.getMessage());
                                CrashReport.postCatchedException(e);
                            }

                            // 修改交易流水为上报成功
                            try {
                                Log.d("updateSendStatus", "mcNo:" + mcNo + saleListBeans.toString());
                                if (saleListBeans != null  && !saleListBeans.isEmpty()) {
                                    DbManagerHelper.updateSendStatus(saleListBeans);

                                }
                            } catch (Exception e) {
                                Log.e("postMcData", App.mcNo + e.getMessage());
                                CrashReport.postCatchedException(e);
                            }
                        } else {

                            CrashReport.postCatchedException(new Exception(response.toString()));

                        }


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("TimerService", throwable.toString());
                        //hideProgress();
                        CrashReport.postCatchedException(throwable);

                    }
                });
    }
    private Location location;

    private String provider;
    private void getLocation() {
        try {
            LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            List<String> providerList = manager.getProviders(true);
            if (providerList.contains(LocationManager.GPS_PROVIDER)) {
                provider = LocationManager.GPS_PROVIDER;
            } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
                provider = LocationManager.NETWORK_PROVIDER;

            } else {
                // 没有可用的位置服务
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            location = manager.getLastKnownLocation(provider);
        } catch (Exception e) {
            Log.e("timerservice", "get location error"+e.getMessage());
            CrashReport.postCatchedException(e);

        }
    }
    private void  updateInfo(String oprCodes, TermInitResult result) {
        if (!StringUtils.isEmpty(oprCodes)) {
            String[]  codes = oprCodes.split(",");
            Map map  = new HashMap<>();
            for (String code : codes) {
                 switch (code) {
                     case "01":// 终端基本信息  终端号  等
                         if (result.getMachine() != null) {

                             try {
                                 McStatusBean entity = result.getMachine();
                                 entity.setMc_no(mcNo);
                                 App.getDaoSession(App.getContext()).getMcStatusBeanDao().update(entity);
                                 map.put("01", "0");// 0 成功 1 失败
                             } catch (Exception e) {
                                 Log.e("updateMcStatus error", result.getMachine().toString() + e.getMessage());
                                 CrashReport.postCatchedException(e);

                                 map.put("01", "1");// 0 成功 1 失败
                             }

                         } else {
                             map.put("01", "1");// 0 成功 1 失败
                         }
                         break;
                     case "02":// 终端参数,  全部删除， 再插入
                         if (result.getMcparam() != null && !result.getMcparam().isEmpty()) {

                             try {
                                 DbManagerHelper.initMcParam(result.getMcparam());
                                 map.put("02", "0");// 0 成功 1 失败
                             } catch (Exception e) {
                                 Log.e("initMcParam", "param:" + result.getMcparam() + "error:" + e.getMessage());
                                 CrashReport.postCatchedException(e);
                                 map.put("02", "1");// 0 成功 1 失败
                             }
                             continue;
                         } else {
                             map.put("02", "1");// 0 成功 1 失败
                         }
                         break;
                     case "03":// 库存信息 根据货道号更新  商品编码  容量  价格
                         if (result.getMcgoods() != null && !result.getMcgoods().isEmpty()) {

                             try {
                                 DbManagerHelper.updateMcGoods(result.getMcgoods());
                                 map.put("03", "0");// 0 成功 1 失败
                             } catch (Exception e) {
                                 Log.e("updateMcGoods", "param:" + result.getMcgoods() + "error:" + e.getMessage());

                                 CrashReport.postCatchedException(e);
                                 map.put("03", "1");// 0 成功 1 失败
                             }
                         } else {
                             map.put("04", "1");// 0 成功 1 失败
                         }
                         break;
                     case "04"://终端管理员  // 全部删除， 再插入
                         if (result.getMcadmin() != null && !result.getMcadmin().isEmpty()) {

                             try {
                                 DbManagerHelper.initAdmin(result.getMcadmin());
                                 map.put("04", "0");// 0 成功 1 失败
                             } catch (Exception e) {
                                 Log.e("initAdmin", "param:" + result.getMcadmin() + "error:" + e.getMessage());

                                 CrashReport.postCatchedException(e);
                                 map.put("04", "1");// 0 成功 1 失败
                             }

                         } else {
                             map.put("04", "1");// 0 成功 1 失败

                         }
                         break;
                     case "05"://广告 全部删除 再插入

                         if (result.getMcadv() != null && !result.getMcadv().isEmpty()) {

                             try {
                                 DbManagerHelper.initAdv(result.getMcadv());
                                 map.put("05", "0");// 0 成功 1 失败
                             } catch (Exception e) {
                                 Log.e("initAdv", "param:" + result.getMcadv() + "error:" + e.getMessage());

                                 CrashReport.postCatchedException(e);
                                 map.put("05", "1");// 0 成功 1 失败
                             }

                         } else {
                             map.put("05", "1");// 0 成功 1 失败

                         }
                         break;
                     case "06": // 下载商品信息， 全部删除再插入
                          if (result.getGoods() != null && !result.getGoods().isEmpty()) {

                              try {
                                  DbManagerHelper.initGoods(result.getGoods());
                                  map.put("06", "0");// 0 成功 1 失败
                              } catch (Exception e) {
                                  Log.e("initGoods", "param:" + result.getGoods() + "error:" + e.getMessage());

                                  CrashReport.postCatchedException(e);
                                  map.put("06", "1");// 0 成功 1 失败
                              }
                          } else {
                              map.put("06", "1");// 0 成功 1 失败
                          }
                         break;
                     case "07": // 更新库存 卡货
                           if (result.getMcstore() != null && !result.getMcstore().isEmpty()){
                               try {
                                   DbManagerHelper.updateMcStore(result.getMcstore());
                                   map.put("07", "0");// 0 成功 1 失败
                               } catch (Exception e) {
                                   Log.e("updateMcStore", "param:" + result.getMcstore() + "error:" + e.getMessage());

                                   CrashReport.postCatchedException(e);
                                   map.put("07", "1");// 0 成功 1 失败
                               }
                           } else {
                               map.put("07", "1");// 0 成功 1 失败

                           }

                         break;
                     case "08":

                         break;
                     default:
                         break;
                 }
            }

            //上报 处理数据的结果

            if (!map.isEmpty()) {
                postOprStatus(map);
            }
        }



    }

    private void postOprStatus(Map<String, String> map) {
        RetrofitManager.builder().postOprStatus(mcNo, map)
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        //showProgress();
//
//                    }
//                })
                .subscribe(new Action1<CommonResponse<String>>() {
                    @Override
                    public void call(CommonResponse<String> response) {
                        Log.d("result", response.toString());

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("error", throwable.toString());
                        //hideProgress();
                        CrashReport.postCatchedException(throwable);
                    }
                });
    }
}
