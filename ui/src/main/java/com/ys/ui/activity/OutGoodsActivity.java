package com.ys.ui.activity;

import android.os.Bundle;
import android.util.Log;

import com.ys.BufferData;
import com.ys.BytesUtil;
import com.ys.GetBytesUtils;
import com.ys.RobotEvent;
import com.ys.RobotEventArg;
import com.ys.ui.sample.SerialPortActivity;
import com.ys.ui.utils.PropertyUtils;

import java.io.IOException;
import java.util.LinkedList;
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

    // 定义一个queue, 往queue
    private Queue<RobotEventArg> queue = new LinkedBlockingQueue<>();
    private long startTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startTime = System.currentTimeMillis();

        // 发送选货请求
        byte b = 11;
        mBuffer = GetBytesUtils.goodsSelect(b);
        SendingThread mSendingThread = new SendingThread();
        mSendingThread.start();
        this.consumer();
    }

    @Override
    protected void onDataReceived(final byte[] buffer, int size) {

        runOnUiThread(new Runnable() {
            public void run() {
                // 是正确的返回结果
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
                                    break; // 스캔중
                                case 0x14:
                                    queue.add(new RobotEventArg(3, 1, "扫描完毕"));
                                    break; // 스캔완료
                                case 0x01:
                                    queue.add(new RobotEventArg(3, 1, "扫描中 Time Over 发生错误"));
                                    break; // 스캔중 타임오버
                                case 0x0F:
                                    queue.add(new RobotEventArg(3, 1, "扫描中 ERROR"));
                                    break; // 스캔중 에러
                                case 0x0C:
                                    queue.add(new RobotEventArg(3, 2, "TEST MODE 1"));
                                    break;
                                case 0x0D:
                                    queue.add(new RobotEventArg(3, 2, "TEST MODE 2"));
                                    break;
                                case 0x20:
                                    queue.add(new RobotEventArg(3, 3, "硬币机制 发生错误"));
                                    break; // 코인메카 에러
                                case 0x21:
                                    queue.add(new RobotEventArg(3, 4, "纸币被卡到 发生错误"));
                                    break; // 지폐걸림
                                case 0x22:
                                    queue.add(new RobotEventArg(3, 4, "纸币感应器 发生问题"));
                                    break; // 지폐센서에러
                                case 0x23:
                                    queue.add(new RobotEventArg(3, 4, "纸币马达 发生问题"));
                                    break; // 지폐모터에러
                                case 0x24:
                                    queue.add(new RobotEventArg(3, 4, "纸币ROM 发生问题"));
                                    break; // 지폐ROM에러
                            }
                            break;
                        case 0x26: // --缺货确认
                            String goodsNo = BytesUtil.byteToHexString(buffer[2]);

                            queue.add(new RobotEventArg(4, 0, goodsNo));
                            break;
                        case 0x00: // 待机------------------------------------------------------------------ RESET
                            switch (buff[3]) {
                                case 0x00:
                                    break; // 정상수신[ACK]
                                case (byte) 0xFF:
                                    break; // 수신에러[NAK]
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
                                    queue.add(new RobotEventArg(2, 2, Integer.valueOf(buff[2]).toString()));
                                    break; // 提取中
                                case 0x55:
                                    queue.add(new RobotEventArg(2, 3, Integer.valueOf(buff[2]).toString()));
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
                                    queue.add(new RobotEventArg(2, 6, "排放完毕"));
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
                        // 出货完成  结束

                     } else {
                         // 出货失败  结束

                     }

                }

            }
            if ((System.currentTimeMillis() - startTime) > PropertyUtils.getInstance().getTransTimeout()*1000) {
                //出货超时
                break;
            }
        }
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

}