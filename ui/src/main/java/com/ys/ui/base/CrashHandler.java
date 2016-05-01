package com.ys.ui.base;

import android.content.Context;

import com.ys.ui.utils.ToastUtils;

/**
 * Created by wangtao on 2016/5/1.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String Tag = "CrashHandler";

    private Context mContext;

    private Thread.UncaughtExceptionHandler mDefaultHandler;



    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        mDefaultHandler.uncaughtException(thread, ex);
        ToastUtils.showShortMessage("error:" + ex);

    }

    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(mDefaultHandler);
    }
}
