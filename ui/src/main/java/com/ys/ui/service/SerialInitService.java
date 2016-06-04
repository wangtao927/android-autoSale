package com.ys.ui.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.ys.ui.serial.pos.PosSerialHelper;
import com.ys.ui.serial.print.activity.PrintHelper;
import com.ys.ui.serial.salemachine.SerialMachineHelper;
import com.ys.ui.utils.PropertyUtils;
import com.ys.ui.utils.ToastUtils;

/**
 * Created by wangtao on 2016/6/4.
 */
public class SerialInitService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // 初始化 串口
        try {
            PosSerialHelper.getInstance().setPath();

            PrintHelper.getInstance().initPrint();


            SerialMachineHelper.getInstance().getSerial();
        } catch (Exception e) {
            ToastUtils.showShortMessage("init serial error:" + e);
        }

        return super.onStartCommand(intent, flags, startId);
    }
}
