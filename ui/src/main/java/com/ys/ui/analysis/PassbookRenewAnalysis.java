package com.ys.ui.analysis;

import java.io.UnsupportedEncodingException;

/**
 * Created by thinkpad on 2016/1/31.
 */
public class PassbookRenewAnalysis {


    public static PassbookRenewRecord[] Analysis(String dataStr){
        byte[] dataArr = null;
        PassbookRenewRecord[] ret = null;
        int arrCnt = 0,i=0;
        if(dataStr==null)return null;

        try {
            dataArr = dataStr.getBytes("GBK");//库里面已经是通过GBK生成的String
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if(dataArr==null || dataArr.length<64 || (dataArr.length%64!=0)){
            System.out.println(">>>>>data err, size:"+(dataArr==null?"0":dataArr.length));
            return null;
        }
        System.out.println(">>>>>data err, size:"+(dataArr==null?"0":dataArr.length));
        arrCnt = dataArr.length/64;
        ret = new PassbookRenewRecord[arrCnt];

        if(ret==null)return null;

        i=0;
        while(i<arrCnt){//循环解析
            try {
                ret[i]=PassbookRenewRecord.Unpack(dataArr, (i * 64));
                i++;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                System.out.println(">>>>>PassbookRenewRecord["+i+"/"+(arrCnt-1)+"]  unpack error!!");
            }
        }

        return ret;
    }
}
