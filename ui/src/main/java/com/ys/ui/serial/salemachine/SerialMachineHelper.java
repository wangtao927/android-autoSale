/*
package com.ys.ui.serial.salemachine;

import android.os.Bundle;

import com.ys.ui.base.App;
import com.ys.ui.utils.ToastUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;



*/
/**
 * Created by wangtao on 2016/5/2.
 * 扫描串口，获取售货机的串口命令
 *//*

public class SerialMachineHelper {

    protected App mApplication;
    protected SerialPort mSerialPort;
    protected OutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;
    */
/**
     * 读取终端设备数据
     * @author Administrator
     *//*

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
			*/
/* Create a receiving thread *//*

            mReadThread = new ReadThread();
            mReadThread.start();
        } catch (SecurityException e) {
            //DisplayError(R.string.error_security);
         } catch (InvalidParameterException e) {
         } catch (Exception e) {

        }
    }


    protected void onDestroy() {
        if (mReadThread != null)
            mReadThread.interrupt();
        mApplication.closeSerialPort();
        mSerialPort = null;
     }

}
*/
