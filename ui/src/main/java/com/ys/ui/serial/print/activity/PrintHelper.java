package com.ys.ui.serial.print.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.common.constants.PrintConstants;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android_serialport_api.SerialPort;

/**
 * Created by wangtao on 2016/5/7.
 */
public class PrintHelper {



    public App mApplication;
    public SerialPort mSerialPort = null;
    public OutputStream mOutputStream;

    public static PrintHelper printHelper;

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

    public void gdPrint(String orderNo, String termNo, String gdName, String gdType, String price, String vipPrice, String actualPrice) {
        // 初始化串口
        InitCom();

        // 打印条形码
        printCode39();

        // 打印内容
        print(orderNo, termNo, gdName, gdType, price, vipPrice, actualPrice);

        // 切纸
        printCut();

        CloseCom();

    }


    private void InitCom() {
        mApplication = (App) App.getContext();
        try {
            mSerialPort = mApplication.getPrintSerial();
            mOutputStream = mSerialPort.getOutputStream();
        } catch (SecurityException e) {
            DisplayError(R.string.error_security);
        } catch (IOException e) {
            DisplayError(R.string.error_unknown);
        } catch (InvalidParameterException e) {
            DisplayError(R.string.error_configuration);
        }
    }


    private void printCode39() {
        byte SendBuf[]={0X0D, 0X0A, 0X1D, 0X48, 0X02, 0X1D, 0X68, (byte) 0X80, 0X1D, 0X77, 0X02, 0X1D, 0X6B, 0X45, 0X09, 0X30, 0X31, 0X32, 0X33, 0X34, 0X35, 0X36, 0X37, 0X38, 0X0D, 0X0A};
        SendData(SendBuf);

    }

    private void print(String orderNo, String termNo, String gdName, String gdType, String price, String vipPrice, String actualPrice) {

        String mSendData =String.format(PrintConstants.content, orderNo, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()),
                termNo, gdName, gdType, price, vipPrice, actualPrice);

        byte SendBuf[]={0x1b,0x40};
        SendData(SendBuf);

        try {
            byte[] send = null;
            send = mSendData.getBytes("GBK");
            SendData(send);

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
                e.printStackTrace();
            }
        } else {

         }
    }

    private void CloseCom() {
        mApplication.closePrintSerialPort();
        mSerialPort = null;
    }

    protected  void onDataReceived(final byte[] buffer, final int size) {
        // do nothing
    }


}
