package com.ys;

/**
 * Created by wangtao on 2016/3/18.
 */
public class GetBytesUtils {


    public static byte[] reset() {

        //重置运输带状态
        byte[] buffer = new byte[]{0x02, 0x00, 0x00, 0x03};
        return buffer;
    }

    public static byte[] standby() {
        byte[] buffer = new byte[]{0x02, 0x0B, 0x0B, 0x03};
        return buffer;
    }

    public static byte[] reback() {
        byte[] buffer = new byte[]{0x02, 0x25, 0x25, 0x03};
        return buffer;
    }

    public static byte[] goodsButtonOn(byte RackNo, byte onff) {
        // 01:ON, 00:OFF
        byte[] buffer = new byte[]{0x02, 0x31, RackNo, onff, 0x00, 0x03};
        for (int i = 1; i < 4; i++) {
            buffer[4] += buffer[i];
        }
        buffer[4] |= 0x80;

        return buffer;
    }

    public static byte[] goodsAllButtonOn(byte onff) {
        // FF:ON, 00:OFF
        byte[] buffer = new byte[]{0x02, 0x32, onff, 0x00, 0x03};
        for (int i = 1; i < 3; i++) {
            buffer[3] += buffer[i];
        }
        buffer[3] |= 0x80;

        return buffer;

    }

    public static byte[] goodsSelect(byte rackNo) {
        if (rackNo == 0x02) {
            rackNo = (byte) 0xF2;
        } else if (rackNo == 0x03) {
            rackNo = (byte) 0xF3;
        }

        byte[] buffer = new byte[]{0x02, 0x0E, rackNo, 0x00, 0x03};
//        for (int i = 1; i < 3; i++) {
//            buffer[3] += buffer[i];
//        }
        //buffer[3] = (buffer[3]|0x80);
        //buffer[3] |= 0X80;
        //buffer[3]&0xFF;
        return buffer;
    }
    public static byte[] goodsOuter() {

        byte[] buffer = new byte[]{0x02, 0x08, 0x08, 0x03};
        return buffer;
    }

    public static byte[] billInhibit(int on)
    {
//        if (this.m_ComPort.IsOpen == true)
//        {
            byte[] buffer = new byte[] { 0x02, 0x1B, 0x1B, 0x03 };
            switch (on)
            {
                case 0: buffer = new byte[] { 0x02, 0x20, 0x20, 0x03 }; break; // 입수 금지
                case 1: buffer = new byte[] { 0x02, 0x21, 0x21, 0x03 }; break; // 입수 허가
            }
            return buffer;
       // }
    }


//    public final void cashPollStart()
//    {
//              this.CASH_THREAD = new Thread(() => this.CASH_POLLSTATE());
//
//        }
//    }
//
//
//    public final void CASH_POLL_STOP()
//    {
//        if (this.CASH_Running == true)
//        {
//            this.CASH_Running = false;
//        }
//    }

    // 控制 硬币器  开启 /关闭
    public static byte[] coinInhibit(int on)
    {
           byte[] buffer = new byte[] { 0x02, 0x1B, 0x1B, 0x03 };
            switch (on)
            {
                case 0: // 关闭
                    buffer = new byte[] { 0x02, 0x1A, 0x1A, 0x03 };
                    break;
                case 1: // 입수 허가
                    buffer = new byte[] { 0x02, 0x1B, 0x1B, 0x03 };
                    break;
            }
          return buffer;


    }

    /**
     * 纸币器  关闭/开启
     * @param on
     * @return
     */
    public static byte[] BILL_INHIBIT(int on) {

        byte[] buffer = new byte[]{0x02, 0x1B, 0x1B, 0x03};
        switch (on) {
            case 0: // 입수 금지
                buffer = new byte[]{0x02, 0x20, 0x20, 0x03};
                break;
            case 1: // 입수 허가
                buffer = new byte[]{0x02, 0x21, 0x21, 0x03};
                break;
        }

        return buffer;

    }

    public static byte[] CASH_STACKBILL(byte accept)
    {

        return new byte[] { 0x02, accept, accept, 0x03 };

    }

    public static byte[] CASH_COIN_TUBE()
    {
            // 거스름 가능매수 확인
         return new byte[] { 0x02, 0x1D, 0x1D, 0x03 };

    }

    // 退款
//    public final void CASH_COIN_OUT(double coin)
//    {
//        if (this.m_ComPort.IsOpen == true)
//        {
//            byte COIN_Y = Byte.parseByte((int)(coin / 1.0) + 128);
//            byte COIN_U = Byte.parseByte((coin % 1.0) + 128.5);
//
//            this.CLEAR_BUFFER();
//
//            byte[] TxBuf = new byte[] { 0x02, 0x1C, COIN_U, COIN_Y, 0x00, 0x03 };
//            for (int i = 1; i < 4; i++)
//            {
//                TxBuf[4] += TxBuf[i];
//            }
//            TxBuf[4] |= 0x80;
//
//            this.m_ComPort.Write(TxBuf, 0, TxBuf.length);
//            Thread.sleep(100);
//        }
//    }


//    private void CASH_POLLSTATE()
//    {
//        try
//        {
//            this.COIN_INHIBIT(1); // 입수준비
//            this.BILL_INHIBIT(1);
//
//            byte[] TxBuf = new byte[] { 0x02, 0x30, 0x30, 0x03 };
//
//            while (this.CASH_Running)
//            {
//                this.m_ComPort.Write(TxBuf, 0, TxBuf.length);
//                Thread.sleep(500);
//                System.Windows.Forms.Application.DoEvents();
//            }
//
//            this.COIN_INHIBIT(0); // 입수완료
//            this.BILL_INHIBIT(0);
//        }
//        catch (RuntimeException e)
//        {
//        }
//        finally
//        {
//            this.CLEAR_BUFFER();
//            this.CASH_THREAD = null;
//        }
//    }





}
