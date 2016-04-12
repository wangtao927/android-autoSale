package com.ys.ui.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.ys.data.bean.McGoodsBean;
import com.ys.data.bean.McParamsBean;
import com.ys.data.bean.McStatusBean;
import com.ys.data.bean.SaleListBean;
import com.ys.data.dao.McParamsBeanDao;
import com.ys.data.dao.SaleListBeanDao;
import com.ys.ui.base.App;
import com.ys.ui.common.http.RetrofitManager;
import com.ys.ui.common.request.CommonRequest;
import com.ys.ui.common.request.McDataVo;
import com.ys.ui.common.response.CommonResponse;
import com.ys.ui.common.response.McDataResult;
import com.ys.ui.common.response.TermInitResult;
import com.ys.ui.utils.PropertyUtils;
import com.ys.ui.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
                PropertyUtils.getInstance().getOnlineSendSplit() * 60 * 1000;

        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pi);

        return super.onStartCommand(intent, flags, startId);
    }

    private List<McStatusBean> mcStatusBeanList;
    private List<McGoodsBean> mcGoodsBeanList;
    private List<SaleListBean> saleListBeans;
    private void startTimer() {
        McDataVo vo = new McDataVo();

        // 获取终端状态， 只有一条记录
        mcStatusBeanList =
                App.getDaoSession(App.getContext()).getMcStatusBeanDao().loadAll();
        McStatusBean bean = mcStatusBeanList.get(0);
        vo.setMcStatus(bean);

        // 获取库存信息
        mcGoodsBeanList = App.getDaoSession(App.getContext()).getMcGoodsBeanDao().loadAll();
        vo.setMcGoodsList(mcGoodsBeanList);
        //
        Query<SaleListBean> query =  App.getDaoSession(App.getContext()).getSaleListBeanDao()
                .queryBuilder().where(SaleListBeanDao.Properties.Sl_send_status.eq(0)).build();

        saleListBeans = query.list();
        //saleListBeans = App.getDaoSession(App.getContext()).queryBuilder().list()

        vo.setMcSaleList(saleListBeans);
        CommonRequest<McDataVo> request = new CommonRequest<>(
                bean.getMc_no(), System.currentTimeMillis(), vo);
        RetrofitManager.builder().postMcData(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //showProgress();

                    }
                })
                .subscribe(new Action1<CommonResponse<McDataResult>>() {
                    @Override
                    public void call(CommonResponse<McDataResult> response) {
                        Log.d("result", response.toString());
                        if (response.isSuccess()) {
                            // 生成成功  同步数据
                            // 判断数据，并更新
                            updateInfo(response.getExt_data().getOprcode(), response.getExt_data().getOprdata());

                        } else {

                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("error", throwable.toString());
                        //hideProgress();
                    }
                });
    }

    private void  updateInfo(String oprCodes, TermInitResult result) {
        if (!StringUtils.isEmpty(oprCodes)) {
            String[]  codes = oprCodes.split(",");

            for (String code : codes) {
                 switch (code) {
                     case "01":// 终端基本信息  终端号  等
                         if (result.getMachine() != null) {
                             // 先查出主键， 然后更新
                             McStatusBean statusBean = App.getDaoSession(App.getContext()).getMcStatusBeanDao().loadAll().get(0);
                            // App.getDaoSession(App.getContext()).getMcStatusBeanDao().update();
                         }
                         break;
                     case "02":// 终端参数

                         break;
                     case "03":// 库存信息 根据货道号更新  商品编码  容量  价格

                         break;
                     case "04"://终端管理员  // 全部删除， 再插入

                         break;
                     case "05"://广告 全部删除 再插入

                         break;
                     case "06": // 下载商品信息

                         break;
                     default:
                         break;
                 }
            }
        }



    }
}
