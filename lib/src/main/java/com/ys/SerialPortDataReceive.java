package com.ys;

import android.util.Log;

/**
 * Created by wangtao on 2016/3/13.
 */
public class SerialPortDataReceive implements SerialPortUtil.OnDataReceiveListener {

    private RobotEvent robotEvent = RobotEvent.getInstance();

    private BufferData bufferData;


    @Override
    public void onDataReceive(byte[] buffer, int size) {
        // 是正确的返回结果
        bufferData =  new BufferData();
        bufferData.add(buffer);
        if (bufferData.match((byte) 0x02, (byte) 0x03)) {
            // 展示返回数据

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
                        default: this.goodsOutState(buff[3], 4); break;               // 提取错误 MSG
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
                            this.goodsOutState(buff[2], 7);
                            break;
                    }
                    break;
            
        
    
            }


        }
        Log.d("result :", buffer.toString());
    }




    private void goodsOutState(byte flag, int pros)
    {
        switch (flag)
        {
            case 0x05: robotEvent.handle(null, new RobotEventArg(2, pros, "Y轴上方MS 感应器 未感知")); break; // Y축 상단 MS센서 미감지
            case 0x06: robotEvent.handle(null, new RobotEventArg(2, pros, "Y轴下方MS 感应器 未感知")); break; // Y축 하단 MS센서 미감지
            case 0x07: robotEvent.handle(null, new RobotEventArg(2, pros, "HOME   MS 感应器 未感知")); break; // HOME     MS센서 미감지
            case 0x09: robotEvent.handle(null, new RobotEventArg(2, pros, "Door open 感应器 未感知")); break; // Door Open  Sensor Error
            case 0x0A: robotEvent.handle(null, new RobotEventArg(2, pros, "Door Close感应器 未感知")); break; // Door Close Sensor Error
            case 0x0E: robotEvent.handle(null, new RobotEventArg(2, pros, "运行中 机械臂感应器 感知了物体")); break; // 바구니센서 감지[동작중 사고]
            case 0x10: robotEvent.handle(null, new RobotEventArg(2, pros, "X轴 左侧 MS感应器 未感知")); break; // X축 좌측 MS센서 미감지
            case 0x11: robotEvent.handle(null, new RobotEventArg(2, pros, "X轴 右侧 MS感应器 未感知")); break; // X축 우측 MS센서 미감지
            case 0x12: robotEvent.handle(null, new RobotEventArg(2, pros, "X축 B동 Start센서 미감지")); break;
            case 0x15: robotEvent.handle(null, new RobotEventArg(2, pros, "机械臂马达 前方感应器 未感知")); break; // 바구니 모터 앞센서 미감지
            case 0x16: robotEvent.handle(null, new RobotEventArg(2, pros, "机械臂马达 后方感应器 未感知")); break; // 바구니 모터 뒤센서 미감지
            case 0x18: robotEvent.handle(null, new RobotEventArg(2, pros, "商品 未收取")); break; //  상품 미수거 발생(1분이상 미수거시)
        }
    }



    private void displayBuffer(byte[] bytes) {

       // Log.d(BytesUtil.bytesToHexString(bytes));
    }

}
