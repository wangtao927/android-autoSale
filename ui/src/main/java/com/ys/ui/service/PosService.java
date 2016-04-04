package com.ys.ui.service;

import com.landfoneapi.misposwa.E_REQ_RETURN;
import com.landfoneapi.misposwa.MyApi;

/**
 * Created by wangtao on 2016/4/1.
 */
public class PosService {

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
}
