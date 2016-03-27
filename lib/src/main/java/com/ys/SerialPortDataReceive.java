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
             //this.displayBuffer(buffer);

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
                case 0x1A: // ---------------------------------------------------------------------------------- 코인/빌 입수금지
//                    switch (buff[2])
//                    {
//                        case 0x00: break;
//                        case 0xAA: this.COIN_INHIBIT(0); break; // 재전송
//                        case 0xEE: handler(null, new RobotEventArg(1, 9, "硬币机制 发生错误")); break;
//                    }
                    break;
                case 0x20: // ---------------------------------------------------------------------------------- 지폐 입수금지
//                    switch (buff[2])
//                    {
//                        case 0x00: break;
//                        case 0xAA: this.BILL_INHIBIT(0); break; // 재전송
//                        case 0xEE: handler(null, new RobotEventArg(1, 10, "纸币 ERROR")); break;
//                    }
                    break;
                case 0x1B: // ---------------------------------------------------------------------------------- 코인/빌 입수허가
//                    switch (buff[2])
//                    {
//                        case 0x00: break; // 정상수신[ACK]
//                        case 0xAA: this.COIN_INHIBIT(1); break;
//                        case 0xEE: handler(null, new RobotEventArg(1, 9, "코인 입수허가 ERROR")); break;
//                    }
                    break;
                case 0x21: // ---------------------------------------------------------------------------------- 지폐 입수허가
//                    switch (buff[2])
//                    {
//                        case 0x00: break; // 정상수신[ACK]
//                        case 0xAA: this.BILL_INHIBIT(1); break;
//                        case 0xEE: handler(null, new RobotEventArg(1, 10, "纸币 ERROR")); break;
//                    }
                    break;
                case 0x30: // ---------------------------------------------------------------------------------- 코인/빌 입수대기
//                    switch (buff[4])
//                    {
//                        case 0x00:
//                            switch (buff[2])
//                            {
//                                case 0x50:
//                                    int coinY = Convert.ToInt32(buff[3] - 0x80);
//                                    handler(null, new RobotEventArg(1, 1, coinY.ToString())); break;
//                                case 0x51:
//                                    int coinU = Convert.ToInt32(buff[3] - 0x80);
//                                    handler(null, new RobotEventArg(1, 2, coinU.ToString())); break;
//                                case 0x80: this.BillCount = Convert.ToInt32(buff[3] - 0x80) *  1; this.CASH_STACKBILL(0x23); break;
//                                case 0x81: this.BillCount = Convert.ToInt32(buff[3] - 0x80) *  5; this.CASH_STACKBILL(0x23); break;
//                                case 0x82: this.BillCount = Convert.ToInt32(buff[3] - 0x80) * 10; this.CASH_STACKBILL(0x23); break;
//                                case 0x83: this.BillCount = Convert.ToInt32(buff[3] - 0x80) * 20; this.CASH_STACKBILL(0x23); break;
//                            }
//                            break;
//                        case 0xEE:
//                            switch (buff[2]) // COINL ERR
//                            {
//                                case 0x80: break;
//                                default: handler(null, new RobotEventArg(1, 9, "COIN ERROR")); break;
//                            }
//                            switch (buff[3]) // BILL ERR
//                            {
//                                case 0x80: break;
//                                case 0x81: handler(null, new RobotEventArg(1, 10, "Bill Motor Failure")); break;
//                                case 0x82: handler(null, new RobotEventArg(1, 10, "Bill Sensor Problem")); break;
//                                case 0x83: handler(null, new RobotEventArg(1, 10, "Bill is busy")); break;
//                                case 0x84: handler(null, new RobotEventArg(1, 10, "Bill CheckSum Error")); break;
//                                case 0x85: handler(null, new RobotEventArg(1, 10, "Bill Jam")); break;
//                                case 0x86: handler(null, new RobotEventArg(1, 10, "Bill Reset")); break;
//                                case 0x87: handler(null, new RobotEventArg(1, 10, "Bill Remove")); break;
//                                case 0x88: handler(null, new RobotEventArg(1, 10, "Bill Box out of position")); break;
//                                case 0x8B: handler(null, new RobotEventArg(1, 10, "Bill Reject")); break;
//                                default:   handler(null, new RobotEventArg(1, 10, "Bill ERROR")); break;
//                            }
//                            break;
//                        case 0xFF: handler(null, new RobotEventArg(1, 11, "통신이상 ERROR")); break;
//                    }
//                    break;
                case 0x23: // ----------------------------------------------------------------------------------- 지폐 입수 신호
