package com.ys.ui.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.ys.SerialPortFinder;
import com.ys.data.dao.DaoMaster;
import com.ys.data.dao.DaoSession;
import com.ys.ui.R;
import com.ys.ui.activity.AdminActivity;
import com.ys.ui.activity.TermInitActivity;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.serial.pos.PosSerialHelper;
import com.ys.ui.serial.print.activity.PrintHelper;
import com.ys.ui.serial.salemachine.SerialMachineHelper;
import com.ys.ui.service.SerialInitService;
import com.ys.ui.service.TimerService;
import com.ys.ui.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android_serialport_api.SerialPort;


public class App extends Application {

    private static final String APP_ID = "900032569";
    public static Context ctx;
    private  static Application instance = null;
    private final String APP_CONTEXT_TAG = "appContext";

    private static DaoMaster daoMaster;
    private static DaoSession daoSession;


    public SerialPortFinder mSerialPortFinder = new SerialPortFinder();
    private SerialPort mSaleSerialPort = null;
    private SerialPort mPrintSerialPort = null;

    private int minipos_baudrate = 9600;
    private String minipos_path = "/dev/ttyES0";

    private int print_baudrate = 38400;
    private String print_path = "/dev/ttymxc2";


    private int sale_baudrate = 19200;
    private String sale_path = "/dev/ttyES1";

    public static String mcNo;


    @Override
    public void onCreate() {
        ctx = getApplicationContext();


        mcNo = DbManagerHelper.getMcNo();
//
        Intent intent = new Intent(this, TimerService.class);
        startService(intent);

        //注册bugly
        regBUgly();
        synchronized (this) {
            if (instance == null) {
                instance = this;
            }


//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(ctx);
            //PosSerialHelper.getInstance().setPath();

            //SerialMachineHelper.getInstance().getSerial();

        /*Intent intent1 = new Intent(this, SerialInitService.class);
        startService(intent1);*/
            CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(instance); //App的策略Bean
            strategy.setAppChannel(getPackageName());     //设置渠道
            strategy.setAppVersion(getVersion());      //App的版本
            strategy.setAppReportDelay(1000);  //设置SDK处理延时，毫秒
            strategy.setDeviceID(mcNo);
            strategy.setCrashHandleCallback(new AppCrashHandleCallback());

            CrashReport.initCrashReport(instance, mcNo, true, strategy); //自定义策略生效，必须在初始化SDK前调用
            CrashReport.setUserId("BBDTEK");
        }

        super.onCreate();

    }

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return this.getString(R.string.app_version) + version;
        } catch (Exception e) {
            e.printStackTrace();
            return this.getString(R.string.app_version);
        }
    }
    private void regBUgly() {
        /***** Beta高级设置 *****/
        /**
         * true表示app启动自动初始化升级模块;
         * false不会自动初始化;
         * 开发者如果担心sdk初始化影响app启动速度，可以设置为false，
         * 在后面某个时刻手动调用Beta.init(getApplicationContext(),false);
         */
        Beta.autoInit = true;

        /**
         * true表示初始化时自动检查升级;
         * false表示不会自动检查升级,需要手动调用Beta.checkUpgrade()方法;
         */
        Beta.autoCheckUpgrade = false;


        /**
         * 设置sd卡的Download为更新资源保存目录;
         * 后续更新资源会保存在此目录，需要在manifest中添加WRITE_EXTERNAL_STORAGE权限;
         */
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);


        Bugly.init(getApplicationContext(), APP_ID, false);
    }

