package com.ys.ui.serial.salemachine;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.ys.GetBytesUtils;
import com.ys.SerialPortFinder;
import com.ys.SerialPortTest;
import com.ys.ui.base.App;
import com.ys.ui.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android_serialport_api.SerialPort;


/*
*
 * Created by wangtao on 2016/5/2.
 * 扫描串口，获取售货机的串口命令
 */

public class SerialMachineHelper {

    protected App mApplication;
    protected SerialPort mSerialPort;
    protected OutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;
    SendingThread mSendingThread;
    byte[] mBuffer;

    static SerialMachineHelper helper = null;
    public static SerialMachineHelper getInstance() {
        if (helper == null) {
            return new SerialMachineHelper();
        }
        return helper;
    }
    /**
     * 读取终端设备数据
     * @author Administrator
    */
    boolean begin = false;
    boolean end = false;
    private class ReadThread extends Thread {

        @Override
        public void run() {
            super.run();

            // 定义一个包的最大长度
            int maxLength = 512;
            //byte[] buffer = new byte[maxLength];
            // 每次收到实际长度
            int available = 0;
            // 当前已经收到包的总长度
            int currentLength = 0;
            // 协议头长度4个字节（开始符1，类型1，长度2）
            int headerLength = 4;
            List<Byte> temp=new ArrayList<>();

            while (!isInterrupted()) {
                byte[] buffer = new byte[maxLength];
                try {
                    available = mInputStream.available();
                    if (available > 0) {

                        if (available > maxLength) {
                            available = maxLength;
                        }
                        mInputStream.read(buffer, currentLength, available);
                        currentLength += available;
                        for (int i=0; i<buffer.length;i++) {
                            if (buffer[i] == 0x02) {
                                begin = true;
                                end = false;
                            }

                            if (begin && !end) {

                                temp.add(buffer[i]);
                            }

                            if (buffer[i] == 0x03) {

                                byte[] temp2 = new byte[temp.size()];
                                for (int k = 0; k < temp.size(); k++) {
                                    temp2[k] = temp.get(k);
                                }

                                onDataReceive(temp2, temp2.length);

                                temp.clear();
                                end = true;
                                begin = false;

                            }
                        }


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private static SerialPortFinder finder = new SerialPortFinder();

    private String tmpPath;
    private String salePath="";

    private boolean flag = false;


    public void onDataReceive(byte[] buffer, int size) {

            // 存储 path 和port
            // 售货机 020B0003  将path 写到sharePre
            salePath = tmpPath;
        ToastUtils.showShortMessage("sale suc: path="+ salePath);


         SharedPreferences mySharedPreferences= App.getContext().getSharedPreferences("saleSerial",
                    Activity.MODE_PRIVATE);
            //实例化SharedPreferences.Editor对象（第二步）
            SharedPreferences.Editor editor = mySharedPreferences.edit();
            //用putString的方法保存数据
                        editor.putString("sale_baudrate", String.valueOf(mApplication.getSale_baudrate()));
                        editor.putString("sale_path", salePath);
            //提交当前数据
            editor.commit();
        flag = true;

        //使用toast信息提示框提示成功写入数据
            close();

    }


    private void close() {
        if (mReadThread != null) {
            mReadThread.interrupt();
            mReadThread = null;
        }
        if (mSendingThread != null) {
            mSendingThread.interrupt();
            mSendingThread = null;
        }
    }

    public void getSerial() {


        String[] driverPaths = finder.getAllDevicesPath();

        mApplication = (App)App.getContext();

        for (String path : driverPaths) {

            tmpPath = path;

            try {
                mSerialPort = mApplication.getSerialPort(path, mApplication.getSale_baudrate());

                mOutputStream = mSerialPort.getOutputStream();
                mInputStream = mSerialPort.getInputStream();
                mReadThread = new ReadThread();
                mReadThread.start();
                sendCmds();
                long startTime = System.currentTimeMillis();
                while (!flag) {
                    if ((System.currentTimeMillis() - startTime) > 3000) {
                        break;
                    } else {
                        Thread.sleep(300);
                    }
                }
                if (flag) {
                    ToastUtils.showShortMessage("suc: path= " + path);
                    break;
                }
            } catch (Exception e) {
                ToastUtils.showShortMessage("sale exception path: " + path + " e:" + e.getMessage());
                flag = false;
                continue;
            }

        }
    }

    private void sendCmds() {


        mBuffer =new byte[] {0x02, 0x0B, 0x0B, 0x03};

        mSendingThread = new SendingThread();
        mSendingThread.start();

    }
    private class SendingThread extends Thread {
        @Override
        public void run() {
             try {
                if (mOutputStream != null) {
                    mOutputStream.write(mBuffer, 0, mBuffer.length);
                } else {
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

        }
    }
}