//                    switch (buff[2])
//                    {
//                        case 0x00: handler(null, new RobotEventArg(1, 3, this.BillCount.ToString())); this.BillCount = 0; break;
//                        case 0xAA: this.CASH_STACKBILL(0x23); break; // 재전송
//                        case 0xFF: handler(null, new RobotEventArg(1, 10, "통신이상 ERROR")); break;
//                        case 0xEE: handler(null, new RobotEventArg(1, 10, "지폐 입수 ERROR")); break;
//                    }
                    break;
                case 0x22: // ----------------------------------------------------------------------------------- 지폐 반환 신호
//                    switch (buff[2])
//                    {
//                        case 0x00: this.BillCount = 0; handler(null, new RobotEventArg(1, 4, "0")); break;
//                        case 0xAA: this.CASH_STACKBILL(0x22); break; // 재전송
//                        case 0xFF: handler(null, new RobotEventArg(1, 10, "통신이상 ERROR")); break;
//                        case 0xEE: handler(null, new RobotEventArg(1, 10, "지폐 반환 ERROR")); break;
//                    }
                    break;
                case 0x1C: // -零钱排出--------------------------------------------------------------------- 거스름 배출 확인(실행)
//                    switch (buff[4])
//                    {
//                        case 0x00: handler(null, new RobotEventArg(1, 5, "COIN OUTING....,")); break; // 반환중
//                        case 0x55: // 반환 종료=반환금 확인
//                            int COIN_U = Convert.ToInt32(buff[2] - 0x80), COIN_Y = Convert.ToInt32(buff[3] - 0x80);
//                            double COIN = (COIN_U * 0.5) + (COIN_Y * 1.0);
//                            handler(null, new RobotEventArg(1, 6, COIN.ToString())); break;
//                        case 0xEE: handler(null, new RobotEventArg(1, 7, "ERROR....,")); break;
//                        case 0xFF: handler(null, new RobotEventArg(1, 7, "ERROR")); break;
//                    }
                    break;
                case 0x1E: // 硬币或纸币退还----------------------------------------------------------- 코인반환버튼작동
//                    switch (buff[2])
//                    {
//                        case 0x00: this.CASH_COIN_OUT(this.form.CASH_InMoney[0]); break;
//                        case 0xAA: this.CASH_COIN_OUT(this.form.CASH_InMoney[0]); break;
//                        case 0xFF: handler(null, new RobotEventArg(1, 9, "통신이상 ERROR")); break;
//                        case 0xEE: handler(null, new RobotEventArg(1, 9, "코인반환버튼 ERROR")); break;
//                    }
                    break;
                case 0x1D: // -硬币现余额要求----------------------------------------------------- 거스름 가능매수 확인
//                    switch (buff[4])
//                    {
//                        case 0x00:
//                            int[] OutEnable = { 0, 0 };
//                            OutEnable[0] = Convert.ToInt32(buff[2] - 0x80); // 0.5
//                            OutEnable[1] = Convert.ToInt32(buff[3] - 0x80); // 1
//                            handler(null, new RobotEventArg(1, 8, OutEnable)); break;
//                        case 0xAA: this.CASH_COIN_TUBE(); break;
//                        case 0xFF: handler(null, new RobotEventArg(1, 9, "통신이상 ERROR")); break;
//                        case 0xEE: handler(null, new RobotEventArg(1, 9, "거스름 가능매수 ERROR")); break;
//                    }
                    break;
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
                case 0x0E: // ------------------------------------------------------------------------------- 제품추출(바구니에담기)
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
                        case 0x00: robotEvent.handle(null, new RobotEventArg(2, 5, "排放中")); break;
                        case 0x55: robotEvent.handle(null, new RobotEventArg(2, 6, "排放完毕")); break;
                        case (byte)0xFF: robotEvent.handle(null, new RobotEventArg(2, 7, "排放失败")); break;
                        case (byte)0xEE: robotEvent.handle(null, new RobotEventArg(2, 7, "排放错误")); break;
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
