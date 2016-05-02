package com.ys.ui.utils;

import android.util.Log;

import com.ys.SerialPortFinder;
import com.ys.SerialPortTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android_serialport_api.SerialPort;

/**
 * Created by wangtao on 2016/3/26.
 */
public class SerialPortHelper {

    private String TAG = SerialPortHelper.class.getSimpleName();

    private static List<Integer> ports;

    private static SerialPortFinder finder = new SerialPortFinder();
    static {
        ports = new ArrayList<>();
        ports.add(4800);
        ports.add(9600);
        ports.add(19200);
        ports.add(38400);
        ports.add(57600);
        ports.add(115200);
    }
    // flag = 0  标识是售货机  1 打印机
    private int flag = 0;

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
                Log.d(TAG, String.format("onDataReceive: path:%s,port:%s", path, port));

                test.setIsStop(true);
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
        flag = 1;
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
                    test = SerialPortTest.getInstance(serialPort);
                    test.setOnDataReceiveListener(listener);
                    test.onCreate();
                    test.sendCmds(cmds);

                } catch (SecurityException e) {
                    continue;
                }
            }
        }
    }

}
