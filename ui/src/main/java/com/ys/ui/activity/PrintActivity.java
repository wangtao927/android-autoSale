package com.ys.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ys.BytesUtil;


import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.base.BaseActivity;
import com.ys.ui.utils.StringUtils;
import com.ys.ui.utils.ToastUtils;

import java.util.LinkedList;
import java.util.Queue;

import butterknife.Bind;

/**
 * Created by wangtao on 2016/3/31.
 */
public class PrintActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.et_input)
    EditText etInput;

    @Bind(R.id.et_out)
    EditText etOut;

    @Bind(R.id.btn_send)
    Button btnSend;
    int iRecLines = 0;

    //DispQueueThread dispQueueThread;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_print;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
        btnSend.setOnClickListener(this);
//        dispQueueThread = new DispQueueThread();
//        // 启动异步线程  去打印数据
//        dispQueueThread.start();
    }

    @Override
    public void onClick(View v) {

//        switch (v.getId()) {
//            case R.id.btn_send:
//                print();
//                break;
//        }
    }

//    private class DispQueueThread extends Thread {
//
//        private Queue<ComBean> QueueList = new LinkedList();
//
//        private DispQueueThread() {
//        }
//
//        public void addQueue(ComBean paramComBean) {
//            this.QueueList.add(paramComBean);
//
//        }
//
//        public void run() {
//            super.run();
//            while (true) {
//                if (isInterrupted())
//                    return;
//                final ComBean localComBean = this.QueueList.poll();
//                if (localComBean == null)
//                    continue;
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        dispRecData(localComBean);
//                    }
//                });
//                try {
//                    Thread.sleep(100);
//                } catch (Exception localException) {
//                    localException.printStackTrace();
//                }
//            }
//        }
//    }
//
//    private void dispRecData(ComBean paramComBean) {
//        StringBuilder localStringBuilder = new StringBuilder();
//        localStringBuilder.append(paramComBean.sRecTime);
//        localStringBuilder.append("[");
//        localStringBuilder.append(paramComBean.sComPort);
//        localStringBuilder.append("]");
//
//        while (true) {
//            localStringBuilder.append("\r\n");
//            etOut.append(localStringBuilder);
//            this.iRecLines = (1 + this.iRecLines);
//            if ((this.iRecLines > 500)) {
//                etOut.setText("");
//                this.iRecLines = 0;
//                return;
//            }
//            localStringBuilder.append("[Hex] ");
//            localStringBuilder.append(BytesUtil.bytesToHexString(paramComBean.bRec));
//        }
//    }
//
//    private void print() {
//
//        try {
//            byte[] bytes = null;
//            if (!TextUtils.isEmpty(etInput.getText())) {
//                bytes = BytesUtil.hexStringToBytes(etInput.getText().toString());
//
//            } else {
//                bytes = BytesUtil.hexStringToBytes("20202020202020202020200D0A2020202020202020202020202020B2A9B0AED7D4B6AFCADBBBF5BBFA0D0A0D0A2020202020C9CCC6B7C7E5B5A5A3BA0D0A2020202020C9CCC6B7C3FBB3C6A3BA0D0A2020202020C9CCC6B7BCDBB8F1A3BA0D0A2020202020B9BAC2F2CAB1BCE4A3BA0D0A202020202020D6A7B8B6B7BDCABDA3BA0D0A202020200D0ABBB6D3ADCFC2B4CEB9E2C1D9A3A1");
////               byte[] bytes1 = {
////                        0x20, 0x20, 0x20, 0xB2, 0xA9, 0xB0,0xAE, 0xD7, 0xD4, 0xB6,
////                        0xAF, 0xCA,0xDB, 0xBB, 0xF5, 0xBB, 0xFA, 0x0D, 0x0A, 0x20, 0x20,
////                        0xC9, 0xCC, 0xC6, 0xB7, 0xC7, 0xE5, 0xB5, 0xA5, 0xA3,0xBA, 0x0D,
////                        0x0A, 0x20, 0x20, 0x20, 0x20, 0x20, 0xC9, 0xCC, 0xC6,0xB7, 0xC3,
////                        0xFB, 0xB3, 0xC6, 0xA3, 0xBA, 0x0D, 0x0A, 0x20, 0x20,0x20,
////                        0x20, 20, 0xC9, 0xCC, 0xC6, 0xB7, 0xBC, 0xDB, 0xB8, 0xF1,
////                        0xA3, 0xBA, 0x0D, 0x0A, 0x20, 0x20, 0x20, 0x20, 0x20, 0xB9, 0xBA, 0xC2,
////                        0xF2, 0xCA, 0xB1, 0xBC, 0xE4, 0xA3, 0xBA, 0x0D,0x0A, 0x20, 0x20, 0x20, 0x20,
////                        0x20, 0x20, 0xD6, 0xA7, 0xB8, 0xB6, 0xB7, 0xBD, 0xCA, 0xBD, 0xA3,
////                        0xBA, 0x0D, 0x0A, 0x20, 0x20, 0x20, 0x20, 0x0D, 0x0A, 0xBB, 0xB6, 0xD3,
////                        0xAD, 0xCF, 0xC2, 0xB4, 0xCE, 0xB9, 0xE2, 0xC1, 0xD9, 0xA3, 0xA1};
//                //ToastUtils.showShortMessage("请输入请求内容",App.getContext());
//            }
//            SerialPortUtil util = SerialPortUtil.getInstance();
//            util.setOnDataReceiveListener(new SerialPortUtil.OnDataReceiveListener() {
//                @Override
//                public void onDataReceive(byte[] buffer, int size) {
//                    dispQueueThread.addQueue(new ComBean("/dev/ttyS1", buffer, size));
//                }
//            });
//            boolean result = util.sendBuffer(BytesUtil.hexStringToBytes("1B40"));
//           result  = util.sendBuffer(bytes);
//
//        } catch (Exception e) {
//            ToastUtils.showShortMessage("报错了："+ e.getMessage());
//        }
//
//
//    }
}
