package com.ys.ui.analysis;

import java.lang.reflect.Field;

/**
 * Created by thinkpad on 2016/1/31.
 */
public class _999_002_Rst extends _common_rst{
    /*//4位地区区号+3位缴费行业代号+3位缴费内容码（缴费内容码目前只有有线电视有）+20位用户号+80位客户姓名
    */
    public enum _f {
        AreaCode,// 0;//4位地区区号
        PaymentIndustryCode,// 1;//3位缴费行业代号
        PaymentContentCode,// 0;//3位缴费内容码
        UserNumber,// 2;//20位用户号
        CustomerName,// 3;//80位客户姓名

        //缴费内容码为002
        //8位用户类别+10位联系方式+10位前截止日期+10位后截止日期+12为滞纳金+40位用户地址+12位应缴纳金额+32位收款账号+40为收款户名
        //8位用户类别+10位联系方式+10位前截止日期+10位后截止日期12为滞纳金+40位用户地址+12位应缴纳金额+32位收款账号+40为收款户名

        UserType,//4;//8位用户类别
        Contact,//5;//10位联系方式
        ClosingDate_01,//6;//10位前截止日期
        ClosingDate_02,//7;//10位后截止日期12为滞纳金
        LateFee,//8;//12为滞纳金
        UserAddress,//9;//40位用户地址
        PayableLateFee,//10;//12位应缴纳金额
        RecvAccountNo,//11;//32位收款账号
        RecvAccountName,//12;//40为收款户名
        }
}
