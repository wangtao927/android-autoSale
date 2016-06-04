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
    App mApplication;
    private Class<?> serviceClass = MyService.class;
    static PosSerialHelper posSerialHelper;
    Context context;
    private boolean initFlag = false;

    public PosSerialHelper() {
        context =  App.getContext();
        ConnectService_whenOncreate();
        BindLfService();
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
            initFlag = true;
            SerialPortFinder mSerialPortFinder = new SerialPortFinder();
            String[] paths = mSerialPortFinder.getAllDevicesPath();
            mApplication = (App) App.getContext();
            for (String path : paths) {
                flag = 0;
                if (TextUtils.isEmpty(path) || path.contains("USB")) {
                    continue;
                }
                tmpPath = path;
                try {
                    if (posInit() && posSign()) {
                        ToastUtils.showShortMessage("串口连接成功-path=" + path);
                         break;
                    } else {
                        if (myBinder != null) {
                            myBinder.pos_release();
                        }
                    }
                } catch (Exception e) {
                    flag = 0;
                    tmpPath = "";
                    if (myBinder != null) {
                        myBinder.pos_release();
                    }
                    ToastUtils.showError("exception path=" + path + "ex=" + e, App.getContext());
                    continue;
                }
            }
        } catch (Exception e) {
            ToastUtils.showError("exception "  + e, App.getContext());
        }

    }

    private void ConnectService_whenOncreate() {
        mSc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "service connected");
                Log.d("", "startDownLoad() --> onbind");
                myBinder = (MyService.MyBinder) service;
                ToastUtils.showShortMessage("set callback handler");
                //回调
                myBinder.setILfListener(mILfMsgHandler);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "service disconnected");
            }
        };
    }

    private void BindLfService(){

        Intent service = new Intent(App.getContext(),serviceClass);//LfService.class
        context.bindService(service, mSc, Context.BIND_AUTO_CREATE);
    }

    private void UnBindLfService(){
       context.unbindService(mSc);
    }
    public boolean posInit() {

        try {
            E_REQ_RETURN result = myBinder.pos_init();

            return E_REQ_RETURN.REQ_OK == result;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean posSign() {
        flag = 0;
        E_REQ_RETURN req_return = myBinder.pos_signin();
        if (E_REQ_RETURN.REQ_OK == req_return) {
            while (true) {
                if (flag == 1) {

                    break;
                } else if (flag == 2) {

                    break;
                } else {
                    try {
                        Thread.sleep(2000);
                        continue;
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }

            }
            if (flag == 1) {
                return true;
            } else {
                return false;
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
                    break;
                } else {
                    try {
                        Thread.sleep(2000);
                        continue;
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }

            }
            if (flag == 1) {
                return true;
            }
        }
        return false;
    }

    public void realese() {

        UnBindLfService();
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
                        flag = 1;
                        if (initFlag) {
                            SharedPreferences mySharedPreferences = App.getContext().getSharedPreferences("posSerial",
                                    Activity.MODE_PRIVATE);
                            //实例化SharedPreferences.Editor对象（第二步）
                            SharedPreferences.Editor editor = mySharedPreferences.edit();
                            //用putString的方法保存数据
                            editor.putString("pos_baudrate", String.valueOf(mApplication.getMinipos_baudrate()));
                            editor.putString("pos_path", tmpPath);
                            //提交当前数据
                            editor.commit();
                        }

                    } else if (cbmsg.reply == 1) {//失败
                        flag = 2;
                    }
                    break;
                case OP_POS_DISPLAY://POS提示信息
                    if (cbmsg.reply == 0) {//成功
                       ToastUtils.showShortMessage("pos 操作成功");
                    }
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


}
