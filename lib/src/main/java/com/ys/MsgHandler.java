package com.ys;

/**
 * Created by wangtao on 2016/3/13.
 */
public class MsgHandler {

    private SerialPortUtil serialPortUtil = SerialPortUtil.getInstance();

    public MsgHandler() {
        serialPortUtil.setOnDataReceiveListener(new SerialPortDataReceive());
    }


    public void RESET() {
        byte[] TxBuf = new byte[]{0x02, 0x00, 0x00, 0x03};
        serialPortUtil.sendBuffer(TxBuf);
    }

    public void STANDBY() {
        byte[] TxBuf = new byte[]{0x02, 0x0B, 0x0B, 0x03};
        serialPortUtil.sendBuffer(TxBuf);
    }

    public void GOODS_BUTTON_ON(byte RackNo, byte onff) {
        // 01:ON, 00:OFF
        byte[] TxBuf = new byte[]{0x02, 0x31, RackNo, onff, 0x00, 0x03};
        for (int i = 1; i < 4; i++) {
            TxBuf[4] += TxBuf[i];
        }
        TxBuf[4] |= 0x80;

        serialPortUtil.sendBuffer(TxBuf);
    }

    public void GOODS_ALLBUTTON_ON(byte onff) {
        // FF:ON, 00:OFF
        byte[] TxBuf = new byte[]{0x02, 0x32, onff, 0x00, 0x03};
        for (int i = 1; i < 3; i++) {
            TxBuf[3] += TxBuf[i];
        }
        TxBuf[3] |= 0x80;

        serialPortUtil.sendBuffer(TxBuf);

    }

    public void GOODS_SELECT(byte RackNo) {
        if (RackNo == 0x02) {
            RackNo = (byte) 0xF2;
        } else if (RackNo == 0x03) {
            RackNo = (byte) 0xF3;
        }

        byte[] TxBuf = new byte[]{0x02, 0x0E, RackNo, 0x00, 0x03};
        for (int i = 1; i < 3; i++) {
            TxBuf[3] += TxBuf[i];
        }
        TxBuf[3] |= 0x80;
        serialPortUtil.sendBuffer(TxBuf);

    }

