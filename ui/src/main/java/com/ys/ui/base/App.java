package com.ys.ui.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ys.SerialPortFinder;
import com.ys.data.dao.DaoMaster;
import com.ys.data.dao.DaoSession;
import com.ys.ui.activity.TermInitActivity;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.serial.pos.PosSerialHelper;
import com.ys.ui.serial.salemachine.SerialMachineHelper;
import com.ys.ui.service.TimerService;
import com.ys.ui.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;

import android_serialport_api.SerialPort;


public class App extends Application {

    public static Context ctx;

    private static DaoMaster daoMaster;
    private static DaoSession daoSession;


    public SerialPortFinder mSerialPortFinder = new SerialPortFinder();
    private SerialPort mSaleSerialPort = null;
    private SerialPort mPrintSerialPort = null;

    private int minipos_baudrate = 9600;
    private String minipos_path = "/dev/ttyES0";

    private int print_baudrate = 38400;
    private String print_path = "/dev/ttyS2";


    private int sale_baudrate = 19200;
    private String sale_path = "/dev/ttyES1";

    public static String mcNo;

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = getApplicationContext();


        mcNo = DbManagerHelper.getMcNo();
//
        Intent intent = new Intent(this, TimerService.class);
        startService(intent);

        //PosSerialHelper.getInstance().setPath();

        //SerialMachineHelper.getInstance().getSerial();


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

            return  new SerialPort(new File(path), baudrate, 0);

        } catch (Exception e) {

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
        return minipos_baudrate;
    }

    public void setMinipos_baudrate(int minipos_baudrate) {
        this.minipos_baudrate = minipos_baudrate;
    }

    public String getMinipos_path() {
        return minipos_path;
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
}
