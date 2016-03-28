package com.ys.ui.analysis;

import android.util.Log;

/**
 * Created by yelz on 2016/1/31.
 */
/*
成功:公共部分，
4位地区区号+3位缴费行业代号+3位缴费内容码（缴费内容码目前只有有线电视有）+20位用户号+80位客户姓名
电费：
12位应缴纳金额+12用电客户余额+7位供电公司编号+32位代收内部账号+80位代收内部户名+6位摘要码+50摘要信息；
有线电视：
缴费内容码为001：20位账户编码+20位客户编码+2位业务编码+4位运营区域编码+25收款账号+60位收款户名+2位返回记录条数（最大返回5条）+20位余额账本编码+60位余额账本名称+20位账本余额；
缴费内容码为002:8位用户类别+10位联系方式+10位前截止日期+10位后截止日期12为滞纳金+40位用户地址+12位应缴纳金额+32位收款账号+40为收款户名

除电费、有线电视费：16位查询流水号+12位应缴金额+保留字段1+保留字段2+（上期余额+滞纳金 注：只有景德镇水费才有此内容）
失败: 4位地区区号+3位缴费行业代号+20位用户号+错误信息

*/
public class PayQueryAnalysis {
    //"000"渠道
    public static final int RST_TYPE_300=300;
    public static final int RST_TYPE_500=300;
    public static final int RST_TYPE_999_001=999001;
    public static final int RST_TYPE_999_002=999002;
    public static final int RST_TYPE_OTHER=1;
    //"999"渠道缴费通
    public static final int RST_TYPE_JFT=2;

    private static final String TAG = "PayQueryAnalysis";

    /**
     * 解析
     * @param type 渠道标志 "000","999",后者为缴费通
     * @param dataStr 信息
     * @return
     */
    public static PayQueryAnalysisRst Analysis(String type, String dataStr){
        PayQueryAnalysisRst analysisRst = new PayQueryAnalysisRst();
        _common_rst rst = null;

        String tmp1="",tmp2="";
        String[] strArr = null;
        byte[] dataArr = null;
        dataArr = dataStr.getBytes();
        ///////////////////////////////////////////////字符串初步解析////////////////////
        //简单判断参数和长度
        if(dataArr==null
                ||dataStr==null
                || dataStr.length()<8){
            Log.d(TAG, "dataStr == null || dataStr.length() < 8");
            return analysisRst;
        }

        //替换第一个“|”,以便于处理
        if(dataArr[0]==(byte)'|'){
            tmp1 = dataStr.replaceFirst("\\|", "");
        }else{
            tmp1 = dataStr;
        }
        dataArr = tmp1.getBytes();
        if(dataArr[dataArr.length-1]==(byte)'|'){
            tmp1 += " ";//便于split
        }

        System.out.println(">>>>>>>2.2---"+tmp1);
        //分割字符串至一个数组
        strArr = tmp1.split("\\|");


        if(strArr==null || strArr.length<4){//数组无效，大小至少必须大于4
            Log.d(TAG, "strArr==null || strArr.length<4");
            return analysisRst;//解析失败
        }
        //////////////////////////////////////////////具体业务解析////////////////////////////////////////////////
        System.out.println(">>>>>>>3,strArr.length:"+strArr.length);
        if(strArr.length==4){//失败
            rst = new _fail_Rst();
            ((_fail_Rst)rst).setRst(strArr);
            analysisRst.setRst(rst);
            analysisRst.setFlagClass(_fail_Rst.class);
            return analysisRst;//
        }
        ////////打印信息
        /*for(int j=0;j<strArr.length;j++){
            System.out.println(">>>>>["+j+"]"+strArr[j]);
        }*/
        ////////////////
        if(type.equals("000")) {
            System.out.println(">>>>>>>4");
            if (strArr[1].equals("100")//) {//100 通讯费
            || strArr[1].equals("211")//) {//211 电信绑定号码宽带费
            || strArr[1].equals("212")//) {//212 电信ADSL宽带费
            || strArr[1].equals("213")//) {//213 电信LAN宽带费
            || strArr[1].equals("221")//) {//221 新联通ADSL宽带费
            || strArr[1].equals("222")//) {//222 新联通LAN宽带费
            || strArr[1].equals("400")) {//400 燃气费
                rst = new _other_Rst();
                ((_other_Rst)rst).setRst(strArr);
                analysisRst.setRst(rst);
                analysisRst.setFlagClass(_other_Rst.class);
            }else if (strArr[1].equals("300")) {//300 电费
                rst = new _300_Rst();
                ((_300_Rst)rst).setRst(strArr);
                analysisRst.setRst(rst);
                analysisRst.setFlagClass(_300_Rst.class);
            } else if (strArr[1].equals("500")) {//500 水费
                rst = new _500_Rst();
                ((_500_Rst)rst).setRst(strArr);
                analysisRst.setRst(rst);
                analysisRst.setFlagClass(_500_Rst.class);
            } else if (strArr[1].equals("999")) {//999 有线电视（自定义）
                if (strArr[1].equals("001")) {//缴费内容码为001
                    rst = new _999_001_Rst();
                    ((_999_001_Rst)rst).setRst(strArr);
                    analysisRst.setRst(rst);
                    analysisRst.setFlagClass(_999_001_Rst.class);
                } else if (strArr[1].equals("002")) {//缴费内容码为002
                    rst = new _999_002_Rst();
                    ((_999_002_Rst)rst).setRst(strArr);
                    analysisRst.setRst(rst);
                    analysisRst.setFlagClass(_999_002_Rst.class);
                }
            }
        }else if(type.equals("999")){//缴费通渠道
            rst = new _jft_Rst();
            ((_jft_Rst)rst).setRst(strArr);
            analysisRst.setRst(rst);
            analysisRst.setFlagClass(_jft_Rst.class);
        }


        return analysisRst;
    }

    public static final int AreaCode = 0;//4位地区区号
    public static final int PaymentIndustryCode = 1;//3位缴费行业代号
    public static final int UserNumber = 2;//20位用户号
    public static final int ErrorInof = 3;//错误信息
    public static final int ATTR_CNT = 4;
    public static String getAttr(int attrOffset,String[] srcArr){
        if(srcArr==null || srcArr.length<ATTR_CNT
                || attrOffset<0|| attrOffset>=ATTR_CNT){//异常返回；下标不符，或者源数组不符
            System.out.println("_Fail_Rst need String array size "+ATTR_CNT+", Array "+(srcArr==null?"null":"not null")
                    +", size "+(srcArr==null?"0":srcArr.length));
            return "";
        }
        return srcArr[attrOffset];
    }
}
