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
import com.ys.ui.utils.ToastUtils;

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

    private String tmpPath;
    int flag = 0;
    public void setPath() {
        String[] paths = new String[0];
        try {
            SerialPortFinder mSerialPortFinder = new SerialPortFinder();

            paths = mSerialPortFinder.getAllDevicesPath();

            mApplication = (App) App.getContext();
        } catch (Exception e) {
            ToastUtils.showShortMessage("pos init error");
        }

            for (String path : paths) {
                flag = 0;
                tmpPath = path;
                try {
                    E_REQ_RETURN req_return =  myBinder.pos_init(path, mApplication.getMinipos_baudrate());
                    try {
                        ToastUtils.showShortMessage("posInit----path:"+path +  "--result=" + req_return.getDesc());

                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    req_return = myBinder.pos_signin();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 请求已发出
                    if (E_REQ_RETURN.REQ_OK == req_return) {
                         // 等待返回结果
                        while (true) {
                            if (1 == flag) {
                                ToastUtils.showShortMessage("串口连接成功-path=" + path);
                                break;
                            } else if (flag ==2) {

                                break;
                            } else {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    } else {
                        myBinder.pos_release();
                    }
                    if (flag == 1) {
                        break;
                    }

                } catch (Exception e) {
                    flag =0;
                    tmpPath = "";
                    ToastUtils.showShortMessage("exception path=" + path + "ex=" + e);
                    continue;
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


    public boolean posSign() {
        flag = 0;
        E_REQ_RETURN req_return = myBinder.pos_signin();
        if (E_REQ_RETURN.REQ_OK == req_return) {
            while (true) {
                if (flag == 1) {

                    return true;
                } else {
                    try {
                        Thread.sleep(2000);
                        continue;
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }

            }
        }
        return false;
    }

    public boolean purchase(long amount) {

        flag = 0;
        E_REQ_RETURN req_return = myBinder.pos_purchase((int) amount);

        if (E_REQ_RETURN.REQ_OK == req_return) {
            while (true) {
                if (flag == 1) {

                    return true;
                } else {
                    try {
                        Thread.sleep(2000);
                        continue;
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }

            }
        }
        return false;
    }


    //////////////////////////////////////////业务返回///////////////////////////////////////
    private Handler mmHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Display dpl = null;//POS提示信息显示
            CallbackMsg cbmsg = (CallbackMsg)msg.obj;
            switch(cbmsg.op_type){//信息（交易）类型
                case OP_POS_SIGNIN://签到
                case OP_POS_QUERY://查询
                case OP_POS_PURCHASE://支付
                    if(cbmsg.reply == 0){//成功
                        flag = 1;
                        SharedPreferences mySharedPreferences= App.getContext().getSharedPreferences("posSerial",
                                Activity.MODE_PRIVATE);
                        //实例化SharedPreferences.Editor对象（第二步）
                        SharedPreferences.Editor editor = mySharedPreferences.edit();
                        //用putString的方法保存数据
                        editor.putString("pos_baudrate", String.valueOf(mApplication.getMinipos_baudrate()));
                        editor.putString("pos__path", tmpPath);
                        //提交当前数据
                        editor.commit();
                    }else if(cbmsg.reply == 1){//失败
                        flag = 2;
                    }
                    break;
                case OP_POS_DISPLAY://POS提示信息
                    if(cbmsg.reply == 0){//成功
                        dpl = cbmsg.dsp;
                        if(dpl.getType()== DisplayType._key.getType()){
//                            key.setText("上报键值:"+dpl.getMsg());
                        }else if(dpl.getType()== DisplayType._card.getType()){
//                            key.setText("上报卡信息:"+dpl.getMsg());//逗号,隔开 “卡号,磁道2,磁道3,卡类型”
                        }else {
//                            lcd.setText("提示信息("+dpl.getType()+"):"+DisplayType.getDesc(dpl)+"\n" + dpl.getMsg());
                        }
                        ToastUtils.showShortMessage("pos 操作成功");
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
