package com.ys.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ys.BufferData;
import com.ys.BytesUtil;
import com.ys.GetBytesUtils;
import com.ys.RobotEvent;
import com.ys.RobotEventArg;
import com.ys.ui.R;
import com.ys.ui.common.constants.SlOutStatusEnum;
import com.ys.ui.common.manager.DbManagerHelper;
import com.ys.ui.sample.SerialPortActivity;
import com.ys.ui.utils.PropertyUtils;
import com.ys.ui.utils.ToastUtils;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by wangtao on 2016/4/18.
 */
public class OutGoodsActivity extends SerialPortActivity {

    private RobotEvent robotEvent = RobotEvent.getInstance();


    private BufferData bufferData;
    SendingThread mSendingThread;

    byte[] mBuffer;
    protected int baudrate  = 19200;

    protected String path = "/dev/ttyES1";

    TextView transStatus;
     ContentLoadingProgressBar mPbLoading;

    // 定义一个queue, 往queue
    private Queue<RobotEventArg> queue = new LinkedBlockingQueue<>();
    private long startTime = 0L;

    private String channo = "";
    private String slNo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.out_goods_main);
        transStatus = (TextView) findViewById(R.id.tranStatus);
        mPbLoading = (ContentLoadingProgressBar)findViewById(R.id.pb_loading);
        Bundle datas = getIntent().getExtras();
        slNo = datas.getString("slNo");
        channo = datas.getString("channo");
        // 发送选货请求
        byte b = Byte.parseByte(channo);
        mBuffer = GetBytesUtils.goodsSelect(b);
        ToastUtils.showShortMessage("sendData:" + mBuffer);

        startTime = System.currentTimeMillis();

        mSendingThread = new SendingThread();
        mSendingThread.start();
        showProgress();
        this.consumer();
    }

    @Override
    protected  void onDataReceived(final byte[] buffer)  {

        runOnUiThread(new Runnable() {
            public void run() {
                // 是正确的返回结果
                ToastUtils.showShortMessage("resultData:" + BytesUtil.bytes2Hex(buffer));

                bufferData = new BufferData();
                bufferData.add(buffer);
                if (bufferData.match((byte) 0x02, (byte) 0x03)) {
                    // 记录返回数据
                    byte[] buff = bufferData.getMatchedBytes();
                    switch (buff[1]) {
                        case 0x09:  // ---检查当前的状态。
                            switch (buff[2]) {
                                case 0x00:
                                    queue.add(new RobotEventArg(3, 0, ""));
                                    break; // 正常
                                case 0x13:
                                    queue.add(new RobotEventArg(3, 1, "扫描中"));
                                    break;
                                case 0x14:
                                    queue.add(new RobotEventArg(3, 1, "扫描完毕"));
                                    break;
                                case 0x01:
                                    queue.add(new RobotEventArg(3, 1, "扫描中 Time Over 发生错误"));
                                    break;
                                case 0x0F:
                                    queue.add(new RobotEventArg(3, 1, "扫描中 ERROR"));
                                    break;
                                case 0x0C:
                                    queue.add(new RobotEventArg(3, 2, "TEST MODE 1"));
                                    break;
                                case 0x0D:
                                    queue.add(new RobotEventArg(3, 2, "TEST MODE 2"));
                                    break;
                                case 0x20:
                                    queue.add(new RobotEventArg(3, 3, "硬币机制 发生错误"));
                                    break;
                                case 0x21:
                                    queue.add(new RobotEventArg(3, 4, "纸币被卡到 发生错误"));
                                    break;
                                case 0x22:
                                    queue.add(new RobotEventArg(3, 4, "纸币感应器 发生问题"));
                                    break;
                                case 0x23:
                                    queue.add(new RobotEventArg(3, 4, "纸币马达 发生问题"));
                                    break;
                                case 0x24:
                                    queue.add(new RobotEventArg(3, 4, "纸币ROM 发生问题"));
                                    break;
                            }
                            break;
                        case 0x26: // --缺货确认
                            String goodsNo = BytesUtil.byteToHexString(buffer[2]);

                            queue.add(new RobotEventArg(4, 0, goodsNo));
                            break;
                        case 0x00: // 待机------------------------------------------------------------------ RESET
                            switch (buff[3]) {
                                case 0x00:
                                    break; //  [ACK]
                                case (byte) 0xFF:
                                    break; //  [NAK]
                                case (byte) 0xAA:
                                    //this.RESET();
                                    break;
                            }
                            break;
                        case 0x0E: // ------------------------------------------------------------------------------- 提货
                            if (buff[2] == 0xF2) buff[2] = 0x02;
                            if (buff[2] == 0xF3) buff[2] = 0x03;
                            switch (buff[3]) {
                                case 0x00:
                                    //queue.add(new RobotEventArg(2, 2, Integer.valueOf(buff[2]).toString()));
                                    break; // 提取中
                                case 0x55:
                                    //queue.add(new RobotEventArg(2, 3, Integer.valueOf(buff[2]).toString()));
                                    // 选货结束  发送出货命令
                                    mBuffer = GetBytesUtils.goodsOuter();

                                    SendingThread mSendingThread = new SendingThread();
                                    mSendingThread.start();
                                    break; // 提取完毕
                                case (byte) 0xFF:
                                    queue.add(new RobotEventArg(2, 4, Integer.valueOf(buff[2]).toString()));
                                    break; // 提取失败
                                case (byte) 0xEE:
                                    queue.add(new RobotEventArg(2, 4, "提取错误"));
                                    break; // 提取错误
                            }
                            break;
                        case 0x08: // 商品出货
                            switch (buff[2]) {
                                case 0x00:
                                    // 出货中    等待
                                    queue.add(new RobotEventArg(2, 5, "排放中"));
                                    break;
                                case 0x55:
                                    // 出货完成    结束出货流程
                                    //queue.add(new RobotEventArg(2, 6, "排放完毕"));
                                    // 出货成功  结束
                                    outGoodsSuc();
                                    transStatus.setText("出货完成");
                                    ToastUtils.showShortMessage("交易成功");
                                    break;
                                case (byte) 0xFF:
                                    // 出货失败，结束出货流程
                                    // 出货失败：
                                    queue.add(new RobotEventArg(2, 7, "排放失败"));
                                    break;
                                case (byte) 0xEE:
                                    // 出货错误

                                    queue.add(new RobotEventArg(2, 7, "排放错误"));
                                    break;
                                default:
                                    break;
                            }
                            break;


                    }
                }
                Log.d("result :", buffer.toString());
            }


        });
    }
    public void consumer() {

        while (true) {
            if (!queue.isEmpty()) {
                RobotEventArg robotEvent = queue.poll();
                if (robotEvent.getiCode() == 2) {
                     if (robotEvent.getiMsgCode() == 5 || robotEvent.getiMsgCode() == 2) {
                         continue;
                     } else if (robotEvent.getiMsgCode() == 3) {
                         // 选货结束  发送出货命令
                         mBuffer = GetBytesUtils.goodsOuter();

                         SendingThread mSendingThread = new SendingThread();
                         mSendingThread.start();
                     } else if (robotEvent.getiMsgCode() == 6) {
                        // 出货成功  结束
                        outGoodsSuc();
                        //startActivity(new Intent(OutGoodsActivity.this, MainActivity.class));
                        break;
                     } else {
                         // 出货失败  结束
                         outGoodsFail();
                         break;
                     }
                }
            }
            if ((System.currentTimeMillis() - startTime) > PropertyUtils.getInstance().getTransTimeout()*1000) {
                //出货超时
                ToastUtils.showShortMessage("交易超时");
                hideProgress();
                break;
            }
        }
    }

    private void outGoodsSuc() {
        // 支付成功才会 走到出货
        DbManagerHelper.updateOutStatus(slNo, SlOutStatusEnum.FINISH);
        hideProgress();
        transStatus.setText("出货完成");
        ToastUtils.showShortMessage("交易成功, 欢迎下次光临");
    }

    private void outGoodsFail() {
        // 出货失败， 考虑退款
        DbManagerHelper.updateOutStatus(slNo, SlOutStatusEnum.FAIL);
        hideProgress();
        transStatus.setText("出货失败");
        ToastUtils.showShortMessage("交易失败");
    }

    private class SendingThread extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
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
            }
        }
    }
    public void showProgress() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        mPbLoading.setVisibility(View.GONE);
    }

}
