package com.ys;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android_serialport_api.SerialPort;

/**
 * Created by wangtao on 2016/3/28.
 */
public class SerialPortTest {

    private String TAG = SerialPortTest.class.getSimpleName();
    private SerialPort mSerialPort;
    private OutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;
    private static SerialPortTest portTest;
    private OnDataReceiveListener onDataReceiveListener = null;
    boolean isStop = false;

    public boolean isStop() {
        return isStop;
    }

    public void setIsStop(boolean isStop) {
        this.isStop = isStop;
    }

    public interface OnDataReceiveListener {
        public void onDataReceive(byte[] buffer, int size);
    }

    public SerialPortTest() {
    }

    public SerialPortTest(SerialPort mSerialPort) {
        this.mSerialPort = mSerialPort;
    }

    public SerialPort getmSerialPort() {
        return mSerialPort;
    }

    public void setmSerialPort(SerialPort mSerialPort) {
        this.mSerialPort = mSerialPort;
    }

    public void setOnDataReceiveListener(
            OnDataReceiveListener dataReceiveListener) {
        onDataReceiveListener = dataReceiveListener;
    }

    public static SerialPortTest getInstance(SerialPort serialPort) {
        if (null == portTest) {
            portTest = new SerialPortTest(serialPort);
            portTest.onCreate();
        }
        return portTest;
    }

    /**
     * 初始化串口信息
     */
    public void onCreate() {
        try {
            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();

            mReadThread = new ReadThread();
            isStop = false;
            mReadThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //initBle();
    }

    /**
     * 发送指令到串口
     *
     * @param cmd
     * @return
     */
    public boolean sendCmds(String cmd) {
        boolean result = true;
        byte[] mBuffer = (cmd + "\r\n").getBytes();
//注意：我得项目中需要在每次发送后面加\r\n，大家根据项目项目做修改，也可以去掉，直接发送mBuffer
        try {
            if (mOutputStream != null) {
                mOutputStream.write(mBuffer);
            } else {
                result = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public boolean sendBuffer(byte[] mBuffer) {
        boolean result = true;
        byte[] mBufferTemp = new byte[mBuffer.length];
        System.arraycopy(mBuffer, 0, mBufferTemp, 0, mBuffer.length);
        try {
            if (mOutputStream != null) {
                mOutputStream.write(mBufferTemp);
            } else {
                result = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    private class ReadThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (!isStop && !isInterrupted()) {
                int size;
                try {
                    if (mInputStream == null)
                        return;
                    byte[] buffer = new byte[16];
                    size = mInputStream.read(buffer);
                    if (size > 0) {
//                        if(MyLog.isDyeLevel()){
//                            MyLog.log(TAG, MyLog.DYE_LOG_LEVEL, "length is:"+size+",data is:"+new String(buffer, 0, size));
//                        }
                        Log.d(TAG, "length is:" + size + ",data is:" + new String(buffer, 0, size));
                        if (null != onDataReceiveListener) {
                            onDataReceiveListener.onDataReceive(buffer, size);
                        }
                    }
                    Thread.sleep(10);
                } catch (Exception e) {

                    return;
                }
            }
        }
    }

    /**
     * 关闭串口
     */
    public void closeSerialPort() {
        //sendShellCommond1();
        isStop = true;
        if (mReadThread != null) {
            mReadThread.interrupt();
        }
        if (mSerialPort != null) {
            mSerialPort.close();
        }
    }

}
