package com.ys.ui.activity;

import android.util.Log;

import com.ys.BufferData;
import com.ys.BytesUtil;
import com.ys.RobotEvent;
import com.ys.RobotEventArg;
import com.ys.ui.sample.SerialPortActivity;

/**
 * Created by wangtao on 2016/4/18.
 */
public class OutGoodsActivity extends SerialPortActivity {

    private RobotEvent robotEvent = RobotEvent.getInstance();

    private BufferData bufferData;

    @Override
    protected void onDataReceived(final byte[] buffer, int size) {
        runOnUiThread(new Runnable() {
            public void run() {
                // 是正确的返回结果
                bufferData =  new BufferData();
                bufferData.add(buffer);
                if (bufferData.match((byte) 0x02, (byte) 0x03)) {
                    // 记录返回数据

                    byte[] buff = bufferData.getMatchedBytes();
                    switch (buff[1]) {
                        case 0x09:  // ---检查当前的状态。
                            switch (buff[2])
                            {
                                case 0x00: robotEvent.handle(null, new RobotEventArg(3, 0, "")); break; // 正常
                                case 0x13: robotEvent.handle(null, new RobotEventArg(3, 1, "扫描中")); break; // 스캔중
                                case 0x14: robotEvent.handle(null, new RobotEventArg(3, 1, "扫描完毕")); break; // 스캔완료
                                case 0x01: robotEvent.handle(null, new RobotEventArg(3, 1, "扫描中 Time Over 发生错误")); break; // 스캔중 타임오버
                                case 0x0F: robotEvent.handle(null, new RobotEventArg(3, 1, "扫描中 ERROR")); break; // 스캔중 에러
                                case 0x0C: robotEvent.handle(null, new RobotEventArg(3, 2, "TEST MODE 1")); break;
                                case 0x0D: robotEvent.handle(null, new RobotEventArg(3, 2, "TEST MODE 2")); break;
                                case 0x20: robotEvent.handle(null, new RobotEventArg(3, 3, "硬币机制 发生错误")); break; // 코인메카 에러
                                case 0x21: robotEvent.handle(null, new RobotEventArg(3, 4, "纸币被卡到 发生错误")); break; // 지폐걸림
                                case 0x22: robotEvent.handle(null, new RobotEventArg(3, 4, "纸币感应器 发生问题")); break; // 지폐센서에러
                                case 0x23: robotEvent.handle(null, new RobotEventArg(3, 4, "纸币马达 发生问题")); break; // 지폐모터에러
                                case 0x24: robotEvent.handle(null, new RobotEventArg(3, 4, "纸币ROM 发生问题")); break; // 지폐ROM에러
                            }
                            break;
                        case 0x26: // --缺货确认
                            String goodsNo = BytesUtil.byteToHexString(buffer[2]);

                            robotEvent.handle(null, new RobotEventArg(4, 0, goodsNo)); break;
                        case 0x00: // 待机------------------------------------------------------------------ RESET
                            switch (buff[3])
                            {
                                case 0x00: break; // 정상수신[ACK]
                                case (byte)0xFF: break; // 수신에러[NAK]
                                case (byte)0xAA:
                                    //this.RESET();
                                    break;
                            }
                            break;
                        case 0x0E: // ------------------------------------------------------------------------------- 提货
                            if (buff[2] == 0xF2) buff[2] = 0x02;
                            if (buff[2] == 0xF3) buff[2] = 0x03;
                            switch (buff[3])
                            {
                                case 0x00: robotEvent.handle(null, new RobotEventArg(2, 2, Integer.valueOf(buff[2]).toString())); break; // 提取中
                                case 0x55: robotEvent.handle(null, new RobotEventArg(2, 3, Integer.valueOf(buff[2]).toString())); break; // 提取完毕
                                case (byte)0xFF: robotEvent.handle(null, new RobotEventArg(2, 4, Integer.valueOf(buff[2]).toString())); break; // 提取失败
                                case (byte)0xEE: robotEvent.handle(null, new RobotEventArg(2, 4, "提取错误")); break; // 提取错误
                             }
                            break;
                        case 0x08: // 商品出货
                            switch (buff[2])
                            {
                                case 0x00:
                                    robotEvent.handle(null, new RobotEventArg(2, 5, "排放中")); break;
                                case 0x55:
                                    robotEvent.handle(null, new RobotEventArg(2, 6, "排放完毕")); break;
                                case (byte)0xFF:
                                    // 出货失败：
                                    robotEvent.handle(null, new RobotEventArg(2, 7, "排放失败")); break;
                                case (byte)0xEE:
                                    // 出货错误

                                    robotEvent.handle(null, new RobotEventArg(2, 7, "排放错误")); break;
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





    private void displayBuffer(byte[] bytes) {

        // Log.d(BytesUtil.bytesToHexString(bytes));
    }
}
