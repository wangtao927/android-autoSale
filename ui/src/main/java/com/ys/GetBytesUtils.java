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
}
