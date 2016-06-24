package com.ys.ui.base;

import android.content.Context;
import android.content.Intent;

import com.tencent.bugly.crashreport.CrashReport;
import com.ys.ui.activity.HomeActivity;

/**
 * Created by wangtao on 2016/5/1.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String Tag = "CrashHandler";

    private Context mContext;


    private static CrashHandler INSTANCE = new CrashHandler();

    /** 保证只有一个CrashHandler实例 */
    private CrashHandler() {
    }

    /** 获取CrashHandler实例 ,单例模式 */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {


//        try {
//            handleException(ex);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // 新开任务栈
//        Intent intent = new Intent(mContext, HomeActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        mContext.startActivity(intent);
//        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        CrashReport.putUserData(mContext, "终端号" + App.mcNo, ex.getMessage());

        return true;
    }

    public void init(Context ctx) {
        mContext = ctx;

        Thread.setDefaultUncaughtExceptionHandler(this);
//        Thread.setDefaultUncaughtExceptionHandler(mDefaultHandler);
    }
}
