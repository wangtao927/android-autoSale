package com.ys.ui.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by wangtao on 2016/4/9.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
          Intent i = new Intent(context, TimerService.class);
        context.startService(i);
    }
}