    public void GOODS_OUTER() {

        byte[] TxBuf = new byte[]{0x02, 0x08, 0x08, 0x03};
        serialPortUtil.sendBuffer(TxBuf);

    }

//    public void CASH_POLL_START()
//    {
//        if (this.m_ComPort.IsOpen == true)
//        {
//            this.CLEAR_BUFFER(); this.CASH_Running = true;
//            this.CASH_THREAD = new Thread(() => this.CASH_POLLSTATE());
//            //this.CASH_THREAD = new Thread(() => this.CASH_POLLSTATE(polling));
//            this.CASH_THREAD.Name = "CASH THREAD";
//            this.CASH_THREAD.IsBackground = true;
//            this.CASH_THREAD.Start();
//        }
//    }
//
//    public void CASH_POLL_STOP()
//    {
//        if (this.CASH_Running == true)
//        {
//            this.CASH_Running = false;
//        }
//    }
//
//    public void COIN_INHIBIT(int on)
//    {
//        if (this.m_ComPort.IsOpen == true)
//        {
//            this.CLEAR_BUFFER();
//            byte[] TxBuf = new byte[] { 0x02, 0x1B, 0x1B, 0x03 };
//            switch (on)
//            {
//                case 0: TxBuf = new byte[] { 0x02, 0x1A, 0x1A, 0x03 }; break; // 입수 금지
//                case 1: TxBuf = new byte[] { 0x02, 0x1B, 0x1B, 0x03 }; break; // 입수 허가
//            }
//
//            this.m_ComPort.Write(TxBuf, 0, TxBuf.Length); Thread.Sleep(500);
//        }
//    }
//
//    public void BILL_INHIBIT(int on)
//    {
//        if (this.m_ComPort.IsOpen == true)
//        {
//            this.CLEAR_BUFFER();
//            byte[] TxBuf = new byte[] { 0x02, 0x1B, 0x1B, 0x03 };
//            switch (on)
//            {
//                case 0: TxBuf = new byte[] { 0x02, 0x20, 0x20, 0x03 }; break; // 입수 금지
//                case 1: TxBuf = new byte[] { 0x02, 0x21, 0x21, 0x03 }; break; // 입수 허가
//            }
//
//            this.m_ComPort.Write(TxBuf, 0, TxBuf.Length); Thread.Sleep(500);
//        }
//    }
//
//    public void CASH_STACKBILL(byte accept)
//    {
//        if (this.m_ComPort.IsOpen == true)
//        {
//            this.CLEAR_BUFFER();// 입수[0x23], 반환[0x22]
//            byte[] TxBuf = new byte[] { 0x02, accept, accept, 0x03 };
//            this.m_ComPort.Write(TxBuf, 0, TxBuf.Length); Thread.Sleep(100);
//        }
//    }
//
//    public void CASH_COIN_TUBE()
//    {
//        if (this.m_ComPort.IsOpen == true)
//        {
//            //this.CLEAR_BUFFER(); // 거스름 가능매수 확인
//            byte[] TxBuf = new byte[] { 0x02, 0x1D, 0x1D, 0x03 };
//            this.m_ComPort.Write(TxBuf, 0, TxBuf.Length); Thread.Sleep(100);
//        }
//    }
//
//    public void CASH_COIN_OUT(double coin)
//    {
//        if (this.m_ComPort.IsOpen == true)
//        {
//            byte COIN_Y = Convert.ToByte((int)(coin / 1.0) + 128);
//            byte COIN_U = Convert.ToByte((coin % 1.0) + 128.5);
//
//            this.CLEAR_BUFFER();
//
//            byte[] TxBuf = new byte[] { 0x02, 0x1C, COIN_U, COIN_Y, 0x00, 0x03 };
//            for (int i = 1; i < 4; i++) { TxBuf[4] += TxBuf[i]; }
//            TxBuf[4] |= 0x80;
//
//            this.m_ComPort.Write(TxBuf, 0, TxBuf.Length); Thread.Sleep(100);
//        }
//    }
//
//    private void CLEAR_BUFFER()
//    {
//        //this.m_BufferData.Clear();
//        this.m_ComPort.DiscardOutBuffer(); this.m_ComPort.DiscardInBuffer();
//    }
//
//    private void CASH_POLLSTATE()
//    {
//        try
//        {
//            this.COIN_INHIBIT(1); this.BILL_INHIBIT(1); // 입수준비
//
//            byte[] TxBuf = new byte[] { 0x02, 0x30, 0x30, 0x03 };
//
//            while (this.CASH_Running)
//            {
//                this.m_ComPort.Write(TxBuf, 0, TxBuf.Length);
//                System.Threading.Thread.Sleep(500);
//                System.Windows.Forms.Application.DoEvents();
//            }
//
//            this.COIN_INHIBIT(0); this.BILL_INHIBIT(0); // 입수완료
//        }
//        catch (Exception) { } finally { this.CLEAR_BUFFER(); this.CASH_THREAD = null; }
//    }
//
//    private void GoodsOutState(byte flag, int pros, EventHandler<RobotEventArgs> handler)
//    {
//        switch (flag)
//        {
//            case 0x05: handler(null, new RobotEventArgs(2, pros, "Y轴上方MS 感应器 未感知")); break; // Y축 상단 MS센서 미감지
//            case 0x06: handler(null, new RobotEventArgs(2, pros, "Y轴下方MS 感应器 未感知")); break; // Y축 하단 MS센서 미감지
//            case 0x07: handler(null, new RobotEventArgs(2, pros, "HOME   MS 感应器 未感知")); break; // HOME     MS센서 미감지
//            case 0x09: handler(null, new RobotEventArgs(2, pros, "Door open 感应器 未感知")); break; // Door Open  Sensor Error
//            case 0x0A: handler(null, new RobotEventArgs(2, pros, "Door Close感应器 未感知")); break; // Door Close Sensor Error
//            case 0x0E: handler(null, new RobotEventArgs(2, pros, "运行中 机械臂感应器 感知了物体")); break; // 바구니센서 감지[동작중 사고]
//            case 0x10: handler(null, new RobotEventArgs(2, pros, "X轴 左侧 MS感应器 未感知")); break; // X축 좌측 MS센서 미감지
//            case 0x11: handler(null, new RobotEventArgs(2, pros, "X轴 右侧 MS感应器 未感知")); break; // X축 우측 MS센서 미감지
//            case 0x12: handler(null, new RobotEventArgs(2, pros, "X축 B동 Start센서 미감지")); break;
//            case 0x15: handler(null, new RobotEventArgs(2, pros, "机械臂马达 前方感应器 未感知")); break; // 바구니 모터 앞센서 미감지
//            case 0x16: handler(null, new RobotEventArgs(2, pros, "机械臂马达 后方感应器 未感知")); break; // 바구니 모터 뒤센서 미감지
//            case 0x18: handler(null, new RobotEventArgs(2, pros, "商品 未收取")); break; //  상품 미수거 발생(1분이상 미수거시)
//        }
//    }
//
//    private void DataReceived(object sender, SerialDataReceivedEventArgs e)
//    {
//        EventHandler<RobotEventArgs> handler = this.RobotEvents;
//
//        try
//        {
//            Thread.Sleep(200);
//
//            int ReadBytes = this.m_ComPort.BytesToRead;
//            if (ReadBytes == 0) { return; }
//
//            byte[] temp = new byte[ReadBytes];
//            this.m_ComPort.Read(temp, 0, ReadBytes);
//            this.m_BufferData.Add(temp);
//
//            while (this.m_BufferData.Match(0x02, 0x03)) // STX-ETX
//            {
//                byte[] buff = this.m_BufferData.GetMatchedBytes();
//                this.m_BufferData.RemoveMatched();
//                // this.m_BufferData.Clear();
//
//                this.form.DisplayLabel(this.m_BufferData.ByteArrayToHexString(buff));
//
//                switch (buff[1])
//                {
//                    case 0x09: // ---------------------------------------------------------------------------------- 현재상태확인
//                        switch (buff[2])
//                        {
//                            case 0x00: handler(null, new RobotEventArgs(3, 0, "")); break; // 정상
//                            case 0x13: handler(null, new RobotEventArgs(3, 1, "扫描中")); break; // 스캔중
//                            case 0x14: handler(null, new RobotEventArgs(3, 1, "扫描完毕")); break; // 스캔완료
//                            case 0x01: handler(null, new RobotEventArgs(3, 1, "扫描中 Time Over 发生错误")); break; // 스캔중 타임오버
//                            case 0x0F: handler(null, new RobotEventArgs(3, 1, "扫描中 ERROR")); break; // 스캔중 에러
//                            case 0x0C: handler(null, new RobotEventArgs(3, 2, "TEST MODE 1")); break;
//                            case 0x0D: handler(null, new RobotEventArgs(3, 2, "TEST MODE 2")); break;
//                            case 0x20: handler(null, new RobotEventArgs(3, 3, "硬币机制 发生错误")); break; // 코인메카 에러
//                            case 0x21: handler(null, new RobotEventArgs(3, 4, "纸币被卡到 发生错误")); break; // 지폐걸림
//                            case 0x22: handler(null, new RobotEventArgs(3, 4, "纸币感应器 发生问题")); break; // 지폐센서에러
//                            case 0x23: handler(null, new RobotEventArgs(3, 4, "纸币马达 发生问题")); break; // 지폐모터에러
//                            case 0x24: handler(null, new RobotEventArgs(3, 4, "纸币ROM 发生问题")); break; // 지폐ROM에러
//                        }
//                        break;
//                    case 0x26: // ---------------------------------------------------------------------------------- 품절확인
//                        string goodsNo = this.m_BufferData.ByteToHexString(buff[2]);
//                        handler(null, new RobotEventArgs(4, 0, goodsNo)); break;
//                    case 0x1A: // ---------------------------------------------------------------------------------- 코인/빌 입수금지
//                        switch (buff[2])
//                        {
//                            case 0x00: break;
//                            case 0xAA: this.COIN_INHIBIT(0); break; // 재전송
//                            case 0xEE: handler(null, new RobotEventArgs(1, 9, "硬币机制 发生错误")); break;
//                        }
//                        break;
//                    case 0x20: // ---------------------------------------------------------------------------------- 지폐 입수금지
//                        switch (buff[2])
//                        {
//                            case 0x00: break;
//                            case 0xAA: this.BILL_INHIBIT(0); break; // 재전송
//                            case 0xEE: handler(null, new RobotEventArgs(1, 10, "纸币 ERROR")); break;
//                        }
//                        break;
//                    case 0x1B: // ---------------------------------------------------------------------------------- 코인/빌 입수허가
//                        switch (buff[2])
//                        {
//                            case 0x00: break; // 정상수신[ACK]
//                            case 0xAA: this.COIN_INHIBIT(1); break;
//                            case 0xEE: handler(null, new RobotEventArgs(1, 9, "코인 입수허가 ERROR")); break;
//                        }
//                        break;
//                    case 0x21: // ---------------------------------------------------------------------------------- 지폐 입수허가
//                        switch (buff[2])
//                        {
//                            case 0x00: break; // 정상수신[ACK]
//                            case 0xAA: this.BILL_INHIBIT(1); break;
//                            case 0xEE: handler(null, new RobotEventArgs(1, 10, "纸币 ERROR")); break;
//                        }
//                        break;
//                    case 0x30: // ---------------------------------------------------------------------------------- 코인/빌 입수대기
//                        switch (buff[4])
//                        {
//                            case 0x00:
//                                switch (buff[2])
//                                {
//                                    case 0x50:
//                                        int coinY = Convert.ToInt32(buff[3] - 0x80);
//                                        handler(null, new RobotEventArgs(1, 1, coinY.ToString())); break;
//                                    case 0x51:
//                                        int coinU = Convert.ToInt32(buff[3] - 0x80);
//                                        handler(null, new RobotEventArgs(1, 2, coinU.ToString())); break;
//                                    case 0x80: this.BillCount = Convert.ToInt32(buff[3] - 0x80) *  1; this.CASH_STACKBILL(0x23); break;
//                                    case 0x81: this.BillCount = Convert.ToInt32(buff[3] - 0x80) *  5; this.CASH_STACKBILL(0x23); break;
//                                    case 0x82: this.BillCount = Convert.ToInt32(buff[3] - 0x80) * 10; this.CASH_STACKBILL(0x23); break;
//                                    case 0x83: this.BillCount = Convert.ToInt32(buff[3] - 0x80) * 20; this.CASH_STACKBILL(0x23); break;
//                                }
//                                break;
//                            case 0xEE:
//                                switch (buff[2]) // COINL ERR
//                                {
//                                    case 0x80: break;
//                                    default: handler(null, new RobotEventArgs(1, 9, "COIN ERROR")); break;
//                                }
//                                switch (buff[3]) // BILL ERR
//                                {
//                                    case 0x80: break;
//                                    case 0x81: handler(null, new RobotEventArgs(1, 10, "Bill Motor Failure")); break;
//                                    case 0x82: handler(null, new RobotEventArgs(1, 10, "Bill Sensor Problem")); break;
//                                    case 0x83: handler(null, new RobotEventArgs(1, 10, "Bill is busy")); break;
//                                    case 0x84: handler(null, new RobotEventArgs(1, 10, "Bill CheckSum Error")); break;
//                                    case 0x85: handler(null, new RobotEventArgs(1, 10, "Bill Jam")); break;
//                                    case 0x86: handler(null, new RobotEventArgs(1, 10, "Bill Reset")); break;
//                                    case 0x87: handler(null, new RobotEventArgs(1, 10, "Bill Remove")); break;
//                                    case 0x88: handler(null, new RobotEventArgs(1, 10, "Bill Box out of position")); break;
//                                    case 0x8B: handler(null, new RobotEventArgs(1, 10, "Bill Reject")); break;
//                                    default:   handler(null, new RobotEventArgs(1, 10, "Bill ERROR")); break;
//                                }
//                                break;
//                            case 0xFF: handler(null, new RobotEventArgs(1, 11, "통신이상 ERROR")); break;
//                        }
//                        break;
//                    case 0x23: // ----------------------------------------------------------------------------------- 지폐 입수 신호
//                        switch (buff[2])
//                        {
//                            case 0x00: handler(null, new RobotEventArgs(1, 3, this.BillCount.ToString())); this.BillCount = 0; break;
//                            case 0xAA: this.CASH_STACKBILL(0x23); break; // 재전송
//                            case 0xFF: handler(null, new RobotEventArgs(1, 10, "통신이상 ERROR")); break;
//                            case 0xEE: handler(null, new RobotEventArgs(1, 10, "지폐 입수 ERROR")); break;
//                        }
//                        break;
//                    case 0x22: // ----------------------------------------------------------------------------------- 지폐 반환 신호
//                        switch (buff[2])
//                        {
//                            case 0x00: this.BillCount = 0; handler(null, new RobotEventArgs(1, 4, "0")); break;
//                            case 0xAA: this.CASH_STACKBILL(0x22); break; // 재전송
//                            case 0xFF: handler(null, new RobotEventArgs(1, 10, "통신이상 ERROR")); break;
//                            case 0xEE: handler(null, new RobotEventArgs(1, 10, "지폐 반환 ERROR")); break;
//                        }
//                        break;
//                    case 0x1C: // --------------------------------------------------------------------------- 거스름 배출 확인(실행)
//                        switch (buff[4])
//                        {
//                            case 0x00: handler(null, new RobotEventArgs(1, 5, "COIN OUTING....,")); break; // 반환중
//                            case 0x55: // 반환 종료=반환금 확인
//                                int COIN_U = Convert.ToInt32(buff[2] - 0x80), COIN_Y = Convert.ToInt32(buff[3] - 0x80);
//                                double COIN = (COIN_U * 0.5) + (COIN_Y * 1.0);
//                                handler(null, new RobotEventArgs(1, 6, COIN.ToString())); break;
//                            case 0xEE: handler(null, new RobotEventArgs(1, 7, "ERROR....,")); break;
//                            case 0xFF: handler(null, new RobotEventArgs(1, 7, "ERROR")); break;
//                        }
//                        break;
//                    case 0x1E: // ---------------------------------------------------------------------------------- 코인반환버튼작동
//                        switch (buff[2])
//                        {
//                            case 0x00: this.CASH_COIN_OUT(this.form.CASH_InMoney[0]); break;
//                            case 0xAA: this.CASH_COIN_OUT(this.form.CASH_InMoney[0]); break;
//                            case 0xFF: handler(null, new RobotEventArgs(1, 9, "통신이상 ERROR")); break;
//                            case 0xEE: handler(null, new RobotEventArgs(1, 9, "코인반환버튼 ERROR")); break;
//                        }
//                        break;
//                    case 0x1D: // ------------------------------------------------------------------------------- 거스름 가능매수 확인
//                        switch (buff[4])
//                        {
//                            case 0x00:
//                                int[] OutEnable = { 0, 0 };
//                                OutEnable[0] = Convert.ToInt32(buff[2] - 0x80); // 0.5
//                                OutEnable[1] = Convert.ToInt32(buff[3] - 0x80); // 1
//                                handler(null, new RobotEventArgs(1, 8, OutEnable)); break;
//                            case 0xAA: this.CASH_COIN_TUBE(); break;
//                            case 0xFF: handler(null, new RobotEventArgs(1, 9, "통신이상 ERROR")); break;
//                            case 0xEE: handler(null, new RobotEventArgs(1, 9, "거스름 가능매수 ERROR")); break;
//                        }
//                        break;
//                    case 0x00: // ---------------------------------------------------------------------------------------------- RESET
//                        switch (buff[3])
//                        {
//                            case 0x00: break; // 정상수신[ACK]
//                            case 0xFF: break; // 수신에러[NAK]
//                            case 0xAA: this.RESET(); break;
//                        }
//                        break;
//                    case 0x0E: // ------------------------------------------------------------------------------- 제품추출(바구니에담기)
//                        if (buff[2] == 0xF2) buff[2] = 0x02;
//                        if (buff[2] == 0xF3) buff[2] = 0x03;
//                        switch (buff[3])
//                        {
//                            case 0x00: handler(null, new RobotEventArgs(2, 2, Convert.ToInt32((buff[2])).ToString())); break; // 추출 중
//                            case 0x55: handler(null, new RobotEventArgs(2, 3, Convert.ToInt32((buff[2])).ToString())); break; // 추출 완료
//                            case 0xFF: handler(null, new RobotEventArgs(2, 4, Convert.ToInt32((buff[2])).ToString())); break; // 추출 실패
//                            case 0xEE: handler(null, new RobotEventArgs(2, 4, "추출에러")); break; // 추출 에러
//                            default: this.GoodsOutState(buff[3], 4, handler); break;               // 추출 에러 MSG
//                        }
//                        break;
//                    case 0x08: // --------------------------------------------------------------------------------------------- 제품배출
//                        switch (buff[2])
//                        {
//                            case 0x00: handler(null, new RobotEventArgs(2, 5, "배출  중")); break;  // 배출   중
//                            case 0x55: handler(null, new RobotEventArgs(2, 6, "배출완료")); break;  // 배출 완료
//                            case 0xFF: handler(null, new RobotEventArgs(2, 7, "배출실패")); break;  // 배출 실패
//                            case 0xEE: handler(null, new RobotEventArgs(2, 7, "배출에러")); break;  // 배출 에러
//                            default: this.GoodsOutState(buff[2], 7, handler); break;                // 배출 에러 MSG
//                        }
//                        break;
//                }
//            }
//        }
//        catch (Exception ex) { SunLib.Log.Write(SunLib.Level.ERROR, "RS232Lib_DataReceive", ex.Message + "\r\n" + ex.StackTrace); }
//    }
}
