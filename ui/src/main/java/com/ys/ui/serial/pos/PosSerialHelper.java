package com.ys.ui.serial.pos;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.landfoneapi.misposwa.CallbackMsg;
import com.landfoneapi.misposwa.E_REQ_RETURN;
import com.landfoneapi.misposwa.ILfListener;
import com.landfoneapi.misposwa.MyApi;
import com.landfoneapi.protocol.pkg.Display;
import com.landfoneapi.protocol.pkg.DisplayType;
import com.landfoneapi.protocol.pkg._04_GetRecordReply;
import com.ys.SerialPortFinder;
import com.ys.ui.base.App;
import com.ys.ui.service.MyService;
import com.ys.ui.utils.ToastUtils;

/**
 * Created by wangtao on 2016/5/13.
 */
public class PosSerialHelper {
    private String TAG = "PosSerialHelper";

    App mApplication;
    private MyApi mMyApi = new MyApi();

    static PosSerialHelper posSerialHelper;
    Context context;

    public PosSerialHelper() {
        context =  App.getContext();
        mMyApi.setILfListener(mILfMsgHandler);
    }

    public static PosSerialHelper getInstance() {
        if (posSerialHelper == null) {
            posSerialHelper = new PosSerialHelper();
        }
        return posSerialHelper;
    }

    private String tmpPath;
    int flag = 0;

    public void setPath() {
        try {
            SerialPortFinder mSerialPortFinder = new SerialPortFinder();
            String[] paths = mSerialPortFinder.getAllDevicesPath();
            mApplication = (App) App.getContext();
            for (String path : paths) {

                if (TextUtils.isEmpty(path) || path.contains("USB")) {
                    continue;
                }
                tmpPath = path;
                try {
                     if (posInit(path) && posSign()) {
                        ToastUtils.showShortMessage("串口连接成功-path=" + path);
                         break;
                    } else {
                         pos_release();
                    }
                } catch (Exception e) {
                    flag = 0;
                    tmpPath = "";
                    pos_release();
                    ToastUtils.showError("exception path=" + path + "ex=" + e, App.getContext());
                    continue;
                }
            }
        } catch (Exception e) {
            ToastUtils.showError("exception "  + e, App.getContext());
        }

    }

    public boolean posInit(String path) {

        try {
            E_REQ_RETURN result = pos_init(path, mApplication.getMinipos_baudrate());

            return E_REQ_RETURN.REQ_OK == result;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean posSign() {

        flag = 0;
        E_REQ_RETURN req_return = pos_signin();
        if (E_REQ_RETURN.REQ_OK == req_return) {
            try {
                Thread.sleep(10000);
            } catch (Exception e) {

            }
            return true;

        }
        return false;
    }


    //////////////////////////////////////////业务返回///////////////////////////////////////
    private Handler mmHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            CallbackMsg cbmsg = (CallbackMsg) msg.obj;
            switch (cbmsg.op_type) {//信息（交易）类型
                case OP_POS_SIGNIN://签到
                case OP_POS_QUERY://查询
                case OP_POS_PURCHASE://支付
                    ToastUtils.showShortMessage("pos return reply=" + cbmsg.reply + "--info="+cbmsg.info);
                    if (cbmsg.reply == 0) {//成功
                            SharedPreferences mySharedPreferences = App.getContext().getSharedPreferences("posSerial",
                                    Activity.MODE_PRIVATE);
                            //实例化SharedPreferences.Editor对象（第二步）
                            SharedPreferences.Editor editor = mySharedPreferences.edit();
                            //用putString的方法保存数据
                            editor.putString("pos_path", tmpPath);
                            //提交当前数据
                            editor.commit();


                    } else if (cbmsg.reply == 1) {//失败

                    }
                    break;
                case OP_POS_DISPLAY://POS提示信息
                    //ToastUtils.showShortMessage("OP_POS_DISPLAY return reply=" + cbmsg.reply + "--info="+cbmsg.info);

//                    if (cbmsg.reply == 0) {//成功
//                        flag = 1;
//                       //ToastUtils.showShortMessage("pos 操作成功");
//                    } else if (cbmsg.reply == 1) {
//                        flag = 2;
//                    }
                    break;

            }
        }
    };
    //支付接口的返回处理
    private ILfListener mILfMsgHandler = new ILfListener() {

        @Override
        public void onCallback(Message msg) {
            mmHandler.sendMessage(msg);
        }
    };


    /**
     * 是否使用同步接口，切换时设置一次即可
     * @param v
     */
    public void setUseSynch(boolean v){
        mMyApi.setUseSynch(v);
    }

    /**
     * 是否使用同步方法
     * @return
     */
    public boolean isUseSynch(){
        return mMyApi.isUseSynch();
    }
//    public E_REQ_RETURN pos_init(){
//
//        App app = (App)App.getContext();
//        String path = app.getMinipos_path();
//        int baudrate = app.getMinipos_baudrate();
//
//			/* Check parameters */
//        if ( (path.length() == 0) || (baudrate == -1)) {
//            ToastUtils.showError("baudarete is error ：" + baudrate, App.getContext());
//            // throw new InvalidParameterException();
//        }
//        //设置串口接口
//        mMyApi.setPOSISerialPort(null);//null时使用android的串口jni，android_serialport_api.SerialPort
//        //设置透传ip、端口；POS的串口路径和波特率
//
//        return mMyApi.pos_init("113.108.182.4", 10061,
//                path, String.valueOf(baudrate));//"/dev/ttyS1"//lf
//    }
    public E_REQ_RETURN pos_init(String path, int port){
        //设置串口接口
        mMyApi.setPOSISerialPort(null);//null时使用android的串口jni，android_serialport_api.SerialPort
        //设置透传ip、端口；POS的串口路径和波特率
        return mMyApi.pos_init("113.108.182.4", 10061, path, String.valueOf(port));//"/dev/ttyS1"//lf
    }
    /**
     * 签到
     * @return
     */
    public E_REQ_RETURN pos_signin(){
        return mMyApi.pos_signin();
    }


    /**
     * 释放接口
     * @return
     */
    public E_REQ_RETURN pos_release(){
        return mMyApi.pos_release();
    }

}
