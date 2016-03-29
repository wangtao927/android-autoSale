package com.ys.jni;

import android.util.Log;

import com.landfoneapi.misposwa.E_REQ_RETURN;
import com.landfoneapi.misposwa.MyApi;
import com.ys.ui.service.MyService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                Log.d(TAG, String.format("onDataReceive: path:%s,port:%s", path, port));

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
    private MyApi mMyApi = new MyApi();
    public void getMiniPosSerial(String path, int port) {
        mMyApi.setPOSISerialPort(null);//null时使用android的串口jni，android_serialport_api.SerialPort
        //设置透传ip、端口；POS的串口路径和波特率
        mMyApi.pos_init("211.147.64.198", 5800, path, String.valueOf(port));//"/dev/ttyS1"//lf

        if(E_REQ_RETURN.REQ_OK != (mMyApi.pos_signin())){
            //调用失败，可能的返回如下
            //E_REQ_RETURN.REQ_BUSY//接口忙，有操作未完成
            //E_REQ_RETURN.REQ_DENY//与其它操作或状态冲突

         }else{
             // 记录该机器的   path ，port

         }
    }
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
