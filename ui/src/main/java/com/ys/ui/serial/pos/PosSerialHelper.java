package com.ys.ui.serial.pos;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.landfoneapi.misposwa.CallbackMsg;
import com.landfoneapi.misposwa.E_REQ_RETURN;
import com.landfoneapi.misposwa.ILfListener;
import com.landfoneapi.protocol.pkg.Display;
import com.landfoneapi.protocol.pkg.DisplayType;
import com.landfoneapi.protocol.pkg._04_GetRecordReply;
import com.ys.SerialPortFinder;
import com.ys.ui.base.App;
import com.ys.ui.service.MyService;

/**
 * Created by wangtao on 2016/5/13.
 */
public class PosSerialHelper {
    private String TAG = "PosSerialHelper";

    private MyService.MyBinder myBinder;
    private ServiceConnection mSc;
    private Class<?> serviceClass = MyService.class;
    App mApplication ;

    static PosSerialHelper posSerialHelper ;

    public PosSerialHelper() {
        ConnectService_whenOncreate();
    }

    public static PosSerialHelper getInstance() {
        if (posSerialHelper == null) {
            return new PosSerialHelper();
        }
        return posSerialHelper;
    }

    public void setPath() {
        SerialPortFinder mSerialPortFinder = new SerialPortFinder();

        String[] paths = mSerialPortFinder.getAllDevicesPath();

        mApplication = (App) App.getContext();

        for (String path : paths) {

            try {
                myBinder.pos_init(path, mApplication.getMinipos_baudrate());
                E_REQ_RETURN req_return = myBinder.pos_signin();
                if (E_REQ_RETURN.REQ_OK == req_return) {
                    //  path 是对的
                    SharedPreferences mySharedPreferences= App.getContext().getSharedPreferences("posSerial",
                            Activity.MODE_PRIVATE);
                    //实例化SharedPreferences.Editor对象（第二步）
                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                    //用putString的方法保存数据
                    editor.putString("pos_baudrate", String.valueOf(mApplication.getPrint_baudrate()));
                    editor.putString("pos__path", path);
                    //提交当前数据
                    editor.commit();

                }
            } catch (Exception e) {
                break;
            }
        }
    }

    private void ConnectService_whenOncreate() {
        mSc = new ServiceConnection(){
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "service connected");
                Log.d("","startDownLoad() --> onbind");
                myBinder = (MyService.MyBinder) service;
                //回调
                myBinder.setILfListener(mILfMsgHandler);
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "service disconnected");
            }
        };
    }
    //////////////////////////////////////////业务返回///////////////////////////////////////
    private Handler mmHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Display dpl = null;//POS提示信息显示
            System.out.println("InitActivity, mILfMsgHandler incomming, msg.arg:"+msg.arg1);
            CallbackMsg cbmsg = (CallbackMsg)msg.obj;
            String tmp = "";
            switch(cbmsg.op_type){//信息（交易）类型
                case OP_POS_SIGNIN://签到
                case OP_POS_QUERY://查询
                case OP_POS_PURCHASE://支付
                    if(cbmsg.reply == 0){//成功

                    }else if(cbmsg.reply == 1){//失败

                    }
                    break;
                case OP_POS_DISPLAY://POS提示信息
                    if(cbmsg.reply == 0){//成功


                    }
                    break;

            }
        }
    };
    //支付接口的返回处理
    private ILfListener mILfMsgHandler = new ILfListener(){

        @Override
        public void onCallback(Message msg) {
            mmHandler.sendMessage(msg);
        }
    };



}
