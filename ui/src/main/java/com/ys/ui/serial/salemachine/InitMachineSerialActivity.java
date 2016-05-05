package com.ys.ui.serial.salemachine;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.ys.ui.R;
import com.ys.ui.utils.ToastUtils;

import java.io.IOException;

/**
 * Created by wangtao on 2016/5/5.
 */
public class InitMachineSerialActivity extends SerialMachineActivity {
    SendingThread mSendingThread;
    byte[] mBuffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        // 初始化 售货机串口

        try {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.select_goods);


        } catch (Exception e) {
            ToastUtils.showError(e.getMessage(), this);
            return;
        }
        //  打开串口
    }

    @Override
    protected void onDataReceived(byte[] buffer) {

    }

    private class SendingThread extends Thread {
        @Override
        public void run() {
//			while (!isInterrupted()) {
            try {
                if (mOutputStream != null) {
                    mOutputStream.write(mBuffer, 0, mBuffer.length);
                } else {
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
//			}
        }
    }
}
