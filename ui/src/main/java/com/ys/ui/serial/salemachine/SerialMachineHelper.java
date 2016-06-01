package com.ys.ui.serial.salemachine;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

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

    private class ReadThread extends Thread {

        @Override
        public void run() {
            super.run();

            // 定义一个包的最大长度
            int maxLength = 32;
            //byte[] buffer = new byte[maxLength];
            // 每次收到实际长度
            // 协议头长度4个字节（开始符1，类型1，长度2）
            int headerLength = 4;

            while (!isInterrupted()) {
                byte[] buffer = new byte[maxLength];
                try {
                    if (buffer != null && buffer.length > 0) {

                        if (buffer[0] == 0x02) {
                            onDataReceive(buffer,buffer.length);
                        }
                        break;
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


    public void onDataReceive(byte[] buffer, int size) {

            // 存储 path 和port
            // 售货机 020B0003  将path 写到sharePre
            salePath = tmpPath;

            mSendingThread.interrupt();
            SharedPreferences mySharedPreferences= App.getContext().getSharedPreferences("saleSerial",
                    Activity.MODE_PRIVATE);
            //实例化SharedPreferences.Editor对象（第二步）
            SharedPreferences.Editor editor = mySharedPreferences.edit();
            //用putString的方法保存数据
                        editor.putString("sale_baudrate", String.valueOf(mApplication.getSale_baudrate()));
                        editor.putString("sale_path", salePath);
            //提交当前数据
            editor.commit();
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

        for (String path : driverPaths) {

            tmpPath = path;
            try {

                mSerialPort = mApplication.getSerialPort(path, mApplication.getSale_baudrate());

                mOutputStream = mSerialPort.getOutputStream();
                mInputStream = mSerialPort.getInputStream();
			/* Create a receiving thread */
                mReadThread = new ReadThread();
                mReadThread.start();

                sendCmds();
            } catch (Exception e) {
                ToastUtils.showShortMessage("sale exception path: "+path);
                continue;
            }
            try {
                Thread.sleep(10000);
                mReadThread.interrupt();
                if (!TextUtils.isEmpty(salePath)) {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
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