//    public static RefWatcher getRefWatcher(Context context) {
//        App application = (App) context.getApplicationContext();
//        return application.refWatcher;
//    }

    protected boolean isInit() {
        if (App.mcNo == null) {
            return false;
        }
        return true;
    }
    // 获取ApplicationContext
    public static Context getContext() {
        return ctx;
    }

    public SerialPort getSerialPort(String path, int baudrate) throws SecurityException, IOException, InvalidParameterException {

        try {

            return new SerialPort(new File(path), baudrate, 0);

        } catch (Exception e) {
            CrashReport.postCatchedException(e);
            return null;
        }
            /* Open the serial port */

    }

    public SerialPort getSerialPort() throws SecurityException, IOException, InvalidParameterException {
        if (mSaleSerialPort == null) {
            /* Read serial port parameters */
            SharedPreferences sp = getSharedPreferences("saleSerial", MODE_PRIVATE);
            String path = sp.getString("sale_path", sale_path);
            int baudrate = Integer.decode(sp.getString("sale_baudrate", String.valueOf(sale_baudrate)));

			/* Check parameters */
            if ((path.length() == 0) || (baudrate == -1)) {
                ToastUtils.showError("baudarete is error ：" + baudrate, this.getApplicationContext());
                // throw new InvalidParameterException();
            }

			/* Open the serial port */
            mSaleSerialPort = new SerialPort(new File(path), baudrate, 0);
        }
        return mSaleSerialPort;
    }

    public SerialPort getPrintSerial() throws SecurityException, IOException, InvalidParameterException {
        if (mPrintSerialPort == null) {
            /* Read serial port parameters */
            SharedPreferences sp = getSharedPreferences("printSerial", MODE_PRIVATE);
            String path = sp.getString("print_path", print_path);
            int baudrate = Integer.decode(sp.getString("print_baudrate", String.valueOf(print_baudrate)));

			/* Check parameters */
            if ((path.length() == 0) || (baudrate == -1)) {
                ToastUtils.showError("baudarete is error ：" + baudrate, this.getApplicationContext());
                // throw new InvalidParameterException();
            }

			/* Open the serial port */
            mPrintSerialPort = new SerialPort(new File(path), baudrate, 0);
        }
        return mPrintSerialPort;
    }

    public void closeSaleSerialPort() {
        if (mSaleSerialPort != null) {
            mSaleSerialPort.close();
            mSaleSerialPort = null;
        }
    }

    public void closePrintSerialPort() {
        if (mPrintSerialPort != null) {
            mPrintSerialPort.close();
            mPrintSerialPort = null;
        }
    }

    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, Constants.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    public int getMinipos_baudrate() {
        SharedPreferences sp = getSharedPreferences("posSerial", MODE_PRIVATE);
        return Integer.decode(sp.getString("pos_baudrate", String.valueOf(minipos_baudrate)));

    }

    public void setMinipos_baudrate(int minipos_baudrate) {
        this.minipos_baudrate = minipos_baudrate;
    }

    public String getMinipos_path() {
        SharedPreferences sp = getSharedPreferences("posSerial", MODE_PRIVATE);
        return sp.getString("pos_path", minipos_path);
    }

    public void setMinipos_path(String minipos_path) {
        this.minipos_path = minipos_path;
    }

    public int getPrint_baudrate() {
        return print_baudrate;
    }

    public void setPrint_baudrate(int print_baudrate) {
        this.print_baudrate = print_baudrate;
    }

    public String getPrint_path() {
        return print_path;
    }

    public void setPrint_path(String print_path) {
        this.print_path = print_path;
    }

    public int getSale_baudrate() {
        return sale_baudrate;
    }

    public void setSale_baudrate(int sale_baudrate) {
        this.sale_baudrate = sale_baudrate;
    }

    public String getSale_path() {
        return sale_path;
    }

    public void setSale_path(String sale_path) {
        this.sale_path = sale_path;
    }



    private class AppCrashHandleCallback extends CrashReport.CrashHandleCallback //bugly回调
    {
        @Override
        public synchronized Map<String, String> onCrashHandleStart(int crashType, String errorType, String errorMessage, String errorStack)
        {
            String crashTypeName = null;
            switch (crashType)
            {
                case CrashReport.CrashHandleCallback.CRASHTYPE_JAVA_CATCH:
                    crashTypeName = "JAVA_CATCH";
                    break;
                case CrashReport.CrashHandleCallback.CRASHTYPE_JAVA_CRASH:
                    crashTypeName = "JAVA_CRASH";
                    break;
                case CrashReport.CrashHandleCallback.CRASHTYPE_NATIVE:
                    crashTypeName = "JAVA_NATIVE";
                    break;
                case CrashReport.CrashHandleCallback.CRASHTYPE_U3D:
                    crashTypeName = "JAVA_U3D";
                    break;
                default:
                {
                    crashTypeName = "unknown";
                }
            }

            Log.e(APP_CONTEXT_TAG, "Crash Happen Type:" + crashType + " TypeName:" + crashTypeName);
            Log.e(APP_CONTEXT_TAG, "errorType:" + errorType);
            Log.e(APP_CONTEXT_TAG, "errorMessage:" + errorMessage);
            Log.e(APP_CONTEXT_TAG, "errorStack:" + errorStack);

            Map<String, String> userDatas = super.onCrashHandleStart(crashType, errorType, errorMessage, errorStack);
            if (userDatas == null)
            {
                userDatas = new HashMap<String, String>();
            }

            userDatas.put("DEBUG", "TRUE");
            return userDatas;
        }

    }
}
