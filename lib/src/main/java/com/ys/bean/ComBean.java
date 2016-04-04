package com.ys.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangtao on 2016/4/2.
 */
public class ComBean {

    public byte[] bRec = null;
    public String sComPort = "";
    public String sRecTime = "";

    public ComBean(String paramString, byte[] paramArrayOfByte, int paramInt)
    {
        this.sComPort = paramString;
        this.bRec = new byte[paramInt];
        for (int i = 0; ; i++)
        {
            if (i >= paramInt)
            {
                this.sRecTime = new SimpleDateFormat("hh:mm:ss").format(new Date());
                return;
            }
            this.bRec[i] = paramArrayOfByte[i];
        }
    }
}
