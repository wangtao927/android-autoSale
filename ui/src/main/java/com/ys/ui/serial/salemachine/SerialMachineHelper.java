package com.ys.ui.serial.salemachine;

import android.os.Bundle;
import android.util.Log;

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
    private static List<Integer> ports;

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

                        break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    void init() {

        mApplication = (App)App.getContext();

        try {
//            path = mApplication.getSale_path();
//
//            baudrate = mApplication.getSale_baudrate();
//
//            mSerialPort = mApplication.getSerialPort(path, baudrate);

            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();
 /*Create a receiving thread */

            mReadThread = new ReadThread();
            mReadThread.start();
        } catch (SecurityException e) {
            //DisplayError(R.string.error_security);
         } catch (InvalidParameterException e) {
         } catch (Exception e) {

        }
    }


    protected void onDestroy() {
        if (mReadThread != null) {
            mReadThread.interrupt();
        }
        mApplication.closeSerialPort();
        mSerialPort = null;
     }


    private static SerialPortFinder finder = new SerialPortFinder();
    static {
        ports = new ArrayList<>();
        ports.add(4800);
        ports.add(9600);
        ports.add(19200);
        ports.add(38400);
    }
    private SerialPortTest test = null;
    private SerialPort serialPort = null;

    private String path;

    private int port;

    private Map<String, String> serialMap = new HashMap();
    SerialPortTest.OnDataReceiveListener  listener = new SerialPortTest.OnDataReceiveListener() {
        @Override
        public void onDataReceive(byte[] buffer, int size) {

            if (test != null) {
                // 存储 path 和port
                // 写到公共缓存中，或者 sqllite

                // 售货机 020B0003

                // 打印机

                // pos

                serialMap.put(path, String.valueOf(port));

                test.closeSerialPort();

            }
        }
    };


    //获取售货机串口  名字 和端口
    public void getSaleSerial() {
        this.getSerial("020B0B03");
    }


    //获取 打印机串口信息

    private void getPrintSerial() {
        this.getSerial("");
    }
    // 获取银联支付串口

    private void getSerial(String cmds) {
        String[] driverPaths = finder.getAllDevicesPath();

        for (String path : driverPaths) {
            if (serialMap.containsKey(path)) {
                continue;
            }
            for (int port : ports) {
                try {
                    try {
                        serialPort = new SerialPort(new File(path), port, 0);

                    } catch (IOException e) {
                        // 打开串口失败

                        continue;
                    }
                    this.path = path;
                    this.port = port;

                } catch (SecurityException e) {
                    continue;
                }
            }
        }
    }

}
