package com.ys.ui.serial.print.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.tencent.bugly.crashreport.CrashReport;
import com.ys.SerialPortFinder;
import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.common.constants.PrintConstants;
import com.ys.ui.utils.ToastUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android_serialport_api.SerialPort;

/**
 * Created by wangtao on 2016/5/7.
 */
public class PrintHelper {



    public App mApplication;
    public SerialPort mSerialPort = null;
    public OutputStream mOutputStream;

    private InputStream mInputStream;
    private ReadThread mReadThread;
    public static PrintHelper printHelper;

    private String printPath = "";
    private String tmpPath = "";

    public static PrintHelper getInstance() {
        if (printHelper == null) {
            return new PrintHelper();
        }
        return printHelper;
    }

    private void DisplayError(int resourceId) {
        AlertDialog.Builder b = new AlertDialog.Builder(App.getContext());
        b.setTitle("Error");
        b.setMessage(resourceId);
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //SerialPortActivity.this.finish();
            }
        });
        b.show();
    }

    public void gdPrint(String orderNo, String termNo, String gdName, String gdType, String price, String vipPrice, String actualPrice, String payType) {
        // 初始化串口
        try {
            InitCom();

            // 打印条形码
            Thread.sleep(1000);

            printCode39();


            // 打印内容
            Thread.sleep(1000);

            print(orderNo, termNo, gdName, gdType, price, vipPrice, actualPrice, payType);

            // 切纸

            Thread.sleep(2000);

            printCut();


            Thread.sleep(2000);
            CloseCom();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("gdPrint", App.mcNo + "slNo=" + orderNo + e.getMessage());
            CrashReport.postCatchedException(e);
        }

    }


    public void getStatus() {
        InitCom();
        byte SendBuf[]={0X10, 0X04, 0x01};
        SendData(SendBuf);

    }

    private void InitCom() {
        mApplication = (App) App.getContext();
        try {
            mSerialPort = mApplication.getPrintSerial();
            mOutputStream = mSerialPort.getOutputStream();

            mInputStream = mSerialPort.getInputStream();

            mReadThread = new ReadThread();
            mReadThread.start();
        } catch (SecurityException e) {
            Log.e("print InitCom", App.mcNo  + e.getMessage());
            DisplayError(R.string.error_security);
            CrashReport.postCatchedException(e);

        } catch (IOException e) {
            Log.e("print InitCom", App.mcNo  + e.getMessage());

            CrashReport.postCatchedException(e);

            DisplayError(R.string.error_unknown);
        } catch (InvalidParameterException e) {
            Log.e("print InitCom", App.mcNo  + e.getMessage());

            CrashReport.postCatchedException(e);

            DisplayError(R.string.error_configuration);
        }
    }

    static Map<Integer, Byte> map = new HashMap<>();
    static {
        map.put(50, (byte)0x30);
        map.put(51, (byte)0x31);
        map.put(52, (byte)0x32);
        map.put(53, (byte)0x33);
        map.put(54, (byte)0x34);
        map.put(55, (byte)0x35);
        map.put(56, (byte)0x36);
        map.put(57, (byte)0x37);
        map.put(58, (byte)0x38);
        map.put(59, (byte)0x39);

    }
    private void printCode39() {
        byte SendBuf[]={0X0D, 0X0A, 0X1D, 0X48, 0X02, 0X1D, 0X68, (byte) 0X80, 0X1D, 0X77, 0X02, 0X1D, 0X6B, 0X45, 0X09,
                0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0X0D, 0X0A};
        SendData(SendBuf);

    }

    private void print(String orderNo, String termNo, String gdName, String gdType, String price, String vipPrice, String actualPrice, String payType) {

        String mSendData =String.format(PrintConstants.content, orderNo, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                termNo, gdName, gdType, price, vipPrice, actualPrice, payType);

        byte SendBuf[]={0x1b,0x40};
        SendData(SendBuf);

        try {
            byte[] send = null;
            send = mSendData.getBytes("GBK");
            SendData(send);

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            CrashReport.postCatchedException(e);

        }
    }

    private void printCut() {

        byte SendBuf[]={0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x1b,0x69};
        SendData(SendBuf);
    }

    private void SendData(byte[] bytedata) {
        if (mSerialPort != null) {
            try {
                if (mOutputStream != null) {
                    mOutputStream.write(bytedata);
                } else {
                }
            } catch (IOException e) {
                Log.e("print SendData", App.mcNo + e.getMessage());
                CrashReport.postCatchedException(e);

            }
        } else {

         }
    }

    private void CloseCom() {
        mReadThread.interrupt();
        mApplication.closePrintSerialPort();
        mSerialPort = null;
    }

    private class ReadThread extends Thread {

        @Override
        public void run() {
            super.run();

            // 定义一个包的最大长度
            int maxLength = 32;
            //byte[] buffer = new byte[maxLength];
            // 每次收到实际长度
            int available = 0;
            // 当前已经收到包的总长度
            int currentLength = 0;
            // 协议头长度4个字节（开始符1，类型1，长度2）
            int headerLength = 4;

            while (!isInterrupted()) {
                byte[] buffer = new byte[maxLength];
                try {
                    available = mInputStream.available();
                    if (available > 0) {
                        // 防止超出数组最大长度导致溢出
                        if (available > maxLength - currentLength) {
                            available = maxLength - currentLength;
                        }
                        mInputStream.read(buffer, currentLength, available);
                        onDataReceived(buffer, available);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }


    protected  void onDataReceived(final byte[] buffer, final int size) {
        // do nothing
        Log.d("return", new String(buffer));
        mReadThread.interrupt();

        tmpPath = printPath;
        SharedPreferences mySharedPreferences= App.getContext().getSharedPreferences("printSerial",
                Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putString("print_baudrate", String.valueOf(mApplication.getPrint_baudrate()));
        editor.putString("print_path", tmpPath);
        //提交当前数据
        editor.commit();

        ToastUtils.showShortMessage(new String(buffer));
    }


    public void initPrint() {

        SerialPortFinder mSerialPortFinder = new SerialPortFinder();

        String[] paths = mSerialPortFinder.getAllDevicesPath();

        mApplication = (App) App.getContext();
        byte SendBuf[]={0X10, 0X04, 0x01};
        for (String path : paths) {
            if (!TextUtils.isEmpty(tmpPath) ) {
                break;
            }
            printPath = path;

            try {
                mSerialPort = mApplication.getSerialPort(path, mApplication.getPrint_baudrate());
                mOutputStream = mSerialPort.getOutputStream();

                mInputStream = mSerialPort.getInputStream();

                mReadThread = new ReadThread();
                mReadThread.start();
                SendData(SendBuf);
            } catch (Exception e) {

                continue;
            }
            try {
                Thread.sleep(2000);
                mReadThread.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        ToastUtils.showShortMessage("path:" + tmpPath);
    }

    private boolean openCom(String path,  int bodurate) {
        mApplication = (App) App.getContext();
        try {
            mSerialPort = mApplication.getSerialPort(path, bodurate);
            mOutputStream = mSerialPort.getOutputStream();

            mInputStream = mSerialPort.getInputStream();

            mReadThread = new ReadThread();
            mReadThread.start();
            return true;
        } catch (SecurityException e) {
            //DisplayError(R.string.error_security);
            return false;
        } catch (IOException e) {
            //DisplayError(R.string.error_unknown);
            return false;
        } catch (InvalidParameterException e) {
            //DisplayError(R.string.error_configuration);
            return false;
        } catch (Exception e) {
            return false;
        }
    }

}
